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
	
	public ActiveMovie() {
		
	}

	public String getTmdbId() {
		return tmdbId;
	}

	public void setTmdbId(String tmdbId) {
		this.tmdbId = tmdbId;
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

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
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
