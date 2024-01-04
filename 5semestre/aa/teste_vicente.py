import numpy as np
import pandas as pd


def separar_coluna(df):
    # coloca a ultima coluna do dataframe no y
    # coloca o resto do dataframe no X
    X = df.drop([df.columns[-1]], axis=1)
    y = df.iloc[:, -1]

    return X, y


class NaiveBayesUevora:
    def __init__(self):
        # inicializa variaveis e dicionarios necessários
        self.classes = list
        self.ClassesAtrib = {}
        self.probY = {}
        self.probAtrib = {}

        self.X_train = np.array
        self.y_train = np.array
        self.train_size = int
        self.num_classes = int

        # pede ao utilizador o alpha para o estimador
        self.alpha = input('insira o alpha do estimador')
        # no caso de não ser inserido nada, alpha=0
        if self.alpha == "":
            self.alpha = float(0)

    def fit(self, X, y):

        self.classes = list(X.columns)
        self.X_train = X
        self.y_train = y
        self.train_size = X.shape[0]
        self.num_classes = X.shape[1]

        for atrib in self.classes:
            self.ClassesAtrib[atrib] = {}
            self.probAtrib[atrib] = {}

            for atrib_val in np.unique(self.X_train[atrib]):

                self.probAtrib[atrib].update({str(atrib_val): 0})

                for outcome in np.unique(self.y_train):
                    self.ClassesAtrib[atrib].update({str(atrib_val) + '_' + outcome: 0})
                    self.probY.update({outcome: 0})

        self.prob_y()
        self.probClassAtrib()
        self.prob_Atrib()

    def prob_y(self):
        # calcula probabilidade de ser yes ou no
        for outcome in np.unique(self.y_train):
            outcome_count = sum(self.y_train == outcome)
            self.probY[outcome] = outcome_count / self.train_size

    def probClassAtrib(self):
        # calcula probabiliade de cada atributo ser yes ou no
        for atrib in self.ClassesAtrib:

            for outcome in np.unique(self.y_train):
                outcome_count = sum(self.y_train == outcome)
                atrib_count = self.X_train[atrib][
                    self.y_train[self.y_train == outcome].index.values.tolist()].value_counts().to_dict()

                for atrib_val, count in atrib_count.items():
                    self.ClassesAtrib[atrib][str(atrib_val) + '_' + outcome] = (count + float(self.alpha)) / (
                                outcome_count + (float(self.alpha) * len(self.probAtrib)))

    def prob_Atrib(self):
        # calcula a probabilidade de cada atributo
        for atrib in self.ClassesAtrib:
            atrib_vals = self.X_train[atrib].value_counts().to_dict()

            for atrib_val, count in atrib_vals.items():
                self.probAtrib[atrib][atrib_val] = count / self.train_size

    def predict(self, df):

        X, z = separar_coluna(df)
        resultados = []
        X = np.array(X)

        for query in X:
            probs_outcome = {}
            for outcome in np.unique(self.y_train):
                p_y = self.probY[outcome]
                probB_A = 1
                proB = 1

                for atrib, atrib_val in zip(self.ClassesAtrib, query):
                    if (str(atrib_val) + '_' + outcome) in self.ClassesAtrib[atrib]:
                        probB_A *= self.ClassesAtrib[atrib][str(atrib_val) + '_' + outcome]
                        proB *= self.probAtrib[atrib][atrib_val]
                if proB != 0:
                    probs_outcome[outcome] = (probB_A * p_y) + float(self.alpha) / proB
                else:
                    probs_outcome[outcome] = 0
            resultado = max(probs_outcome, key=lambda x: probs_outcome[x])
            resultados.append(resultado)
        return np.array(resultados)

    def accuracy_score(self, df):
        X, y = separar_coluna(df)
        y_pred = self.predict(X)
        return round(float(sum(y_pred == y)) / float(len(X)) * 100, 2)


if __name__ == "__main__":
    # recebe of ficheiros com os dados de treino e teste
    df = pd.read_csv("breast-cancer-train.csv")  # load dos dados de treino
    df2 = pd.read_csv("breast-cancer-test.csv")  # load dos dados de teste

    X, y = separar_coluna(df)
    nb_clf = NaiveBayesUevora()
    nb_clf.fit(X, y)

    print(nb_clf.predict(df2))
    print("exatidão=" + str(nb_clf.accuracy_score(df2)))