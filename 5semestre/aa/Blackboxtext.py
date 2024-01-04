import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
from sklearn.preprocessing import LabelEncoder

class NaiveBayesClassifier:
    def __init__(self, alpha=1.0):
        self.alpha = alpha
        self.class_probs = None
        self.feature_probs = None
        self.classes = None

    def fit(self, X, y):
        self.classes = np.unique(y)
        self.class_probs = self.calculate_class_probs(y)
        self.feature_probs = self.calculate_feature_probs(X, y)

    def predict(self, X):
        return np.argmax(self.predict_proba(X), axis=1)

    def predict_proba(self, X):
        predictions = []
        for instance in X:
            instance_probs = []
            for idx, class_val in enumerate(self.classes):
                class_prob = np.log(self.class_probs[idx])
                feature_probs = self.feature_probs[idx]
                for j, feature_val in enumerate(instance):
                    if feature_val in feature_probs[j]:
                        class_prob += np.log(feature_probs[j][feature_val])
                    else:
                        # Handle the situation where a value in the test set is not present in the training set
                        class_prob += np.log(self.alpha / (len(feature_probs[j]) + self.alpha))
                instance_probs.append(class_prob)
            predictions.append(instance_probs)
        return np.exp(predictions) / np.sum(np.exp(predictions), axis=1, keepdims=True)

    def score(self, X, y):
        return np.mean(self.predict(X) == y)

    def calculate_class_probs(self, y):
        class_freq = np.bincount(y)
        return class_freq[1:] / len(y)

    def calculate_feature_probs(self, X, y):
        feature_probs = []
        for class_val in self.classes:
            class_mask = (y == class_val)
            class_X = X[class_mask, :]
            feature_probs_class = []
            for j in range(class_X.shape[1]):
                feature_vals, feature_counts = np.unique(class_X[:, j], return_counts=True)
                feature_probs_class.append(dict(zip(feature_vals, feature_counts / len(class_X))))
            feature_probs.append(feature_probs_class)
        return feature_probs

dataFrame = pd.read_csv(filepath_or_buffer='trabalho_51483_51820_51717/nominais/bc-nominal.csv')

# Seleciona todas as linhas e todas as colunas exceto a última do DataFrame
X = dataFrame.iloc[:, :-1].values  # Atributos
y = dataFrame.iloc[:, -1].values  # Rótulos

# Convert the target variable to integers using LabelEncoder
label_encoder = LabelEncoder()
y = label_encoder.fit_transform(y)

# Divide os dados em conjuntos de treino e teste
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.25, train_size=0.75, random_state=3)

# Inicializa e treina o modelo
alpha_values_to_test={0, 1, 3, 5}

for alpha_value in alpha_values_to_test:
    # Initialize the NaiveBayesClassifier
    NBUE = NaiveBayesClassifier(alpha=alpha_value)
    
    # Fit the model using the training data
    NBUE.fit(X_train, y_train)

        # Faz previsões e calcula a precisão
    accuracy = NBUE.score(X_test, y_test)

        # -------------
    # Similarly, for scikit-learn Naive Bayes classifier
    knn_sklearn = NaiveBayesClassifier(alpha=alpha_value)
    knn_sklearn.fit(X_train, y_train)

    # Verificar se os valores estão bem calculados com o scikit-learn
    knn_sklearn = NaiveBayesClassifier(alpha_value=alpha_value)
    knn_sklearn.fit(X_train, y_train)

    predictions_sklearn = knn_sklearn.predict(X_test)

    accuracy_sklearn = accuracy_score(y_test, predictions_sklearn)

    print(f"alpha: {alpha_value} | NBUE: {accuracy} | Sklearn: {accuracy_sklearn}")