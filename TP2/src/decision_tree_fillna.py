import pandas as pd
from sklearn import tree
from sklearn.model_selection import train_test_split
from sklearn.metrics import precision_score
from sklearn.metrics import recall_score
from sklearn.metrics import confusion_matrix
from sklearn.model_selection import KFold

dados = pd.read_csv("trab.csv")

target = dados['match']
inputs = dados.drop('match', axis='columns')
inputs = inputs.drop(inputs.columns[0],axis=1)
inputs = inputs.drop(columns=['id','partner'])

inputs.age = inputs.age.fillna(inputs.age.mean())
inputs.age_o = inputs.age_o.fillna(inputs.age_o.mean())
inputs.goal = inputs.goal.fillna(inputs.goal.mode()[0])
inputs.date = inputs.date.fillna(inputs.date.mode()[0])
inputs.int_corr = inputs.int_corr.fillna(inputs.int_corr.mean())
inputs.length = inputs.length.fillna(inputs.length.mode()[0])
inputs.met = inputs.met.fillna(inputs.met.mode()[0])
inputs.like = inputs.like.fillna(inputs.like.mean())
inputs.prob = inputs.prob.fillna(inputs.prob.mean())
inputs.go_out = inputs.go_out.fillna(inputs.go_out.mode()[0])

#print(inputs.columns[inputs.isna().any()])

x_train, x_test, y_train, y_test = train_test_split(inputs, target, test_size=0.3)

model = tree.DecisionTreeClassifier(criterion="entropy", max_depth=20, min_samples_leaf=5, min_samples_split=10,max_features=41)
model.fit(x_train, y_train)

p = precision_score(y_test,model.predict(x_test))
r = recall_score(y_test,model.predict(x_test))

print("precision " + str(p))
print("recall " + str(r))

#matriz de confusão
print(confusion_matrix(y_test,model.predict(x_test)))

#cross-validation
kf = KFold()

for train_index, test_index in kf.split(inputs):
    x_train, x_test = inputs.iloc[train_index], inputs.iloc[test_index]
    y_train, y_test = target.iloc[train_index], target.iloc[test_index]
    model.fit(x_train, y_train)
    print(model.score(x_test, y_test))

