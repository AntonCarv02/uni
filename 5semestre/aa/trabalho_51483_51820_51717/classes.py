import numpy as np
import pandas as pd


class KNeighborsClassUE:
    def __init__(self, k=3, p=2.0):
        self.k = k
        self.p = p
        self.X_train = None
        self.y_train = None

    def fit(self, X, y):
        self.X_train = np.array(X)
        self.y_train = np.array(y)

    def _minkowski_distance(self, x1, x2):
        return np.sum(np.abs(x1 - x2) ** self.p) ** (1 / self.p)

    def predict(self, X):
        X = np.array(X)
        predictions = []

        for sample in X:
            # calculo de distanica entre atributos
            distances = [self._minkowski_distance(sample, x) for x in self.X_train]

            indices = np.argsort(distances)[:self.k]

            neighbors_labels = self.y_train[indices]

            prediction = np.bincount(neighbors_labels).argmax()
            predictions.append(prediction)

        return np.array(predictions)

    def score(self, X, y):
        predictions = self.predict(X)
        accuracy = np.mean(predictions == y)
        return accuracy
    



class NBayesClassUE:
    def __init__(self, alpha):
        self.alpha = alpha
        self.probs = {}

    def fit(self, X, y):
        
        self.class_counts = {}
        for i in range(len(y)):
            if y[i] not in self.class_counts:
                self.class_counts[y[i]] = 0
                self.class_counts[y[i]] += 1
                
        self.feature_counts = {}
        for i in range(len(y)):
            if y[i] not in self.feature_counts:
                self.feature_counts[y[i]] = {}
            for j in range(len(X[i])):
                feature = X[i][j]
                if feature not in self.feature_counts[y[i]]:
                    self.feature_counts[y[i]][feature] = 0
                self.feature_counts[y[i]][feature] += 1

        
        self.class_probs = {}
        for c in self.class_counts:
            self.class_probs[c] = (self.class_counts[c] + self.alpha) / (len(y) + self.alpha * len(self.class_counts))

        # calculate the probability of each feature value given a class
        self.feature_probs = {}
        for c in self.feature_counts:
            self.feature_probs[c] = {}
            total_count = sum(self.feature_counts[c].values()) + self.alpha * len(X[0])
            for f in self.feature_counts[c]:
                self.feature_probs[c][f] = (self.feature_counts[c][f] + self.alpha) / total_count

    def predict(self, X):
        for x in X:
            # calcular a probablilidade de cada classe para os atributos dados
            class_scores = {}
            for c in self.class_probs:
                class_scores[c] = 1.0
                for i in range(len(x)):
                    if x[i] in self.feature_probs[c]:
                        class_scores[c] *= self.feature_probs[c][x[i]]
                    else:
                        class_scores[c] *= 1.0 / len(self.feature_probs[c])
            # retorna a classe com a classificação mais alta
            return max(class_scores, key=class_scores.get)


    def score(self, X, y):

        y_pred = self.predict(X)

        return sum(y_pred == y) / len(y)