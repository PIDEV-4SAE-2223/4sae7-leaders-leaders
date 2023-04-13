#!/usr/bin/env python
# coding: utf-8

# In[49]:


import pickle 

import matplotlib.pyplot as plt
import seaborn as sns 
import pandas as pd
import numpy as np

from sklearn.linear_model import LogisticRegression

from sklearn.preprocessing import StandardScaler

from sklearn.neighbors import KNeighborsClassifier

from sklearn.model_selection import RandomizedSearchCV, train_test_split, KFold 
from sklearn import svm

from sklearn.metrics import roc_curve, classification_report, confusion_matrix
from sklearn.metrics import precision_score, accuracy_score, recall_score, precision_recall_curve, f1_score, roc_auc_score
get_ipython().run_line_magic('matplotlib', 'inline')

from sklearn.naive_bayes import GaussianNB
from sklearn.ensemble import RandomForestClassifier

from sklearn.utils.class_weight import compute_class_weight


# In[50]:


with open('df.pickle','rb') as read_file:
    df = pickle.load(read_file)


# In[51]:


df.head()


# In[52]:


pd.options.display.max_rows = 4000
df.groupby('no_show').describe().transpose()


# # Exploratory Data Analysis 

# In[53]:


df.no_show.value_counts(normalize=True) ##about 20% of patients won't show up


# In[54]:


df.describe()


# In[55]:


df_eda = df.copy()


# In[56]:


### https://towardsdatascience.com/attribute-relevance-analysis-in-python-iv-and-woe-b5651443fc04
### referencing this articles for WoE and IV 


# In[57]:


### Binning categorical data 

df_eda['age_bins'] = pd.qcut(df_eda['age'],10, duplicates='drop')
df_eda['schedule_hour_bins'] = pd.qcut(df_eda['schedule_hour'],3, duplicates='drop')
df_eda['schedule_day_bins'] = pd.qcut(df_eda['schedule_day'],4, duplicates='drop')
df_eda['appointment_day_bins'] = pd.qcut(df_eda['appointment_day'],4, duplicates='drop')
df_eda['day_difference_bins'] = pd.qcut(df_eda['day_difference'],5, duplicates='drop')
df_eda['prior_appointments_bins'] = pd.qcut(df_eda['prior_appointments'], 4, duplicates = 'drop')


# In[58]:


df_eda.drop(columns = ['age', 'schedule_hour', 'schedule_day', 'appointment_day', 'day_difference',
                       'prior_appointments'], inplace=True)


# In[59]:


def calculate_woe_iv(dataset, feature, target):
    lst = []
    for i in range(dataset[feature].nunique()):
        val = list(dataset[feature].unique())[i]
        lst.append({
            'Value': val,
            'All': dataset[dataset[feature] == val].count()[feature],
            'Show': dataset[(dataset[feature] == val) & (dataset[target] == 0)].count()[feature],
            'No_show': dataset[(dataset[feature] == val) & (dataset[target] == 1)].count()[feature]
        })
        
    dset = pd.DataFrame(lst)
    dset['Distr_show'] = dset['Show'] / dset['Show'].sum()
    dset['Distr_no_show'] = dset['No_show'] / dset['No_show'].sum()
    dset['WoE'] = np.log(dset['Distr_show'] / dset['Distr_no_show'])
    dset = dset.replace({'WoE': {np.inf: 0, -np.inf: 0}})
    dset['IV'] = (dset['Distr_show'] - dset['Distr_no_show']) * dset['WoE']
    iv = dset['IV'].sum()
    
    dset = dset.sort_values(by='WoE')
    
    return dset, iv


# In[60]:


for col in df_eda.columns:
    if col == 'no_show': continue
    else:
        print('WoE and IV for column: {}'.format(col))
        df_woe_iv, iv = calculate_woe_iv(df_eda, col, 'no_show')
        print(df_woe_iv)
        print('IV score: {:.2f}'.format(iv))
        print('\n')


# In[61]:


df = df[['hypertension','sms','prior_noshows','age', 'schedule_hour', 
         'schedule_day', 'day_difference', 'no_show']]


# In[62]:


#sns.pairplot(df, hue='no_show')


# In[63]:


df.corr()


# In[64]:


from sklearn.cluster import KMeans
import matplotlib.pyplot as plt
data = df
scaler = StandardScaler()
data_scaled = scaler.fit_transform(data)

# Perform KMeans clustering with 2 clusters
kmeans = KMeans(n_clusters=2, random_state=42)
kmeans.fit(data_scaled)

# Get the cluster labels
labels = kmeans.labels_

# Add the labels to the original dataset
data['Cluster'] = labels

# Visualize the results
plt.scatter(data['age'], data['day_difference'], c=data['Cluster'], cmap='rainbow')
plt.xlabel('Age')
plt.ylabel('Day difference')
plt.title('KMeans Clustering')
plt.show()



# In[48]:


plt.scatter(data['age'], data['no_show'], c=data['Cluster'], cmap='rainbow')
plt.xlabel('Age')
plt.ylabel('no_show')
plt.title('KMeans Clustering')
plt.show()
 


# In[42]:


# Standardize the data
scaler = StandardScaler()
scaled_df = scaler.fit_transform(df)

# Perform PCA
pca = PCA(n_components=2)
principal_components = pca.fit_transform(scaled_df)

# Convert to pandas DataFrame
pca_df = pd.DataFrame(principal_components, columns=["PC1", "PC2"])

# Plot the results
plt.scatter(pca_df["PC1"], pca_df["PC2"])
plt.xlabel("PC1")
plt.ylabel("PC2")
plt.title("PCA Results")
plt.show()


# In[43]:


means = KMeans(n_clusters=2, random_state=0).fit(X_pca)

# Visualize the results using a scatter plot
plt.scatter(X_pca[:, 0], X_pca[:, 1], c=kmeans.labels_)
plt.title('KMeans Clustering')
plt.xlabel('Principal Component 1')
plt.ylabel('Principal Component 2')
plt.show()


# # Train-Test Split 

# In[17]:


X, y = df.drop(['no_show'],axis=1), df['no_show'] 
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=44)
X_train, y_train = np.array(X_train), np.array(y_train) 
kf = KFold(n_splits=5, shuffle=True, random_state = 19)


# In[18]:


### calculating class weights because of imbalanced dataset, but the non-weighted models performed better 

# classes = [0,1]
# cw = compute_class_weight('balanced', classes, df.no_show)
# cw


# Models used:
# 1) Logistic Regression
# 2) K-nearest Neighbour (hyperparameters tuned using RandomSearchCV)
# 3) SVM with radial kernel trick (eventually gave up on this because my laptop was unable to handle computing probabilities) 
# 4) Naive Bayes 

# ## Log Regression

# Accuracy: 0.8158131433151314 <p>
# Recall: 0.133765111575112 <p>
# Precision: 0.6955719204501621 <p>
# AUC: 0.5596017387021316 <p>

# In[19]:


log_accuracy = []
log_recall = []
log_precision = []
log_roc_auc = []

for train, val in kf.split(X_train, y_train):
    X_trainfold, y_trainfold = X_train[train], y_train[train]
    X_valfold, y_valfold = X_train[val], y_train[val] 
    
    log_reg = LogisticRegression(solver= 'liblinear')
    
    scaler = StandardScaler()
    scaled_X_trainfold = scaler.fit_transform(X_trainfold)
    scaled_X_valfold = scaler.transform(X_valfold)
    
    log_reg.fit(scaled_X_trainfold, y_trainfold)
    #y_predict = (log_reg.predict_proba(scaled_X_valfold)[:,1] > 0.22)
    y_predict = log_reg.predict(scaled_X_valfold)
    log_accuracy.append(accuracy_score(y_valfold, y_predict))
    log_recall.append(recall_score(y_valfold, y_predict))
    log_precision.append(precision_score(y_valfold, y_predict))
    log_roc_auc.append(roc_auc_score(y_valfold,y_predict))


# In[20]:


print(np.mean(log_accuracy))
print(np.mean(log_recall))
print(np.mean(log_precision))
print(np.mean(log_roc_auc))


# In[21]:


with open("log_reg.pkl", "wb") as f:
    pickle.dump(log_reg, f)


# ## K-Nearest Neighbour

# Accuracy: 0.8190247270984538 <p>
# Recall: 0.18928845564958027 <p>
# Precision: 0.6589771876195176 <p>
# ROC: 0.5824781354129602 <p> 

# ### RandomizedSearchCV for tuning hyperparameters

# In[24]:


scaler = StandardScaler()
scaled_X_train = scaler.fit_transform(X_train)
scaled_X_test = scaler.transform(X_test)

k_range = list(range(1, 31))
param_dist = dict(n_neighbors=k_range)
rand = RandomizedSearchCV(knn, param_dist, cv=3, scoring='accuracy', verbose=2, random_state=42, n_jobs = -1)
rand.fit(scaled_X_train, y_train)

rand.best_params_


# ### KNN model using RandomizedSearchCV best parameters

# In[35]:


knn_accuracy = []
knn_recall = []
knn_precision = []
knn_roc_auc = []
from matplotlib.colors import ListedColormap

for train, val in kf.split(X_train, y_train):
    X_trainfold, y_trainfold = X_train[train], y_train[train]
    X_valfold, y_valfold = X_train[val], y_train[val]
    
    knn = KNeighborsClassifier(n_neighbors=28) ###seting optimum to an odd number 
    
    scaler = StandardScaler()
    scaled_X_trainfold = scaler.fit_transform(X_trainfold)
    scaled_X_valfold = scaler.transform(X_valfold)
    
    knn.fit(scaled_X_trainfold, y_trainfold)
    y_predict = knn.predict(scaled_X_valfold)
    
    knn_accuracy.append(accuracy_score(y_valfold, y_predict))
    knn_recall.append(recall_score(y_valfold, y_predict))
    knn_precision.append(precision_score(y_valfold, y_predict))
    knn_roc_auc.append(roc_auc_score(y_valfold,y_predict))


    
        
    


# In[36]:


print(np.mean(knn_accuracy))
print(np.mean(knn_recall))
print(np.mean(knn_precision))
print(np.mean(knn_roc_auc))


# In[30]:


import matplotlib.pyplot as plt

# Plot KNN accuracy scores
plt.bar(range(len(knn_accuracy)), knn_accuracy)
plt.title('KNN Accuracy Scores')
plt.xlabel('Fold')
plt.ylabel('Accuracy')
plt.show()

# Plot KNN recall scores
plt.bar(range(len(knn_recall)), knn_recall)
plt.title('KNN Recall Scores')
plt.xlabel('Fold')
plt.ylabel('Recall')
plt.show()

# Plot KNN precision scores
plt.bar(range(len(knn_precision)), knn_precision)
plt.title('KNN Precision Scores')
plt.xlabel('Fold')
plt.ylabel('Precision')
plt.show()

# Plot KNN ROC AUC scores
plt.bar(range(len(knn_roc_auc)), knn_roc_auc)
plt.title('KNN ROC AUC Scores')
plt.xlabel('Fold')
plt.ylabel('ROC AUC')
plt.show()


# In[27]:


with open("knn.pkl", "wb") as f:
    pickle.dump(knn, f)


# ## Support Vector Machine

# ### RandomizedSearchCV for tuning hyperparameters
# 
# reference: https://medium.com/@aneesha/svm-parameter-tuning-in-scikit-learn-using-gridsearchcv-2413c02125a0

# In[ ]:


scaler = StandardScaler()
scaled_X_train = scaler.fit_transform(X_train)
scaled_X_test = scaler.transform(X_test)

svm_random_grid = {'C': [0.001, 0.01, 0.1, 1, 10],
 'gamma': [0.001, 0.01, 0.1, 1]} #'class_weight':[{0:0.62550473,1:2.49195678}]}

svm_random = RandomizedSearchCV(estimator = svm.SVC(kernel ='rbf'), param_distributions = svm_random_grid,
                                n_iter = 10, cv = 3, verbose=2, random_state=42, n_jobs = -1)
# Fit the random search model
svm_random.fit(scaled_X_train, y_train)
svm_random.best_params_


# ### SVM Radial using RandomizedSearchCV best parameters
# 
# Accuracy: 0.821654277792908 <p>
# Recall: 0.1535268532949164 <p> 
# Precision: 0.7578844329944994 <p>
# AUC: 0.57067542143612

# In[ ]:


svmrbf_accuracy = []
svmrbf_recall = []
svmrbf_precision = []
svmrbf_roc_auc = []

for train, val in kf.split(X_train, y_train):
    X_trainfold, y_trainfold = X_train[train], y_train[train]
    X_valfold, y_valfold = X_train[val], y_train[val]
    
    scaler = StandardScaler()
    scaled_X_trainfold = scaler.fit_transform(X_trainfold)
    scaled_X_valfold = scaler.transform(X_valfold)
    
    svm_rbf = svm.SVC(kernel="rbf", gamma=0.1, C=10, probability = True)
    svm_rbf.fit(scaled_X_trainfold, y_trainfold)
    
    y_predict = svm_rbf.predict(scaled_X_valfold)
    
    svmrbf_accuracy.append(accuracy_score(y_valfold, y_predict))
    svmrbf_recall.append(recall_score(y_valfold, y_predict))
    svmrbf_precision.append(precision_score(y_valfold, y_predict))
    svmrbf_roc_auc.append(roc_auc_score(y_valfold, y_predict))


# In[ ]:


print(np.mean(svmrbf_accuracy))
print(np.mean(svmrbf_recall))
print(np.mean(svmrbf_precision))
print(np.mean(svmrbf_roc_auc))


# In[ ]:


with open("svm_rbf.pkl", "wb") as f:
    pickle.dump(svm_rbf, f)


# ### SVM Radial with class weight using RandomizedSearchCV best params for class weight 
# 
# Accuracy: 0.7988117838491379 <p>
# Recall: 0.16890676075436756 <p> 
# Precision: 0.5501785181618126 <p>
# AUC: 

# In[28]:


svmrbfcw_accuracy = []
svmrbfcw_recall = []
svmrbfcw_precision = []
svmrbfcw_roc_auc = []

for train, val in kf.split(X_train, y_train):
    X_trainfold, y_trainfold = X_train[train], y_train[train]
    X_valfold, y_valfold = X_train[val], y_train[val]
    
    scaler = StandardScaler()
    scaled_X_trainfold = scaler.fit_transform(X_trainfold)
    scaled_X_valfold = scaler.transform(X_valfold)
    
    svm_rbfcw = svm.SVC(kernel="rbf", gamma=0.01, C=0.001, class_weight = {0:0.62550473,1:2.49195678}) 
    svm_rbfcw.fit(scaled_X_trainfold, y_trainfold)
    
    y_predict = svm_rbfcw.predict(scaled_X_valfold)
    
    svmrbfcw_accuracy.append(accuracy_score(y_valfold, y_predict))
    svmrbfcw_recall.append(recall_score(y_valfold, y_predict))
    svmrbfcw_precision.append(precision_score(y_valfold, y_predict))
    svmrbfcw_roc_auc.append(roc_auc_score(y_valfold, y_predict))


# In[29]:


print(np.mean(svmrbfcw_accuracy))
print(np.mean(svmrbfcw_recall))
print(np.mean(svmrbfcw_precision))
print(np.mean(svmrbfcw_roc_auc))


# # Naive Bayes 

# Accuracy: 0.814086945060836 <p>
# Recall: 0.1997979921113334 <p>
# Precision: 0.6002664846978236 <p>
# AUC: 0.583335933076093

# In[32]:


nb_accuracy = []
nb_recall = []
nb_precision = []
nb_roc_auc = []

for train, val in kf.split(X_train, y_train):
    X_trainfold, y_trainfold = X_train[train], y_train[train]
    X_valfold, y_valfold = X_train[val], y_train[val]
    
    scaler = StandardScaler()
    scaled_X_trainfold = scaler.fit_transform(X_trainfold)
    scaled_X_valfold = scaler.transform(X_valfold)
    
    nb = GaussianNB()
    nb.fit(scaled_X_trainfold, y_trainfold)

    y_predict = nb.predict(scaled_X_valfold)
    
    nb_accuracy.append(accuracy_score(y_valfold, y_predict))
    nb_recall.append(recall_score(y_valfold, y_predict))
    nb_precision.append(precision_score(y_valfold, y_predict))
    nb_roc_auc.append(roc_auc_score(y_valfold, y_predict))


# In[33]:


print(np.mean(nb_accuracy))
print(np.mean(nb_recall))
print(np.mean(nb_precision))
print(np.mean(nb_roc_auc))


# In[44]:


with open("nb.pkl", "wb") as f:
    pickle.dump(nb, f)


# ## Random Forest

# ### RandomizedSearchCV for tuning hyperparameters
# 
# reference: https://towardsdatascience.com/hyperparameter-tuning-the-random-forest-in-python-using-scikit-learn-28d2aa77dd74

# In[ ]:


scaler = StandardScaler()
scaled_X_train = scaler.fit_transform(X_train)
scaled_X_test = scaler.transform(X_test)

random_grid = {'max_depth': [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, None],
 'max_features': ['auto', 'sqrt'],
 'min_samples_leaf': [1, 2, 4],
 'min_samples_split': [2, 5, 10],
 'n_estimators': [200, 400, 600, 800, 1000, 1200, 1400, 1600, 1800, 2000],
              'class_weight':[{0:0.62550473,1:2.49195678}]}
 
rf_random = RandomizedSearchCV(estimator = RandomForestClassifier() , scoring = 'accuracy', param_distributions = random_grid
                               , n_iter = 10, cv = 3, verbose=2, random_state=42, n_jobs = -1)
# Fit the random search model
rf_random.fit(scaled_X_train, y_train)
rf_random.best_params_


# In[ ]:


rf_random.best_estimator_


# ### Randomforest using RandomizedSearchCV best parameters
# 
# Accuracy: 0.8249662752586012 <p>
# Recall: 0.16894635563896582 <p> 
# Precision: 0.7794004157029264 <p>
# AUC: 0.5785734817445194

# In[36]:


rf_accuracy = []
rf_recall = []
rf_precision = []
rf_roc_auc = []

for train, val in kf.split(X_train, y_train):
    X_trainfold, y_trainfold = X_train[train], y_train[train]
    X_valfold, y_valfold = X_train[val], y_train[val]
    
    scaler = StandardScaler()
    scaled_X_trainfold = scaler.fit_transform(X_trainfold)
    scaled_X_valfold = scaler.transform(X_valfold)
    
    rf = RandomForestClassifier(n_estimators = 400, min_samples_split = 2, min_samples_leaf = 2, 
                                max_features = 'sqrt', max_depth = 10, n_jobs = -1)
    rf.fit(scaled_X_trainfold, y_trainfold)

    y_predict = rf.predict(scaled_X_valfold)
    
    rf_accuracy.append(accuracy_score(y_valfold, y_predict))
    rf_recall.append(recall_score(y_valfold, y_predict))
    rf_precision.append(precision_score(y_valfold, y_predict))
    rf_roc_auc.append(roc_auc_score(y_valfold, y_predict))


# In[37]:


print(np.mean(rf_accuracy))
print(np.mean(rf_recall))
print(np.mean(rf_precision))
print(np.mean(rf_roc_auc))


# In[45]:


with open("rf.pkl", "wb") as f:
    pickle.dump(rf, f)


# ### RandomForest with class weight using RandomizedSearchCV best params for class weight 
# 
# Accuracy: 0.7878117460940285 <p>
# Recall: 0.34327949214184883 <p> 
# Precision: 0.45657089201211465 <p>
# AUC: 

# In[ ]:


rfcw_accuracy = []
rfcw_recall = []
rfcw_precision = []
rfcw_roc_auc = []

for train, val in kf.split(X_train, y_train):
    X_trainfold, y_trainfold = X_train[train], y_train[train]
    X_valfold, y_valfold = X_train[val], y_train[val]
    
    scaler = StandardScaler()
    scaled_X_trainfold = scaler.fit_transform(X_trainfold)
    scaled_X_valfold = scaler.transform(X_valfold)
    
    rfcw = RandomForestClassifier(n_estimators = 400, min_samples_split = 2, min_samples_leaf = 2, 
                              max_features = 'sqrt', max_depth = 10, class_weight= {0:0.62550473,1:2.49195678}, n_jobs = -1)
   
    rfcw.fit(scaled_X_trainfold, y_trainfold)

    y_predict = rf.predict(scaled_X_valfold)
    
    rfcw_accuracy.append(accuracy_score(y_valfold, y_predict))
    rfcw_recall.append(recall_score(y_valfold, y_predict))
    rfcw_precision.append(precision_score(y_valfold, y_predict))
    rfcw_roc_auc.append(roc_auc_score(y_valfold, y_predict))


# In[ ]:


print(np.mean(rfcw_accuracy))
print(np.mean(rfcw_recall))
print(np.mean(rfcw_precision))
print(np.mean(rfcw_roc_auc))


# # Choosing between models 

# In[30]:


with open("log_reg.pkl", "rb") as f:
    log_reg = pickle.load(f)

with open("nb.pkl", "rb") as f:
    nb = pickle.load(f)

with open("knn.pkl", "rb") as f:
    knn = pickle.load(f)

with open("rf.pkl", "rb") as f:
    rf = pickle.load(f)


# In[31]:


### create train and validation set to plot ROC curves
X, y = df.drop(['no_show'],axis=1), df['no_show'] 
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=44)
X_train, X_val, y_train, y_val = train_test_split(X_train, y_train, test_size=0.25, random_state=44)
X_train, y_train, X_val, y_val = np.array(X_train), np.array(y_train), np.array(X_val), np.array(y_val)

scaler = StandardScaler()
scaled_X_train = scaler.fit_transform(X_train)
scaled_X_val = scaler.transform(X_val)


# reference: https://abdalimran.github.io/2019-06-01/Drawing-multiple-ROC-Curves-in-a-single-plot

# In[116]:


# Instantiate the classfiers and make a list
classifiers = [log_reg, nb, knn, rf]

# Define a result table as a DataFrame
result_table = pd.DataFrame(columns=['classifiers', 'fpr','tpr','auc'])

# Train the models and record the results
for cls in classifiers:
    model = cls.fit(scaled_X_train, y_train)
    yproba = model.predict_proba(scaled_X_val)[::,1]
    
    fpr, tpr, _ = roc_curve(y_val,  yproba)
    auc = roc_auc_score(y_val, yproba)
    
    result_table = result_table.append({'classifiers':cls.__class__.__name__,
                                        'fpr':fpr, 
                                        'tpr':tpr, 
                                        'auc':auc}, ignore_index=True)

# Set name of the classifiers as index labels
result_table.set_index('classifiers', inplace=True)
#Plot the figure
fig = plt.figure(figsize=(8,6))

for i in result_table.index:
    plt.plot(result_table.loc[i]['fpr'], 
             result_table.loc[i]['tpr'], 
             label="{}, AUC={:.3f}".format(i, result_table.loc[i]['auc']))
    
plt.plot([0,1], [0,1], color='black', linestyle='--')

plt.xticks(np.arange(0.0, 1.1, step=0.1))
plt.xlabel("Flase Positive Rate", fontsize=15)

plt.yticks(np.arange(0.0, 1.1, step=0.1))
plt.ylabel("True Positive Rate", fontsize=15)

plt.title('ROC Curve Analysis', fontweight='bold', fontsize=15)
plt.legend(prop={'size':13}, loc='lower right')

plt.show()


# In[117]:


fig.savefig('multiple_roc_curve.png', dpi=300)


# Let's go with randomforest!
# 
# ## Threshold Tuning

# In[122]:


precision_curve, recall_curve, threshold_curve = precision_recall_curve(y_train, rf.predict_proba(scaled_X_train)[:,1] )

fig = plt.figure(figsize=(8,6))
plt.plot(threshold_curve, precision_curve[1:],label='precision')
plt.plot(threshold_curve, recall_curve[1:], label='recall')
plt.legend(loc='lower left')
plt.xlabel('Threshold')
plt.title('Precision and Recall Curves');


# In[123]:


fig.savefig('precision_recall_curve.png', dpi=300)


# In[63]:


### curves appear to taper off at about 0.4 threshold 
y_predict = (rf.predict_proba(scaled_X_val)[:, 1] >= 0.20).astype('int')
print(classification_report(y_val, y_predict))


# ## Retrain and Test

# In[45]:


### create train and test sets
X, y = df.drop(['no_show'],axis=1), df['no_show'] 
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=44)

scaler = StandardScaler()
scaled_X_train = scaler.fit_transform(X_train)
scaled_X_test = scaler.transform(X_test)


# In[52]:


### re-training on 80% of data 
rf.fit(scaled_X_train, y_train)
y_predict = (rf.predict_proba(scaled_X_test)[:, 1] >= 0.4).astype('int')


# In[53]:


print(classification_report(y_test, y_predict))


# # Inspecting RF

# In[134]:


feature_importance = pd.DataFrame(rf.feature_importances_,
                                   index = X_train.columns,
                                    columns=['Importance']).sort_values('Importance', ascending=False)


# In[136]:


feature_importance


# In[125]:


df_noshow = df[df['no_show']==1]
df_show = df[df['no_show']==0]


# In[126]:


fig = plt.figure(figsize=(8,6))
sns.distplot(df_show["age"] , color="grey", label="Show", hist = False)
sns.distplot(df_noshow["age"] , color="#bd0026", label="No Show", hist = False)
sns.despine()
plt.legend()
fig.savefig('age.png', dpi=300)


# In[127]:


fig = plt.figure(figsize=(8,6))
sns.distplot( df_show["day_difference"] , color="grey", label="Show", hist=False)
sns.distplot( df_noshow["day_difference"] , color="#bd0026", label="No Show", hist=False)
sns.despine()
plt.legend()
fig.savefig('day_difference.png', dpi=300)


# In[128]:


fig = plt.figure(figsize=(8,6))
sns.distplot( df_show["prior_noshows"] , color="grey", label="Show", hist=False)
sns.distplot( df_noshow["prior_noshows"] , color="#bd0026", label="No Show", hist=False)
sns.despine()
plt.legend()
fig.savefig('day_difference.png', dpi=300)


# In[ ]:





# In[ ]:





# In[ ]:




