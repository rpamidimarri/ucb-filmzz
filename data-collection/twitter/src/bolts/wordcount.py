from __future__ import absolute_import, print_function, unicode_literals

from collections import Counter
from streamparse.bolt import Bolt

import psycopg2

class WordCounter(Bolt):

    def initialize(self, conf, ctx):
        self.counts = Counter()

    def process(self, tup):
        word = tup.values[0]

        # Write codes to increment the word count in Postgres
        # Use psycopg to interact with Postgres
        # Database name: Tcount 
        # Table name: Tweetwordcount 
        # you need to create both the database and the table in advance.
        conn = psycopg2.connect(database="tcount", user="postgres", password="pass", host="localhost", port="5432")
        cur = conn.cursor()
        existingCount=0
        #Select
        cur.execute("SELECT count from Tweetwordcount where word=%s", [word])
        #AN EXAMPLE OF HOW TO ACCESS THE DB FROM THIS CODE
        #TODO - DO WE EVEN NEED THE SECOND BOLT? MAY BE WE CAN DO EVERYTHING IN THE FIRST BOLT! INVESTIGATE THIS.
