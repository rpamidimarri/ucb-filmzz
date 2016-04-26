from __future__ import absolute_import, print_function, unicode_literals

import itertools, time
import tweepy, copy 
import Queue, threading
import simplejson as json
import psycopg2

from streamparse.spout import Spout

################################################################################
# Twitter credentials
################################################################################
twitter_credentials = {
    "consumer_key"        :  "",
    "consumer_secret"     :  "",
    "access_token"        :  "",
    "access_token_secret" :  "",
}

def auth_get(auth_key):
    if auth_key in twitter_credentials:
        return twitter_credentials[auth_key]
    return None

################################################################################
# Class to listen and act on the incoming tweets
################################################################################
class TweetStreamListener(tweepy.StreamListener):

    def __init__(self, listener):
        self.listener = listener
        super(self.__class__, self).__init__(listener.tweepy_api())

    def on_status(self, status):
        latitude=None
        longitude=None
        place=None
        if status.coordinates is not None:
          latitude = status.coordinates['coordinates'][1]
          longitude = status.coordinates['coordinates'][0]

        if status.place is not None:
           place=status.place
        self.listener.queue().put([status.text,latitude,longitude,place], timeout = 0.01)
        return True
 
    def on_error(self, status_code):
        return True # keep stream alive
  
    def on_limit(self, track):
        return True # keep stream alive

class Tweets(Spout):

    def initialize(self, stormconf, context):
        self._queue = Queue.Queue(maxsize = 100)

        consumer_key = auth_get("consumer_key") 
        consumer_secret = auth_get("consumer_secret") 
        auth = tweepy.OAuthHandler(consumer_key, consumer_secret)

        if auth_get("access_token") and auth_get("access_token_secret"):
            access_token = auth_get("access_token")
            access_token_secret = auth_get("access_token_secret")
            auth.set_access_token(access_token, access_token_secret)

        self._tweepy_api = tweepy.API(auth)

        # Create the listener for twitter stream
        listener = TweetStreamListener(self)

        # NOTE THAT THERE ARE SOME WORDS  WE NEED TO IGNORE, EVEN IF THERE ARE MOVIES NAMES BASED ON IT
        # THIS WILL HAVE TO BE IN A PROPERTIES FILE WHICH NEEDS TO BE UPDATED DAILY. OR IT SHOULD BE A COLUMN IN THE ACTIVE MOVIE TO IGNORE A MOVIE.
        ignored_titles=["24","fake","countdown", "the ticket", "parents"]
       
        # CODE TO GET THE PHRASES TO FILTER ON.. 
	maxCount = 0
        conn = psycopg2.connect(database="filmzz", user="postgres", password="pass", host="localhost", port="5432")
        cur = conn.cursor()
        cur.execute("SELECT max(executioncount) from ActiveMovie")
        result=cur.fetchone()
        if result != None:
            maxCount=result[0]

        self.log('Existing maxCount is %d' % (maxCount))
        cur.execute("SELECT tmdbid, searchtitle FROM ActiveMovie WHERE executioncount=%s and status = 'upcoming' and searchfortweets = 'true' order by tmdbpopularity::real DESC LIMIT 100", [maxCount])
        records = cur.fetchall()
        titlemap = {}
        titles=[]
        for rec in records:
            titles.append(unicode(rec[1], "utf-8")) 
        #self.log('Printing the Tmdb ids and the movie titles for UPCOMING MOVIES we are going to filter in tweets')
        # Create the stream and listen for english tweets
        #for running_title in running_titles:
           #self.log(running_title.encode('ascii','replace'))
        stream = tweepy.Stream(auth, listener, timeout=None) 
        stream.filter(languages=["en"], track=titles, async=True)

    def queue(self):
        return self._queue

    def tweepy_api(self):
        return self._tweepy_api

    def next_tuple(self):
        try:
            tweet_data = self.queue().get(timeout = 0.1)
            if tweet_data:
                self.queue().task_done()
                #self.log('Tweet=%s is from latitude=%s, longitude=%s, place=%s' %(tweet_data[0].encode('ascii','replace'),tweet_data[1],tweet_data[2],tweet_data[3]))
                country=""
                region=""
                place=tweet_data[3]
                if place is not None:
                   if place.country is not None:
                      country=place.country
                   if place.full_name is not None:
                      region=place.full_name
                self.emit([tweet_data[0],tweet_data[1],tweet_data[2],country.encode('ascii','replace'),region.encode('ascii','replace')])

        except Queue.Empty:
            self.log("Empty queue exception ")
            time.sleep(0.1)

    def ack(self, tup_id):
        pass  # if a tuple is processed properly, do nothing

    def fail(self, tup_id):
        pass  # if a tuple fails to process, do nothing
