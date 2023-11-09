import numpy as np  
import pandas as pd
from sklearn.naive_bayes import MultinomialNB
from sklearn.preprocessing import LabelEncoder
class NBayesClassUE:
    def __init__(self, alpha=1.0):
        self.alpha = alpha
        self.model = MultinomialNB(alpha=alpha)

    def fit(self, X, y):
        
        label_encoder = LabelEncoder()
        X_encoded = X.apply(label_encoder.fit_transform)
        self.model.fit(X_encoded, y)

    def predict(self, X):
        # Use LabelEncoder to convert categorical features to numeric
        label_encoder = LabelEncoder()
        X_encoded = X.apply(label_encoder.fit_transform)
        # Predict labels
        y_pred = self.model.predict(X_encoded)
        return y_pred

    def score(self, X, y):
        # Use LabelEncoder to convert categorical features to numeric
        label_encoder = LabelEncoder()
        X_encoded = X.apply(label_encoder.fit_transform)
        # Score the model
        accuracy = self.model.score(X_encoded, y)
        return accuracy

# Lista de nomes de arquivos CSV que você deseja usar
arquivos_csv = ["bc-nominal.csv", "contact-lenses.csv", "weather-nominal.csv"]

# Dicionário que mapeia os nomes dos arquivos para as colunas de destino
colunas_destino = {
    "bc-nominal.csv": "diagnosis",
    "contact-lenses.csv": "contact_lenses",
    "weather-nominal.csv": "play"
}

for arquivo in arquivos_csv:
    print(f"Lendo o arquivo: {arquivo}")
    # Carregue seus dados de treinamento e teste usando Pandas
    data = pd.read_csv(arquivo)

    # Verifique se a coluna de destino correta existe no conjunto de dados
    if arquivo in colunas_destino:
        nome_da_coluna_destino = colunas_destino[arquivo]
        if nome_da_coluna_destino in data.columns:
            # Separe os dados em recursos e etiquetas
            X = data.drop(columns=[nome_da_coluna_destino])
            y = data[nome_da_coluna_destino]

            # Inicialize o modelo e ajuste-o aos dados de treinamento
            nb_classifier = NBayesClassUE(alpha=1.0)
            nb_classifier.fit(X, y)

            # Faça previsões nos dados de teste
            y_pred = nb_classifier.predict(X)

            # Avalie a precisão do modelo
            accuracy = nb_classifier.score(X, y)
            print(f"Accuracy for {arquivo}: {accuracy}")
        else:
            print(f"A coluna de destino '{nome_da_coluna_destino}' não foi encontrada no conjunto de dados.")
    else:
        print(f"Coluna de destino não definida para {arquivo}. Defina-a corretamente.")
