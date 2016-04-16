package com.filmzz.tmdb.persistence;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Movie")
public class Movie implements Serializable {	
	private String imdbId;
	private String tmdbId;
	//Currently running/upcoming/old
	private String status;
	
	//All imdb fields - only 4 of them - from the file
	private String imdbTitle;
	private long imdbVotes;
	private float imdbAverageRating;
	private String imdbDistribution;
	
	//All tmdb fields
	private String tmdbOverview;
	private float tmdbPopularity;
	private String tmdbHomePage;
	private long tmdbRevenue;
	private int tmdbRuntime;
	private String tmdbReleaseDate;
	private String tmdbTagline;
	private String tmdbTitle;
	private float tmdbVoteAverage;
	private long tmdbVoteCount;
	private String tmdbProductionCompanies;
	private String tmdbProductionCountries;
	private String tmdbSpokenLanguages;
	
	//All omdb fields
	private String omdbTitle;
	private String omdbYear;
	private String omdbRated;
	private String omdbReleased;
	private String omdbRuntime;
	private String omdbGenres;
	private String omdbDirectors;
	private String omdbWriters;
	private String omdbActors;
	private String omdbPlot;
	private String omdbLanguage;
	private String omdbCountry;
	private String omdbAwards;
	private String omdbPoster;
	private String omdbMetaScore;
	private String omdbType;
	private String omdbImdbRating;
	private String omdbImdbVotes;
	private String omdbImdbID;
	private String omdbDvd;
	private String omdbBoxOffice;
	private String omdbWebsite;
	private String omdbProduction;
	//All Omdb - Rotten tomatoes fields
	private String omdbTomatoMeter;
	private String omdbTomatoImage;
	private String omdbTomatoRating;
	private String omdbTomatoReviews;
	private String omdbTomatoFresh;
	private String omdbTomatoRotten;
	private String omdbTomatoConsensus;
	private String omdbTomatoUserMeter;
	private String omdbTomatoUserRating;
	private String omdbTomatoUserReviews;
	private String omdbTomatoURL;
	
	
	public String getImdbId() {
		return imdbId;
	}
	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}
	public String getTmdbId() {
		return tmdbId;
	}
	public void setTmdbId(String tmdbId) {
		this.tmdbId = tmdbId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getImdbTitle() {
		return imdbTitle;
	}
	public void setImdbTitle(String imdbTitle) {
		this.imdbTitle = imdbTitle;
	}
	public long getImdbVotes() {
		return imdbVotes;
	}
	public void setImdbVotes(long imdbVotes) {
		this.imdbVotes = imdbVotes;
	}
	public float getImdbAverageRating() {
		return imdbAverageRating;
	}
	public void setImdbAverageRating(float imdbAverageRating) {
		this.imdbAverageRating = imdbAverageRating;
	}
	public String getImdbDistribution() {
		return imdbDistribution;
	}
	public void setImdbDistribution(String imdbDistribution) {
		this.imdbDistribution = imdbDistribution;
	}
	public String getTmdbOverview() {
		return tmdbOverview;
	}
	public void setTmdbOverview(String tmdbOverview) {
		this.tmdbOverview = tmdbOverview;
	}
	public float getTmdbPopularity() {
		return tmdbPopularity;
	}
	public void setTmdbPopularity(float tmdbPopularity) {
		this.tmdbPopularity = tmdbPopularity;
	}
	public String getTmdbHomePage() {
		return tmdbHomePage;
	}
	public void setTmdbHomePage(String tmdbHomePage) {
		this.tmdbHomePage = tmdbHomePage;
	}
	public long getTmdbRevenue() {
		return tmdbRevenue;
	}
	public void setTmdbRevenue(long tmdbRevenue) {
		this.tmdbRevenue = tmdbRevenue;
	}
	public int getTmdbRuntime() {
		return tmdbRuntime;
	}
	public void setTmdbRuntime(int tmdbRuntime) {
		this.tmdbRuntime = tmdbRuntime;
	}
	public String getTmdbReleaseDate() {
		return tmdbReleaseDate;
	}
	public void setTmdbReleaseDate(String tmdbReleaseDate) {
		this.tmdbReleaseDate = tmdbReleaseDate;
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
	public float getTmdbVoteAverage() {
		return tmdbVoteAverage;
	}
	public void setTmdbVoteAverage(float tmdbVoteAverage) {
		this.tmdbVoteAverage = tmdbVoteAverage;
	}
	public long getTmdbVoteCount() {
		return tmdbVoteCount;
	}
	public void setTmdbVoteCount(long tmdbVoteCount) {
		this.tmdbVoteCount = tmdbVoteCount;
	}
	public String getTmdbProductionCompanies() {
		return tmdbProductionCompanies;
	}
	public void setTmdbProductionCompanies(String tmdbProductionCompanies) {
		this.tmdbProductionCompanies = tmdbProductionCompanies;
	}
	public String getTmdbProductionCountries() {
		return tmdbProductionCountries;
	}
	public void setTmdbProductionCountries(String tmdbProductionCountries) {
		this.tmdbProductionCountries = tmdbProductionCountries;
	}
	public String getTmdbSpokenLanguages() {
		return tmdbSpokenLanguages;
	}
	public void setTmdbSpokenLanguages(String tmdbSpokenLanguages) {
		this.tmdbSpokenLanguages = tmdbSpokenLanguages;
	}
	public String getOmdbTitle() {
		return omdbTitle;
	}
	public void setOmdbTitle(String omdbTitle) {
		this.omdbTitle = omdbTitle;
	}
	public String getOmdbYear() {
		return omdbYear;
	}
	public void setOmdbYear(String omdbYear) {
		this.omdbYear = omdbYear;
	}
	public String getOmdbRated() {
		return omdbRated;
	}
	public void setOmdbRated(String omdbRated) {
		this.omdbRated = omdbRated;
	}
	public String getOmdbReleased() {
		return omdbReleased;
	}
	public void setOmdbReleased(String omdbReleased) {
		this.omdbReleased = omdbReleased;
	}
	public String getOmdbRuntime() {
		return omdbRuntime;
	}
	public void setOmdbRuntime(String omdbRuntime) {
		this.omdbRuntime = omdbRuntime;
	}
	public String getOmdbGenres() {
		return omdbGenres;
	}
	public void setOmdbGenres(String omdbGenres) {
		this.omdbGenres = omdbGenres;
	}
	public String getOmdbDirectors() {
		return omdbDirectors;
	}
	public void setOmdbDirectors(String omdbDirectors) {
		this.omdbDirectors = omdbDirectors;
	}
	public String getOmdbWriters() {
		return omdbWriters;
	}
	public void setOmdbWriters(String omdbWriters) {
		this.omdbWriters = omdbWriters;
	}
	public String getOmdbActors() {
		return omdbActors;
	}
	public void setOmdbActors(String omdbActors) {
		this.omdbActors = omdbActors;
	}
	public String getOmdbPlot() {
		return omdbPlot;
	}
	public void setOmdbPlot(String omdbPlot) {
		this.omdbPlot = omdbPlot;
	}
	public String getOmdbLanguage() {
		return omdbLanguage;
	}
	public void setOmdbLanguage(String omdbLanguage) {
		this.omdbLanguage = omdbLanguage;
	}
	public String getOmdbCountry() {
		return omdbCountry;
	}
	public void setOmdbCountry(String omdbCountry) {
		this.omdbCountry = omdbCountry;
	}
	public String getOmdbAwards() {
		return omdbAwards;
	}
	public void setOmdbAwards(String omdbAwards) {
		this.omdbAwards = omdbAwards;
	}
	public String getOmdbPoster() {
		return omdbPoster;
	}
	public void setOmdbPoster(String omdbPoster) {
		this.omdbPoster = omdbPoster;
	}
	public String getOmdbMetaScore() {
		return omdbMetaScore;
	}
	public void setOmdbMetaScore(String omdbMetaScore) {
		this.omdbMetaScore = omdbMetaScore;
	}
	public String getOmdbType() {
		return omdbType;
	}
	public void setOmdbType(String omdbType) {
		this.omdbType = omdbType;
	}
	public String getOmdbImdbRating() {
		return omdbImdbRating;
	}
	public void setOmdbImdbRating(String omdbImdbRating) {
		this.omdbImdbRating = omdbImdbRating;
	}
	public String getOmdbImdbVotes() {
		return omdbImdbVotes;
	}
	public void setOmdbImdbVotes(String omdbImdbVotes) {
		this.omdbImdbVotes = omdbImdbVotes;
	}
	public String getOmdbImdbID() {
		return omdbImdbID;
	}
	public void setOmdbImdbID(String omdbImdbID) {
		this.omdbImdbID = omdbImdbID;
	}
	public String getOmdbDvd() {
		return omdbDvd;
	}
	public void setOmdbDvd(String omdbDvd) {
		this.omdbDvd = omdbDvd;
	}
	public String getOmdbBoxOffice() {
		return omdbBoxOffice;
	}
	public void setOmdbBoxOffice(String omdbBoxOffice) {
		this.omdbBoxOffice = omdbBoxOffice;
	}
	public String getOmdbWebsite() {
		return omdbWebsite;
	}
	public void setOmdbWebsite(String omdbWebsite) {
		this.omdbWebsite = omdbWebsite;
	}
	public String getOmdbProduction() {
		return omdbProduction;
	}
	public void setOmdbProduction(String omdbProduction) {
		this.omdbProduction = omdbProduction;
	}
	public String getOmdbTomatoMeter() {
		return omdbTomatoMeter;
	}
	public void setOmdbTomatoMeter(String omdbTomatoMeter) {
		this.omdbTomatoMeter = omdbTomatoMeter;
	}
	public String getOmdbTomatoImage() {
		return omdbTomatoImage;
	}
	public void setOmdbTomatoImage(String omdbTomatoImage) {
		this.omdbTomatoImage = omdbTomatoImage;
	}
	public String getOmdbTomatoRating() {
		return omdbTomatoRating;
	}
	public void setOmdbTomatoRating(String omdbTomatoRating) {
		this.omdbTomatoRating = omdbTomatoRating;
	}
	public String getOmdbTomatoReviews() {
		return omdbTomatoReviews;
	}
	public void setOmdbTomatoReviews(String omdbTomatoReviews) {
		this.omdbTomatoReviews = omdbTomatoReviews;
	}
	public String getOmdbTomatoFresh() {
		return omdbTomatoFresh;
	}
	public void setOmdbTomatoFresh(String omdbTomatoFresh) {
		this.omdbTomatoFresh = omdbTomatoFresh;
	}
	public String getOmdbTomatoRotten() {
		return omdbTomatoRotten;
	}
	public void setOmdbTomatoRotten(String omdbTomatoRotten) {
		this.omdbTomatoRotten = omdbTomatoRotten;
	}
	public String getOmdbTomatoConsensus() {
		return omdbTomatoConsensus;
	}
	public void setOmdbTomatoConsensus(String omdbTomatoConsensus) {
		this.omdbTomatoConsensus = omdbTomatoConsensus;
	}
	public String getOmdbTomatoUserMeter() {
		return omdbTomatoUserMeter;
	}
	public void setOmdbTomatoUserMeter(String omdbTomatoUserMeter) {
		this.omdbTomatoUserMeter = omdbTomatoUserMeter;
	}
	public String getOmdbTomatoUserRating() {
		return omdbTomatoUserRating;
	}
	public void setOmdbTomatoUserRating(String omdbTomatoUserRating) {
		this.omdbTomatoUserRating = omdbTomatoUserRating;
	}
	public String getOmdbTomatoUserReviews() {
		return omdbTomatoUserReviews;
	}
	public void setOmdbTomatoUserReviews(String omdbTomatoUserReviews) {
		this.omdbTomatoUserReviews = omdbTomatoUserReviews;
	}
	public String getOmdbTomatoURL() {
		return omdbTomatoURL;
	}
	public void setOmdbTomatoURL(String omdbTomatoURL) {
		this.omdbTomatoURL = omdbTomatoURL;
	}
	
}
