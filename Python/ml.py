import pickle
import pandas as pd
from sklearn.preprocessing import StandardScaler, LabelEncoder
from sklearn.decomposition import PCA
from sklearn.model_selection import train_test_split
import seaborn as sns
import matplotlib.pyplot as plt
import numpy as np
from sklearn.model_selection import  KFold
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import confusion_matrix, classification_report, accuracy_score

# Loading the data
df = pd.read_csv('train.csv')


# Exploring the data
print(df.head())
print(df.info())


# Checking for missing values
print(df.isnull().sum())

#  battery_power  blue  clock_speed  dual_sim  ...  three_g  touch_screen  wifi  price_range
# 0            842     0          2.2         0  ...        0             0     1            1
# 1           1021     1          0.5         1  ...        1             1     0            2
# 2            563     1          0.5         1  ...        1             1     0            2
# 3            615     1          2.5         0  ...        1             0     0            2
# 4           1821     1          1.2         0  ...        1             1     0            1

# [5 rows x 21 columns]
# <class 'pandas.core.frame.DataFrame'>
# RangeIndex: 2000 entries, 0 to 1999
# Data columns (total 21 columns):
#  #   Column         Non-Null Count  Dtype  
# ---  ------         --------------  -----  
#  0   battery_power  2000 non-null   int64  
#  1   blue           2000 non-null   int64  
#  2   clock_speed    2000 non-null   float64
#  3   dual_sim       2000 non-null   int64  
#  4   fc             1995 non-null   float64
#  5   four_g         1995 non-null   float64
#  6   int_memory     1995 non-null   float64
#  7   m_dep          1995 non-null   float64
#  8   mobile_wt      1996 non-null   float64
#  9   n_cores        1996 non-null   float64
#  10  pc             1995 non-null   float64
#  11  px_height      1996 non-null   float64
#  12  px_width       1998 non-null   float64
#  13  ram            1998 non-null   float64
#  14  sc_h           1999 non-null   float64
#  15  sc_w           1999 non-null   float64
#  16  talk_time      2000 non-null   int64  
#  17  three_g        2000 non-null   int64  
#  18  touch_screen   2000 non-null   int64  
#  19  wifi           2000 non-null   int64  
#  20  price_range    2000 non-null   int64  
# dtypes: float64(13), int64(8)
# memory usage: 328.2 KB
# None
# battery_power    0
# blue             0
# clock_speed      0
# dual_sim         0
# fc               5
# four_g           5
# int_memory       5
# m_dep            5
# mobile_wt        4
# n_cores          4
# pc               5
# px_height        4
# px_width         2
# ram              2
# sc_h             1
# sc_w             1
# talk_time        0
# three_g          0
# touch_screen     0
# wifi             0
# price_range      0
# dtype: int64
categorical_features = ['blue', 'dual_sim', 'four_g', 'three_g', 'touch_screen', 'wifi']


numerical_features = ['battery_power', 'clock_speed', 'fc', 'int_memory', 'm_dep', 
                      'mobile_wt', 'n_cores', 'pc', 'px_height', 'px_width', 'ram', 
                      'sc_h', 'sc_w', 'talk_time']

#replacing missing numerical values with mean
df[numerical_features] = df[numerical_features].fillna(df[numerical_features].mean())

#replacing missing categorical values with mode
for feature in categorical_features:
    df[feature] = df[feature].fillna(df[feature].mode()[0])


#visualization using heatmap
plt.figure(figsize=(12, 8))
sns.heatmap(df.corr(), annot=True, fmt='.2f', cmap='coolwarm')
plt.show()

# there is a very strong positive correlation between ram and price (0.92)
# there is moderate correlation between the following :
#         px_height & px_width
#         pc & fc
#         three_g & four_g
#         sc_h & sc_w

# seperating the y label
X = df.drop('price_range', axis=1)
y = df['price_range']

# Splitting the data into 80% training and 20% testing 
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)


#normalizing numerical features
scaler = StandardScaler()
X_train[numerical_features]= scaler.fit_transform(X_train[numerical_features])
X_test[numerical_features] = scaler.transform(X_test[numerical_features])


# Training a Random Forest Classifier Model
model = RandomForestClassifier(random_state=42)

# Initializing KFold cross-validator
accuracy_scores = []
kf = KFold(n_splits=5, shuffle=True, random_state=42)
# K-Fold Cross Validation
for train_index, val_index in kf.split(X_train):
    X_train_fold, X_val_fold = X_train.iloc[train_index], X_train.iloc[val_index]
    y_train_fold, y_val_fold = y_train.iloc[train_index], y_train.iloc[val_index]
    
    # Training the model on the training fold
    model.fit(X_train_fold, y_train_fold)
    
    # Predicting the validation fold
    y_val_pred = model.predict(X_val_fold)
    
    # Calculating the accuracy score of the validation fold
    accuracy = accuracy_score(y_val_fold, y_val_pred)
    accuracy_scores.append(accuracy)

# Calculating the mean accuracy score of all the folds
mean_accuracy = sum(accuracy_scores) / len(accuracy_scores)

print("Cross-validation scores:")
print(accuracy_scores)
print("Mean cross-validation score:")
print(mean_accuracy)


# Predictions on the test set
y_pred = model.predict(X_test)

# Model evaluation
conf_matrix = confusion_matrix(y_test, y_pred)
class_report = classification_report(y_test, y_pred)

print("Confusion Matrix:")
print(conf_matrix)
print("Classification Report:")
print(class_report)

with open('model.pkl', 'wb') as f:
    pickle.dump((model, scaler), f)

# Cross-validation scores:
# [0.890625, 0.828125, 0.896875, 0.853125, 0.878125]
# Mean cross-validation score:
# 0.869375
# Confusion Matrix:
# [[100   5   0   0]
#  [  6  77   8   0]
#  [  0   7  78   7]
#  [  0   0  14  98]]
# Classification Report:
#               precision    recall  f1-score   support

#            0       0.94      0.95      0.95       105
#            1       0.87      0.85      0.86        91
#            2       0.78      0.85      0.81        92
#            3       0.93      0.88      0.90       112

#     accuracy                           0.88       400
#    macro avg       0.88      0.88      0.88       400
# weighted avg       0.89      0.88      0.88       400