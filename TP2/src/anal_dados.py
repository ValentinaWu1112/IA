import pandas as pd

dados = pd.read_csv("trab.csv")
print(dados.info())


#númerica: age/age_o/int_corr
print(dados["age"].describe())
print(dados["age_o"].describe())
print(dados["int_corr"].describe())
print(dados["like"].describe())
print(dados["prob"].describe())

print(dados["age"].median())
print(dados["age_o"].median())
print(dados["int_corr"].median())
print(dados["like"].median())
print(dados["prob"].median())

# categóricos: goal/date/go_out/length/met/like/prob/match/
#print("goal")
print(dados["goal"].mode())
print(dados["goal"].value_counts())
#print("date")
print(dados["date"].mode())
print(dados["date"].value_counts())
#print("go_out")
print(dados["go_out"].mode())
print(dados["go_out"].value_counts())
#print("length")
print(dados["length"].mode())
print(dados["length"].value_counts())
#print("met")
print(dados["met"].mode())
print(dados["met"].value_counts())

#target: match
print(dados["match"].value_counts())
