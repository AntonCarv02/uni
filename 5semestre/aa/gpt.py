import numpy as np

class KNeigborsClassUE:
    def __init__(self, k=3, p=2.0):
        self.k = k
        self.p = p
        self.X_train = None
        self.y_train = None

    def fit(self, X, y):
        self.X_train = X
        self.y_train = y

    def predict(self, X):
        predictions = []
        for sample in X:
            distances = np.linalg.norm(self.X_train - sample, ord=self.p, axis=1)
            sorted_indices = np.argsort(distances)
            k_nearest_labels = self.y_train[sorted_indices[:self.k]]
            unique_labels, counts = np.unique(k_nearest_labels, return_counts=True)
            predicted_label = unique_labels[np.argmax(counts)]
            predictions.append(predicted_label)
        return np.array(predictions)

    def score(self, X, y):
        predictions = self.predict(X)
        accuracy = np.mean(predictions == y)
        return accuracy

# Exemplo de uso para KNN
knn_model = KNeigborsClassUE(k=5, p=1.5)
knn_model.fit(X_train, y_train)
predictions = knn_model.predict(X_test)
accuracy = knn_model.score(X_test, y_test)
print(f"Accuracy: {accuracy}")

# Exemplo de uso para Naïve Bayes
nb_model = NBayesClassUE(alpha=0.1)
nb_model.fit(X_train, y_train)
predictions = nb_model.predict(X_test)
accuracy = nb_model.score(X_test, y_test)
print(f"Accuracy: {accuracy}")










import numpy as np

class NBayesClassUE:
    def __init__(self, alpha=1):
        self.alpha = alpha
        self.class_probs = None
        self.feature_probs = None
        self.classes = None

    def fit(self, X, y):
        self.classes, class_counts = np.unique(y, return_counts=True)
        num_classes = len(self.classes)
        num_features = X.shape[1]

        # Calcula as probabilidades a priori das classes
        self.class_probs = class_counts / len(y)

        # Inicializa as probabilidades condicionais dos atributos para cada classe
        self.feature_probs = np.zeros((num_classes, num_features), dtype=dict)

        # Calcula as probabilidades condicionais dos atributos para cada classe
        for i, label in enumerate(self.classes):
            class_examples = X[y == label]
            class_total = len(class_examples) + self.alpha * num_features

            for j in range(num_features):
                feature_values, feature_counts = np.unique(class_examples[:, j], return_counts=True)
                feature_probs_dict = dict(zip(feature_values, (feature_counts + self.alpha) / class_total))
                self.feature_probs[i, j] = feature_probs_dict

    def predict(self, X):
        predictions = []
        for sample in X:
            class_scores = []
            for i, label in enumerate(self.classes):
                class_score = np.log(self.class_probs[i])

                for j, feature_value in enumerate(sample):
                    if feature_value in self.feature_probs[i, j]:
                        class_score += np.log(self.feature_probs[i, j][feature_value])
                    else:
                        # Lidar com valores de atributo que não ocorrem nos dados de treino
                        class_score += np.log(self.alpha / (len(self.feature_probs[i, j]) + self.alpha))

                class_scores.append(class_score)

            predicted_label = self.classes[np.argmax(class_scores)]
            predictions.append(predicted_label)

        return np.array(predictions)

    def score(self, X, y):
        predictions = self.predict(X)
        accuracy = np.mean(predictions == y)
        return accuracy

