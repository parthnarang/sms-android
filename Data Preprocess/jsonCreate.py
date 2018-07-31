# -*- coding: utf-8 -*-
"""
Created on Tue Jul 31 19:17:31 2018

@author: Rahul Nori
"""

import pandas as pd
from nltk.stem import PorterStemmer
#from nltk.corpus import stopwords
from sklearn.feature_extraction import text
import re
from sklearn.feature_extraction.text import CountVectorizer
import json
from collections import Counter

def clean(txt,remove_stops=False,stemming=False):
    txt = str(txt).lower()
    txt = re.sub(r'[^A-Za-z0-9\s]',r'',txt)
    txt = re.sub(r'\n',r' ',txt)
    txt = re.sub(r'\r',r' ',txt)
    
    if remove_stops:
        txt = " ".join([w for w in txt.split() if w not in stops])
    if stemming == True:
        ps = PorterStemmer()
        txt = " ".join([ps.stem(w) for w in txt.split()])
    return txt

path  = "data.csv"
x = pd.read_csv(path)
cols = list(x)
train = pd.DataFrame()

for col in cols:
    train[col] = []

for i in range(len(x)):
    if x["RESULT"][i] == 0 or x["RESULT"][i] == 1 or x["RESULT"][i] == 2 or x["RESULT"][i] == 3 or x["RESULT"][i] == 4:
        df = pd.DataFrame()
        for col in cols:
            df[col] = [x[col][i]]
        train = pd.concat([train,df]).reset_index(drop=True)

print train.head()

#stops = set(stopwords.word("english"))
stops = text.ENGLISH_STOP_WORDS

df = pd.DataFrame(train)
df['ADDRESS'] = df['ADDRESS'].map(lambda x:clean(x,True,False))
df['MESSAGE'] = df['MESSAGE'].map(lambda x:clean(x,True,False))
df['CNAME'] = df['CNAME'].map(lambda x:clean(x,True,False))

print df.head()

countVec = CountVectorizer(analyzer='word',ngram_range=(1,1),max_features=300)
bagofwords = countVec.fit_transform(df['MESSAGE'])
temp = dict(countVec.vocabulary_).keys()
features = [val.encode("utf-8") for val in temp]
mp = {}
cnt = {}
cnt['TOTAL'] = len(df.MESSAGE)
wordSet = Counter()

for cat in range(5):
    lst = {}
    c = 0
    for x in features:
        lst[x] = 0
    for i in range(len(df.MESSAGE)):
        if df['RESULT'][i] == cat:
            c += 1
            for x in features:
                if x in df['MESSAGE'][i]:
                    lst[x] += 1
    mp[cat] = lst
    cnt[cat] = c

for msg in df.MESSAGE:
    #print msg
    words = msg.split()
    for word in words:
        wordSet[word] = 1
    #print wordSet
    #raw_input()

result = {}
result['FEATURE'] = features
result['FREQUENCY'] = mp
result['COUNT'] = cnt
result['WORDSET'] = wordSet

with open('data.json','w') as f:
    json.dump(result,f)

print "Completed."