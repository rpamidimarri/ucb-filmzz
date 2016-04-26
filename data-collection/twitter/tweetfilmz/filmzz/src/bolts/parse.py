from __future__ import absolute_import, print_function, unicode_literals

import re
import time
from streamparse.bolt import Bolt
import psycopg2
################################################################################
# Function to check if the string contains only ascii chars
################################################################################
titlemap={}
titles=[]
database_connection = psycopg2.connect(database="filmzz", user="postgres", password="pass", host="localhost", port="5432")
cur = database_connection.cursor()
def ascii_string(s):
  return all(ord(c) < 128 for c in s)

def hasAnyMovieRelatedWord(tweet):
  movieWords=["movie","watch","theater","show","box","ticket","cinema"]
  for movieWord in movieWords:
     if movieWord in tweet:
        return True
#TODO: WE NEED TO MAKE SURE THAT WE DO NOT OPEN A NEW CONNECTION EVERYTIME WE GET A TWEET. WE NEED TO OPEN IT ONCE AND REUSE IT. INVESTIGATE WHY THAT DOES NOT WORK
def saveToDb(keys,values,tweet,latitude,longitude,country,region):
   if latitude == None:
      latitude=""
   if longitude == None:
      longitude="" 
   millis = str(int(round(time.time() * 1000)))
   #The primary key is a sequence based id (Did not want to rely on the timestamp (precision of milliseconds => if we get 2 tweets for a movie at the same time, it will fail
   #With the primary key as a sequence we will never fail 
   #TODO 2. I ADDED FIELDS FOR THE INDIVIDUAL (NOT RUNNING) SENTIMENT SCORES FOR A PARTICULAR TWEET. We should explore if we can update this Tweets table in
   #the Tweet statistic bolt - if we want to do that, we can pass in the Tweet id (generated once we insert into the Tweets table) into the statistic bolt
   cur.execute("INSERT INTO Tweets(tmdbId,tweetTime,keyword,fullTweet,latitude,longitude,country,region) VALUES(%s,%s,%s,%s,%s,%s,%s,%s)", [keys,millis,values,tweet,latitude,longitude,country,region]);
   database_connection.commit()
   #database_connection.close()

class ParseTweet(Bolt):
    def initialize(self, stormconf, context):
        maxCount = 0
        database_connection = psycopg2.connect(database="filmzz", user="postgres", password="pass", host="localhost", port="5432")
        cur = database_connection.cursor()
        #max of execution count gives the value of the latest run => using this, we can get the latest active movies.
        cur.execute("SELECT max(executioncount) from ActiveMovie")
        result=cur.fetchone()
        if result != None:
            maxCount=result[0]
        
        self.log('Existing maxCount is %d' % (maxCount))
        #The idea with this query and the one below is - We will get all the title, ids of the running and upcoming movies based on the top tmd popularity and limit to 100
        # we need to do this, because we want to let twitter figure out if a title is present in the movie (by passing in the track property to the filter with a list of all the
        # movie titles. 
        cur.execute("SELECT tmdbid, searchtitle FROM ActiveMovie WHERE executioncount=%s and status = 'running' and searchfortweets = 'true' order by tmdbpopularity::real DESC LIMIT 100", [maxCount])
        records = cur.fetchall()
        #self.log('Printing the Tmdb ids and the movie titles for RUNNING MOVIES we are going to filter in tweets')
        #All this unicode thing is very important in python
        for rec in records:
            #note how we are adding only the lower case to the list - necessary since we need to check if these titles are present case agnostic in the tweet
            unicodevalue = unicode(rec[1].lower(), "utf-8")
            titlemap[rec[0]]=unicodevalue
            titles.append(unicodevalue) 
        
        cur.execute("SELECT tmdbid, searchtitle FROM ActiveMovie WHERE executioncount=%s and status = 'upcoming' and searchfortweets = 'true' order by tmdbpopularity::real DESC LIMIT 100", [maxCount])
        records = cur.fetchall()
        #self.log('Printing the Tmdb ids and the movie titles for UPCOMING MOVIES we are going to filter in tweets')
        for rec in records:
            unicodevalue = unicode(rec[1].lower(), "utf-8")
            titlemap[rec[0]]=unicodevalue
            titles.append(unicodevalue) 
        
        database_connection.close()
     
    def process(self, tup):
        #if we do the lower case, we can check for the existence of movie titles better
        tweet = tup.values[0].lower()  # extract the tweet
        latitude=tup.values[1]
        longitude=tup.values[2]
        country=tup.values[3]
        region=tup.values[4]
        #self.log('Found tweet :%s' %(tweet.encode('ascii','replace'))) 
        foundMatch = False
        for keys,values in titlemap.items():
           #a movie title being present in the tweet is not sufficient - some movie names (like 24, parents, etc.) are present in lot of tweets
           #so, we do one extra level of filtering - filter based on some of the common "movie" associated words - like "movie", "theater", "cinema", etc.
           if values in tweet:
              if hasAnyMovieRelatedWord(tweet):
                 self.log("Match Found! The keyword:%s for id:%s found in tweet:%s, Region=%s, Emitting the tweet" %(values.encode('ascii','replace'),keys,tweet.encode('ascii','replace'),region.encode('ascii','replace')))
                 saveToDb(keys,values.encode('ascii','replace'),tweet.encode('ascii','replace'),latitude,longitude,country.encode('ascii','replace'),region.encode('ascii','replace'))
                 foundMatch=True 
                 self.emit([keys,values,tweet])
                 break	
              #else:
                 #self.log("Match Found BUT NO MOVIE RELATED WORD FOUND! The keyword:%s for id:%s found in tweet:%s" %(values.encode('ascii','replace'),keys,tweet.encode('ascii','replace')))
   
        #if not foundMatch:
           #self.log('Did not find a match for the tweet :%s' %(tweet.encode('ascii','replace')))
        # tuple acknowledgement is handled automatically
