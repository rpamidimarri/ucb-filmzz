package com.filmzz.tmdb.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductionCompany {
	private String id;
	private String name;
	
	public ProductionCompany() {
		
	}
	
	public ProductionCompany(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ProductionCompany [id=" + id + ", name=" + name + "]";
	}
	
	
}
