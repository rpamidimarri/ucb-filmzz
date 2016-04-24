from __future__ import absolute_import, print_function, unicode_literals

import re
from streamparse.bolt import Bolt
import psycopg2
################################################################################
# Function to check if the string contains only ascii chars
################################################################################
running_titlemap={}
running_titles=[]
upcoming_titlemap={}
upcoming_titles=[]

def ascii_string(s):
  return all(ord(c) < 128 for c in s)

class ParseTweet(Bolt):
    def initialize(self, stormconf, context):
        # CODE TO GET THE PHRASES TO FILTER ON.. 
        maxCount = 0
        conn = psycopg2.connect(database="filmzz", user="postgres", password="pass", host="localhost", port="5432")
        cur = conn.cursor()
        cur.execute("SELECT max(executioncount) from ActiveMovie")
        result=cur.fetchone()
        if result != None:
            maxCount=result[0]

        self.log('Existing maxCount is %d' % (maxCount))
        cur.execute("SELECT tmdbid, tmdbtitle FROM ActiveMovie WHERE executioncount=%s and status = 'running' order by tmdbpopularity::real DESC LIMIT 100", [maxCount])
        records = cur.fetchall()
        running_titlemap = {}
        for rec in records:
            running_titlemap[rec[0]]=rec[1]

        self.log('Printing the Tmdb ids and the movie titles for RUNNING MOVIES we are going to filter in tweets')
        running_titles=[]
        for keys,values in running_titlemap.items():
            self.log(keys)
            #self.log(unicode(values, "utf-8"))
            running_titles.append(unicode(values, "utf-8"))


    def process(self, tup):
        tweet = tup.values[0]  # extract the tweet

        # Split the tweet into words
        words = tweet.split()

        # Filter out the hash tags, RT, @ and urls
        valid_words = []
        for word in words:
            # Filter the hash tags
            if word.startswith("#"): continue

            # Filter the user mentions
            if word.startswith("@"): continue

            # Filter out retweet tags
            if word.startswith("RT"): continue

            # Filter out the urls
            if word.startswith("http"): continue

            # Strip leading and lagging punctuations
            aword = word.strip("\"?><,'.:;)")

            # now check if the word contains only ascii
            if len(aword) > 0 and ascii_string(word):
                #1.INSERT INTO THE TWEETS TABLE TO STORE THE FULL TWEET
                #2.UPDATE THE TWEET STATISTIC TABLE TO UPDATE THE COUNT.
                #3.CALCULATE THE SENTIMENT OF THE TWEET AND STORE IT IN STATISTIC TABLE
                #REMOVE THIS CONTINUE BELOW WHEN YOU PUT IN THE ACTUAL CODE
                valid_words.append([aword]) 
                
        if not valid_words: return

        # Emit all the words
        self.emit_many(valid_words)

        # tuple acknowledgement is handled automatically
