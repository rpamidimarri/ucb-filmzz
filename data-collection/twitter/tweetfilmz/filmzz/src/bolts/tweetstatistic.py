##AV - Change filename to sentiment.py before actual compilation
##AV - Include citation for this module. <small> **Hutto, C.J. & Gilbert, E.E. (2014). VADER: A Parsimonious Rule-based Model for Sentiment Analysis of Social Media Text. Eighth International Conference on Weblogs and Social Media (ICWSM-14). cii', 'ignore')nn Arbor, MI, June 2014.** </small>
from __future__ import absolute_import, print_function 

import re
from streamparse.bolt import Bolt
import psycopg2
from vaderSentiment.vaderSentiment import sentiment as vaderSentiment

################################################################################
# This bolt takes a clean tweet which has a movie title embedded and returns the 
# positive, neutral, negative and compound sentiment values for it
################################################################################

conn = psycopg2.connect(database="filmzz", user="postgres", password="pass", host="localhost", port="5432")
cur = conn.cursor()

class TweetStatistic(Bolt):
    
    def initialize(self, stormconf, context):
        #conn = psycopg2.connect(database="filmzz", user="postgres", password="pass", host="localhost", port="5432")
        #cur = conn.cursor()              
        #currentCount = 0
        currentme = []

    def process(self, tup):
        id = tup.values[0]
        title = tup.values[1].encode('ascii', 'ignore')
        tweet = tup.values[2].encode('ascii', 'ignore')
        # get the good tweet sentence emitted by the Parse bolt 
        # Check if this is the data structure that is emitted by ParseTweet bolt
        # Get the sentiment values for the tweet. 
        vs = vaderSentiment(tweet) 
        # print "\n\t" + str(vs)
        # vs is a dictionary in the form of {'neg': value1, 'neu': value2, 'pos': value3, 'compound': value4}
        # Create a list version of the vs dictionary
        #vslist = list(vs.values())

        # Emit the sentiment values. This will be a set of 4 key-value pairs in JSON format
        # self.emit_many(vs)
        # tuple acknowledgement is handled automatically


        # Get existing running count, running values for positive, negative, neutral & compound sentiment 
        # based on keyword, tmdbid, and increment it by one (for the counter) and the sentiment values from vs.
        #conn = psycopg2.connect(database="filmzz", user="postgres", password="pass", host="localhost", port="5432")
        #cur = conn.cursor()
        currentCount=0
        currentSentiment=[]
        cur.execute("SELECT runningCount from TweetStatistic WHERE tmdbId=%s", [id])
        result=cur.fetchone()
        if result != None:
            currentCount=result[0]
      
        for key, value in vs.items():
	    currentSentiment.append(value) 
         
        if currentCount == 0:
            # Insert new rows into Tweets Statistic database with values for counter value, Movie Title and tmdbId
            currentCount = currentCount + 1
            cur.execute("INSERT INTO TweetStatistic (tmdbId, title, runningCount, runningNegativeSentiment, runningNeutralSentiment, runningPositiveSentiment, runningCompoundSentiment) VALUES (%s,%s,%s,%s,%s,%s,%s)", [id, title, currentCount, currentSentiment[0], currentSentiment[1], currentSentiment[2], currentSentiment[3]])
        else:
            cur.execute("SELECT runningNegativeSentiment, runningNeutralSentiment, runningPositiveSentiment, runningCompoundSentiment from TweetStatistic WHERE tmdbId=%s", [id])
            result=cur.fetchone()
            currentCount = currentCount + 1
            if result != None:
               runningNegativeSentiment=currentSentiment[0]+result[0]
               runningNeutralSentiment=currentSentiment[1]+result[1]
               runningPositiveSentiment=currentSentiment[2]+result[2]
               runningCompoundSentiment=currentSentiment[3]+result[3]
               self.log('Updating the statistics for id:%s' %(id))
               # Update the Tweets Statistic database
               cur.execute("UPDATE TweetStatistic SET runningCount = %s, runningNegativeSentiment = %s, runningNeutralSentiment = %s, runningPositiveSentiment=%s, runningCompoundSentiment = %s WHERE tmdbId = %s", (currentCount, runningNegativeSentiment, runningNeutralSentiment, runningPositiveSentiment, runningCompoundSentiment, id))
               conn.commit()
        

        


        


       

        

        
