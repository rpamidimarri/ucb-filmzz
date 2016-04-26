(ns tweetwordcount
  (:use     [streamparse.specs])
  (:gen-class))

(defn tweetwordcount [options]
   [
    ;; spout configuration
    {"running-movie-spout" (python-spout-spec
          options
          "spouts.runningmovies.Tweets"
          ["tweet","latitude","longitude","country","region"]
          :p 1
          )
     "upcoming-movie-spout" (python-spout-spec
          options
          "spouts.upcomingmovies.Tweets"
          ["tweet","latitude","longitude","country","region"]
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
     "statistic-bolt" (python-bolt-spec
          options
          {"parse-tweet-bolt" ["id"]}
          "bolts.tweetstatistic.TweetStatistic"
          ["title" "count"]
          :p 2
          )
    }
  ]
)
