import numpy as np
import pandas as pd
from classes import KNeighborsClassUE
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder

dataFrame = pd.read_csv('trabalho_51483_51820_51717/numericos/iris.csv')

X = dataFrame.iloc[:, :-1].values  
y = dataFrame.iloc[:, -1].values  

label_encoder = LabelEncoder()
y = label_encoder.fit_transform(y)

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.25, train_size=0.75, random_state=3)

kvalores = [1, 3, 4, 9]
pvalores = [1, 2]

for p in pvalores:
    for k in kvalores:
        KNN_UE = KNeighborsClassUE(k=k,p=p)
        KNN_UE.fit(X_train, y_train)

        accuracy = KNN_UE.score(X_test, y_test)

        
        print(f"K: {k} |P: {p} | KNN_UE: {accuracy} ")