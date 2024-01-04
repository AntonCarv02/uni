import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split


class NBayesClassUE:
    def __init__(self, alpha):
        # Parâmetro aditivo de suavização para o cálculo da estimativa de probabilidades (Lidstone)
        # alpha = 1 é equivalente ao estimador de Laplace
        # alpha = 0 não há suavização
        self.alpha = alpha


    def fit(self, X, y):
        self.classesUnicas = set(y) # Classes únicas presentes no conjunto de etiquetas de y.
        self.probabilidadesClasse = {} # Lista que vai ser usada para armazenar as probabilidades calculadas.

        # Calcula as probabilidades para cada classe
        for classe in self.classesUnicas:
            self.probabilidadesClasse[classe] = float(list(y).count(classe) + self.alpha) / (len(y) + (self.alpha * len(self.classesUnicas)))
        # list(y).count(classe):  Número de instâncias no conjunto de dados que pertencem à classe. nx + alpha
        # Divisor da fórmula: (len(y) + (self.alpha * len(self.classesUnicas))) número total de instâncias + alpha * número de classes únicas no conjunto de dados

        # Calcula as probabilidades para cada atributo e classe
        for i in range(len(X[0])): # O loop percorre cada índice dos atributos no conjunto de dados.
            #X[0]: comprimento da primeira dimensão de X 
            
            # Conjunto de valores únicos para o atributo i
            atributosUnicos = set(X[:, i]) # Cria um conjunto das atributos únicos na coluna i do conjunto de dados X.

            for atributo in atributosUnicos:
                for classe in self.classesUnicas:
                    quantidade = 0

                    # Contagem de instâncias onde o atributo é igual a 'atributo' e a classe é igual a 'classe'
                    for j in range(len(X)):
                        if X[j][i] == atributo and y[j] == classe:
                            quantidade += 1

                    # Cálculo da probabilidade suavizada 
                    probabilidadeSuavizada = float(quantidade + self.alpha) / (list(y).count(classe) + self.alpha * len(atributosUnicos))

                    # Armazenamento da probabilidade condicional no dicionário
                    self.probabilidadesClasse[f"[{atributo}|{classe}]"] = probabilidadeSuavizada


    # Método predict(X): aplica o modelo e devolve as etiquetas previstas para o conjunto fornecido
    # X: array com forma (nexemplos, natributos). Dados de teste;
    def predict(self, X):   
        previsoes = []  # Lista para armazenar as previsões para cada instância em X

        for atributos in X:
            probabilidadeMaxima = -1  # Variável que vai guardar a maior probabilidade
            classeMaiorProbabilidade = " "  # Variável que vai guardar a classe com maior probabilidade

            # Avaliar cada classe presente 
            for classe in self.classesUnicas:
                prob = self.probabilidadesClasse[classe] # Variável prob inicializada com a probabilidade da classe

                for atributo in atributos:
                    prob = prob * self.probabilidadesClasse[f"[{atributo}|{classe}]"]
                    # Atualiza a prob, multiplicando pela probabilidade condicional do atributo dado a classe

                # Comparar e atualizar se a probabilidade atual for maior que a máxima encontrada até agora (prob e classe)
                if prob > probabilidadeMaxima:
                    probabilidadeMaxima = prob
                    classeMaiorProbabilidade = classe

            previsoes.append(classeMaiorProbabilidade)

        return previsoes



    def score(self, X, y):
        # Método score(X, y): prevê o valor associado a cada exemplo do conjunto e devolve a exatidão
        # X: array com forma (nexemplos, natributos). Dados (treino ou teste);
        # y: array com forma (nexemplos). Etiquetas (treino ou teste);

        previsoesCorretas = 0

        X = np.atleast_2d(X)  # Garantir que X tenha pelo menos duas dimensões.
        y = np.atleast_2d(y).reshape(-1, 1)

        previsao = np.array(self.predict(X)).reshape(-1, 1)

        for i in range(len(y)):
            if previsao[i] == y[i]:  # Compara cada previsão com a verdadeira etiqueta (y[i]).
                previsoesCorretas += 1  # Se for igual, então a previsão foi correta.

        # Retorna a exatidão
        return float(previsoesCorretas / len(X))  # Número de previsões corretas dividido pelo número total de exemplos no conjunto de teste


dataFrame = pd.read_csv(filepath_or_buffer='trabalho_51483_51820_51717/nominais/bc-nominal.csv')

# Seleciona todas as linhas e todas as colunas exceto a última do DataFrame
X = dataFrame.iloc[:, :-1].values  # Atributos
y = dataFrame.iloc[:, -1].values  # Rótulos

# Divide os dados em conjuntos de treino e teste
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.25, train_size=0.75, random_state=3)

# Inicializa e treina o modelo
alpha_values_to_test={0, 1, 3, 5}

for alpha_value in alpha_values_to_test:
        # Inicializa os testes de forma dinâmica
        NBUE = NBayesClassUE(alpha=alpha_value)
        NBUE.fit(X_train, y_train)

        # Faz previsões e calcula a precisão
        accuracy = NBUE.score(X_test, y_test)

        # -------------

       
        print(f"alpha: {alpha_value} | NBUE: {accuracy} ")
