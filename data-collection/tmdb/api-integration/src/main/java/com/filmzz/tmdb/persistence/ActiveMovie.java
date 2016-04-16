package com.filmzz.tmdb.persistence;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Movie")
public class ActiveMovie implements Serializable {
	private String tmdbId;
	private String imdbId;
	private String status;
	private String day;
	
	private String tmdbBudget;
	private String tmdbHomePage;
	private String tmdbOverview;
	private String tmdbPopularity;
	private String tmdbReleaseDate;
	private String tmdbRevenue;
	private String tmdbRuntime;
	private String tmdbStatus;
	private String tmdbTagline;
	private String tmdbTitle;
	private String tmdbVoteAverage;
	private String tmdbVoteCount;
	private String tmdbVideo;
}
