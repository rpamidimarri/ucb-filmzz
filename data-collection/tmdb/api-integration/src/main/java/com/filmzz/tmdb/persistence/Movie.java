package com.filmzz.tmdb.persistence;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Movie")
public class Movie implements Serializable {
	//All imdb fields from the file
	private String titleAndYear;
	private String imdbTitle;
	private long imdbVotes;
	private float imdbAverageRating;
	private String imdbDistribution;
	
	private String imdbId;
	
	//All omdb fields
	private String omdbRated;
	private String omdbReleased;
	private String omdbRuntime;
	private String omdbGenre;
	private String omdbDirector;
	private String omdbWriter;
	private String omdbActors;
	private String omdbPlot;
	private String omdbLanguage;
	private String omdbCountry;
	private String omdbAwards;
	private String omdbPoster;
	private String omdbMetaScore;
	private String omdbType;
	private String omdbDvd;
	private String omdbBoxOffice;
	private String omdbWebsite;
	private String omdbProduction;
	//All Omdb - Rotten tomatoes fields
	private String tomatoMeter;
	private String tomatoImage;
	private String tomatoRating;
	private String tomatoReviews;
	private String tomatoFresh;
	private String tomatoRotten;
	private String tomatoConsensus;
	private String tomatoUserMeter;
	private String tomatoUserRating;
	private String tomatoUserReviews;
	private String tomatoUrl;	
}
