CREATE TABLE Tweets (
imdbId varchar(64),
tweetTime varchar(64),
keyword varchar(200),
fullTweet varchar(200),
CONSTRAINT imdbId_tweetTime PRIMARY KEY(imdbId,tweetTime)
)

