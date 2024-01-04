import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score

class KNeighborsClassUE:
    def __init__(self, k=3, p=2.0):
        self.k = k
        self.p = p
        self.X_train = None
        self.y_train = None

    def fit(self, X, y):
        self.X_train = X
        self.y_train = y

    def _minkowski_distance(self, x1, x2):
        return np.sum(np.abs(x1 - x2) * self.p) * (1/self.p)

    def predict(self, X):
        y_pred = []
        for x in X:
            distances = [self._minkowski_distance(x, x_train) for x_train in self.X_train]
            neighbors_indices = np.argsort(distances)[:self.k]
            classes, counts = np.unique(self.y_train[neighbors_indices], return_counts=True)
            predicted_class = classes[np.argmax(counts)]
            y_pred.append(predicted_class)
        return np.array(y_pred)

    def score(self, X, y):
        y_pred = self.predict(X)
        accuracy = np.mean(y_pred == y)
        return accuracy

# Carregar o conjunto de dados usar Pandas
file_path = 'iris.csv'
data = pd.read_csv(file_path)

# Separar os dados em atributos (X) e rótulos (y)
X = data.iloc[:, :-1].values
y = data.iloc[:, -1].values

# Dividir os dados em conjuntos de treino e teste usar train_test_split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Testar diferentes valores de k e p
k_values = [1, 3, 5, 9]
p_values = [1, 2]

for k in k_values:
    for p in p_values:
        # Criar e treinar o modelo
        model = KNeighborsClassUE(k=k, p=p)
        model.fit(X_train, y_train)

        # Calcular a estimativa de desempenho com a função score criada anteriormente
        score = model.score(X_test, y_test)

        # Calcular a precisão usar a função accuracy_score para comparação
        accuracy = accuracy_score(y_test, model.predict(X_test))

        print(f'K={k}, p={p}: Score = {score}, Accuracy (via accuracy_score) = {accuracy}')
