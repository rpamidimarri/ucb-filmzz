CREATE TABLE Tweets (
tmdbId varchar(100),
tweetTime varchar(100),
keyword varchar(1000),
fullTweet varchar(1000),
CONSTRAINT tmdbId_tweetTime PRIMARY KEY(tmdbId,tweetTime)
)

