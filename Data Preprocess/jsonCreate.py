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
    txt = re.sub(r'[^A-Za-z\s]',r'',txt)
    txt = re.sub(r'\n',r' ',txt)
    txt = re.sub(r'\r',r' ',txt)
    
    if remove_stops:
        txt = " ".join([w for w in txt.split() if w not in stops])
    if stemming == True:
        ps = PorterStemmer()
        txt = " ".join([ps.stem(w) for w in txt.split()])
    return txt

def modifyAddress(val):
    #print type(val)
    try:
        newval = int(float(val))
        return ""
    except:
        #print "Address not an Int: ",e
        pass
    return " " + val

def modifyMessage(val):
    newmsg = ""
    for token in val.split():
        try:
            newval = int(float(token))
        except:
            newmsg = newmsg + " " + token
    return newmsg

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


df = pd.DataFrame(train)
#stops = set(stopwords.word("english"))
stops = text.ENGLISH_STOP_WORDS
print df['ADDRESS']
addresslst = df['ADDRESS'].values.tolist()
#df['ADDRESS'] = df['ADDRESS'].map(lambda x:addresslst.append(x))
print 'Address list before is ',addresslst[:50]
addresslst = map(lambda x: modifyAddress(x),addresslst)
print 'Address list is ',addresslst[:50]
addrl = pd.Series(addresslst)

df['MODADDR'] = addrl

print df.MODADDR
messages = df['MESSAGE'].values.tolist()
messages = map(lambda x: modifyMessage(x),messages)

messagepd = pd.Series(messages)
df['MESSAGE'] = messagepd


df['ADDRESS'] = df['ADDRESS'].map(lambda x:clean(x,True,False))
df['MESSAGE'] = df['MESSAGE'].map(lambda x:clean(x,True,False))
df['CNAME'] = df['CNAME'].map(lambda x:clean(x,True,False))

df['MESSAGE'] = df['MESSAGE'] + df['MODADDR']

print df.MESSAGE.tolist()[10:30]

countVec = CountVectorizer(analyzer='word',ngram_range=(1,1),max_features=300000)
bagofwords = countVec.fit_transform(df['MESSAGE'])
temp = dict(countVec.vocabulary_).keys()
#print len(temp)
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