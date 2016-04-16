from __future__ import absolute_import, print_function, unicode_literals

import re
from streamparse.bolt import Bolt

################################################################################
# Function to check if the string contains only ascii chars
################################################################################
def ascii_string(s):
  return all(ord(c) < 128 for c in s)

class ParseTweet(Bolt):

    def process(self, tup):
        tweet = tup.values[0]  # extract the tweet

        # Split the tweet into words
        words = tweet.split()

        # Filter out the hash tags, RT, @ and urls
        valid_words = []
        for word in words:
           #TODO - 1. WE NEED TO CHECK IF THE WORD IS ONE OF THE KEYWORDS (MOVIE NAMES WE ARE LOOKING FOR)
           # WE NEED TO HAVE ACCESS TO A MAP IN THIS CLASS OF IMDBID -> KEYWORD. THAT WAY WE CAN LOOKUP THE TABLE BASED ON IMDBID
           #2. STORE THE TWEET INTO THE RAW_TWEETS TABLE IF WE FIND THAT THE WORD IS WHAT WE ARE LOOKING FOR
           #3. STORE THE METADATA ABOUT THE TWEET - LOCATION, ETC. ALSO IF THE FIRST CONDITION IS MET
           #4. DO THE SENTIMENT ANALYSIS AND COME UP WITH A VALUE
           #5. UPDATE THE TWEET STATISTICS TABLE WITH THE COUNTS.
        if not valid_words: return

        # Emit all the words
        self.emit_many(valid_words)

        # tuple acknowledgement is handled automatically
