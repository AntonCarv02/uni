import numpy as np
import pandas as pd


class KNeigborsClassUE:
    def __init__(self, k = 3, p = 2.0):
        self.k = k
        self.p = p

        print("k = " + str(k) + " p = " + str(p))

        

    def fit(self, X, Y):
        self.X_train = X
        self.y_train = Y
        

    def predict(self, X):
        res = [] # Lista para guardar as classes resultantes

        for x in X:
            calc = [] # Lista para guardar todas as distâncias de X aos dados de treino

            for ex in self.X_train:
                soma = 0
                for i in range(len(x)):
                    soma += abs(x[i] - ex[i]) ** self.p
                distance = soma ** (1/self.p)
                calc.append(distance)

            indexes = np.argsort(calc)

            # Fazer a contagem para determinar qual é a classe persistente
            classes = []
            count = []

            for i in range(self.k):
                if self.y_train[indexes[i]] not in classes:
                    classes.append(self.y_train[indexes[i]])
                    count.append(1)
                else:
                    count[classes.index(self.y_train[indexes[i]])] += 1

            indexes = np.argsort(count)
            res.append(classes[indexes[-1]])
        return res

    def score(self, X, y):
        correct = 0

        # Ajustar os arrays no caso de só terem uma dimensão
        X = np.atleast_2d(X)
        y = np.atleast_2d(y).reshape(-1, 1)

        y_pred = np.array(self.predict(X)).reshape(-1, 1)

        for i in range(len(y)):
            if(y[i] == y_pred[i]):
                correct += 1
        
        return float(correct / len(X))
