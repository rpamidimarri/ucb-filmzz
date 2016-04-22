1. The findMovieTitles.py is an example on how to connect to the filmzz DB to get the titles to search for in tweets
2. In the filmzz directory, you can run ```sparse run``` to execute the storm topology.
3. You will have to provide the Twitter API creds in the tweets.py of the spout for the authentication to work fine. Do not checkin your creds.
4. I could get part of this working on my AWS machine - did not yet integrate with Vader and do not have the code to update the tweet tables. 
5. I added comments to the spouts and bolts on where and what needs to be changed.
