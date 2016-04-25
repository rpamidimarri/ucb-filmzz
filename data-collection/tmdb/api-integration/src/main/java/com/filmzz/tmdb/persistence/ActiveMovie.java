package com.filmzz.tmdb.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.filmzz.tmdb.model.TmdbMovie;

@Entity
@Table(name = "ActiveMovie")
public class ActiveMovie implements Serializable {
	@EmbeddedId
	private TmdbIdCount tmdbIdCount;
	private String imdbId;
	private String status;
	//Indicates whether the movie is running or upcoming
	//Timestamp when the script was run
	private String executionTime;
	private String title;
	private String searchTitle;
	private String searchForTweets;
	
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
	
	public ActiveMovie() {		
	}
	
	public ActiveMovie(int execCount, String status, TmdbMovie tmdbMovie, boolean searchForTweets) {
		TmdbIdCount pk = new TmdbIdCount();
		pk.setExecutionCount(execCount);
		pk.setTmdbId(String.valueOf(tmdbMovie.getTmdbId()));
		this.tmdbIdCount = pk;
		
		this.imdbId = tmdbMovie.getImdbId();
		this.status = status;
		this.title = tmdbMovie.getTitle();
		this.executionTime = new Date().toString();
		this.tmdbHomePage = tmdbMovie.getHomepage();
		this.tmdbOverview = tmdbMovie.getOverview();
		this.tmdbPopularity = String.valueOf(tmdbMovie.getPopularity());
		this.tmdbReleaseDate = tmdbMovie.getReleaseDate();
		this.tmdbRevenue = String.valueOf(tmdbMovie.getRevenue());
		this.tmdbRuntime = String.valueOf(tmdbMovie.getRuntime());
		this.tmdbStatus = tmdbMovie.getStatus();
		this.tmdbTagline = tmdbMovie.getTagline();
		this.tmdbTitle = tmdbMovie.getTitle();
		this.tmdbVoteAverage = String.valueOf(tmdbMovie.getVoteAverage());
		this.tmdbVoteCount = String.valueOf(tmdbMovie.getVoteCount());
		
		if (tmdbTitle.contains(":")) {
			this.searchTitle = tmdbTitle.split(":")[0];
		} else {
			this.searchTitle = tmdbTitle;
		}
		
		if (searchForTweets) {
			this.searchForTweets = "true";
		} else {
			this.searchForTweets = "false";
		}
	}

	public TmdbIdCount getTmdbIdCount() {
		return tmdbIdCount;
	}

	public void setTmdbIdCount(TmdbIdCount tmdbIdCount) {
		this.tmdbIdCount = tmdbIdCount;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(String executionTime) {
		this.executionTime = executionTime;
	}

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTmdbBudget() {
		return tmdbBudget;
	}

	public void setTmdbBudget(String tmdbBudget) {
		this.tmdbBudget = tmdbBudget;
	}

	public String getTmdbHomePage() {
		return tmdbHomePage;
	}

	public void setTmdbHomePage(String tmdbHomePage) {
		this.tmdbHomePage = tmdbHomePage;
	}

	public String getTmdbOverview() {
		return tmdbOverview;
	}

	public void setTmdbOverview(String tmdbOverview) {
		this.tmdbOverview = tmdbOverview;
	}

	public String getTmdbPopularity() {
		return tmdbPopularity;
	}

	public void setTmdbPopularity(String tmdbPopularity) {
		this.tmdbPopularity = tmdbPopularity;
	}

	public String getTmdbReleaseDate() {
		return tmdbReleaseDate;
	}

	public void setTmdbReleaseDate(String tmdbReleaseDate) {
		this.tmdbReleaseDate = tmdbReleaseDate;
	}

	public String getTmdbRevenue() {
		return tmdbRevenue;
	}

	public void setTmdbRevenue(String tmdbRevenue) {
		this.tmdbRevenue = tmdbRevenue;
	}

	public String getTmdbRuntime() {
		return tmdbRuntime;
	}

	public void setTmdbRuntime(String tmdbRuntime) {
		this.tmdbRuntime = tmdbRuntime;
	}

	public String getTmdbStatus() {
		return tmdbStatus;
	}

	public void setTmdbStatus(String tmdbStatus) {
		this.tmdbStatus = tmdbStatus;
	}

	public String getTmdbTagline() {
		return tmdbTagline;
	}

	public void setTmdbTagline(String tmdbTagline) {
		this.tmdbTagline = tmdbTagline;
	}

	public String getTmdbTitle() {
		return tmdbTitle;
	}

	public void setTmdbTitle(String tmdbTitle) {
		this.tmdbTitle = tmdbTitle;
	}

	public String getTmdbVoteAverage() {
		return tmdbVoteAverage;
	}

	public void setTmdbVoteAverage(String tmdbVoteAverage) {
		this.tmdbVoteAverage = tmdbVoteAverage;
	}

	public String getTmdbVoteCount() {
		return tmdbVoteCount;
	}

	public void setTmdbVoteCount(String tmdbVoteCount) {
		this.tmdbVoteCount = tmdbVoteCount;
	}

	public String getTmdbVideo() {
		return tmdbVideo;
	}

	public void setTmdbVideo(String tmdbVideo) {
		this.tmdbVideo = tmdbVideo;
	}
	
	
}
