import numpy as np
import pandas as pd
from classes import NBayesClassUE
from sklearn.model_selection import train_test_split

dataFrame = pd.read_csv('trabalho_51483_51820_51717/nominais/bc-nominal.csv')

X = dataFrame.iloc[:, :-1].values  
y = dataFrame.iloc[:, -1].values  

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.25, train_size=0.75, random_state=3)

valoresalpha=[0, 1, 3, 5]

for alpha in valoresalpha:
        NBUE = NBayesClassUE(alpha=alpha)
        NBUE.fit(X_train, y_train)

        accuracy = NBUE.score(X_test, y_test)

     
       
        print(f"alpha: {alpha} | NBUE: {accuracy} ")
