import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.metrics import precision_score
from sklearn.metrics import recall_score
from sklearn.metrics import confusion_matrix
from sklearn.model_selection import KFold
from sklearn.naive_bayes import GaussianNB

dados = pd.read_csv("trab.csv")

inputs = dados.dropna()
target = inputs['match']
inputs = inputs.drop(inputs.columns[0] , axis=1)
inputs = inputs.drop(columns=['id' , 'partner' , 'match'])

x_train, x_test, y_train, y_test = train_test_split(inputs, target, test_size=0.3)

nb = GaussianNB()
nb.fit(x_train,y_train)


p = precision_score(y_test,nb.predict(x_test))
r = recall_score(y_test,nb.predict(x_test))

print("precision " + str(p))
print("recall " + str(r))

#matriz de confusão
print(confusion_matrix(y_test,nb.predict(x_test)))

#cross-validation
kf = KFold()

for train_index, test_index in kf.split(inputs):
    x_train, x_test = inputs.iloc[train_index], inputs.iloc[test_index]
    y_train, y_test = target.iloc[train_index], target.iloc[test_index]
    nb.fit(x_train, y_train)
    print(nb.score(x_test, y_test))