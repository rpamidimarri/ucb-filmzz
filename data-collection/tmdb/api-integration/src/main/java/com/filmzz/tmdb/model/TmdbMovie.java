package com.filmzz.tmdb.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbMovie {
	@JsonProperty("id")
	private int tmdbId;
	
	@JsonProperty("imdb_id")
	private String imdbId;
	
	private String homepage;
	
	@JsonProperty("original_language")
	private String originalLanguage;
	
	@JsonProperty("original_title")
	private String originalTitle;
	
	@JsonProperty("overview")
	private String overview;
	
	private float popularity;
	
	@JsonProperty("release_date")
	private String releaseDate;
	
	private long revenue;
	private int runtime;
	private String status;
	private String tagline;
	private String title;
	
	@JsonProperty("vote_average")
	private float voteAverage;
	
	@JsonProperty("vote_count")
	private long voteCount;
	
	@JsonProperty("production_companies")
	private List<ProductionCompany> productionCompanies;
	
	@JsonProperty("production_countries")
	private List<ProductionCountry> productionCountries;
	
	@JsonProperty("spoken_languages")
	private List<SpokenLanguage> spokenLanguages;
	
	public TmdbMovie() {
	}

	public int getTmdbId() {
		return tmdbId;
	}

	public void setTmdbId(int tmdbId) {
		this.tmdbId = tmdbId;
	}

	public String getImdb_id() {
		return imdbId;
	}

	public void setImdb_id(String imdb_id) {
		this.imdbId = imdb_id;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getOriginalLanguage() {
		return originalLanguage;
	}

	public void setOriginalLanguage(String originalLanguage) {
		this.originalLanguage = originalLanguage;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public float getPopularity() {
		return popularity;
	}

	public void setPopularity(float popularity) {
		this.popularity = popularity;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public long getRevenue() {
		return revenue;
	}

	public void setRevenue(long revenue) {
		this.revenue = revenue;
	}

	public int getRuntime() {
		return runtime;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTagline() {
		return tagline;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public float getVoteAverage() {
		return voteAverage;
	}

	public void setVoteAverage(float voteAverage) {
		this.voteAverage = voteAverage;
	}

	public long getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(long voteCount) {
		this.voteCount = voteCount;
	}

	public List<ProductionCompany> getProductionCompanies() {
		return productionCompanies;
	}

	public void setProductionCompanies(List<ProductionCompany> productionCompanies) {
		this.productionCompanies = productionCompanies;
	}

	public List<ProductionCountry> getProductionCountries() {
		return productionCountries;
	}

	public void setProductionCountries(List<ProductionCountry> productionCountries) {
		this.productionCountries = productionCountries;
	}

	public List<SpokenLanguage> getSpokenLanguages() {
		return spokenLanguages;
	}

	public void setSpokenLanguages(List<SpokenLanguage> spokenLanguages) {
		this.spokenLanguages = spokenLanguages;
	}

	@Override
	public String toString() {
		return "Movie [tmdbId=" + tmdbId + ", imdbId=" + imdbId
				+ ", homepage=" + homepage + ", originalLanguage="
				+ originalLanguage + ", originalTitle=" + originalTitle
				+ ", overview=" + overview + ", popularity=" + popularity
				+ ", releaseDate=" + releaseDate + ", revenue=" + revenue
				+ ", runtime=" + runtime + ", status=" + status + ", tagline="
				+ tagline + ", title=" + title + ", voteAverage=" + voteAverage
				+ ", voteCount=" + voteCount + ", productionCompanies="
				+ productionCompanies + ", productionCountries="
				+ productionCountries + ", spokenLanguages=" + spokenLanguages
				+ "]";
	}
}
