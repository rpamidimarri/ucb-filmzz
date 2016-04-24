(ns tweetwordcount
  (:use     [streamparse.specs])
  (:gen-class))

(defn tweetwordcount [options]
   [
    ;; spout configuration
    {"running-movie-spout" (python-spout-spec
          options
          "spouts.runningmovies.Tweets"
          ["tweet"]
          :p 1
          )
     "upcoming-movie-spout" (python-spout-spec
          options
          "spouts.upcomingmovies.Tweets"
          ["tweet"]
          :p 1
          )
    }
    ;; bolt configuration
    {"parse-tweet-bolt" (python-bolt-spec
          options
          {"running-movie-spout" :shuffle,
           "upcoming-movie-spout" :shuffle}
          "bolts.parse.ParseTweet"
          ["id","title","tweet"]
          :p 2
          )
     "count-bolt" (python-bolt-spec
          options
          {"parse-tweet-bolt" ["id"]}
          "bolts.wordcount.WordCounter"
          ["title" "count"]
          :p 2
          )
    }
  ]
)
