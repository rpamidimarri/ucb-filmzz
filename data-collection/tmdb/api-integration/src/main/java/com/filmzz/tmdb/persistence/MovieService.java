package com.filmzz.tmdb.persistence;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

public class MovieService {	
	private SessionFactory sessionFactory;
	public MovieService() {
		sessionFactory = getSessionFactory();
	}
	
	public static void main(String[] args) {
/*		MovieService movieService = new MovieService();
		Movie movie = new Movie();
		movie.setTitleAndYear("3 Idiots 2009");
		movie.setImdbId("abcd");
		movie.setImdbTitle("3 idiots");
		movieService.createMovie(movie);
		
		List<Movie> movies = movieService.getMovies();
		System.out.println("First Movie:" + movies.get(0).getImdbTitle());*/
		MovieService movieService = new MovieService();
		ActiveMovie activeMovie = new ActiveMovie();
		activeMovie.setExecutionTime("ajdlwe");
		TmdbIdCount pk = new TmdbIdCount();
		pk.setExecutionCount(1);
		pk.setTmdbId("1234");
		activeMovie.setTmdbIdCount(pk);
		activeMovie.setImdbId("imdb-id");
		movieService.createActiveMovie(activeMovie);
		System.out.println("Max Exec Count:" +movieService.getLatestExecutionCount());
		
		activeMovie = new ActiveMovie();
		activeMovie.setExecutionTime("ajdlwe");
		pk = new TmdbIdCount();
		pk.setExecutionCount(2);
		pk.setTmdbId("1234");
		activeMovie.setTmdbIdCount(pk);
		activeMovie.setImdbId("imdb-id");
		movieService.createActiveMovie(activeMovie);
		System.out.println("Max Exec Count:" +movieService.getLatestExecutionCount());
		//List<ActiveMovie> activeMovies = movieService.getActiveMovies();
		//System.out.println("First active movie's count: " + activeMovies.get(0).getTmdbIdCount().getExecutionCount());
	}
	
    private SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration().configure();
		ServiceRegistryBuilder builder = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration.buildSessionFactory(builder.buildServiceRegistry());
		return sessionFactory;
	}
    
    public void createActiveMovie(ActiveMovie activeMovie) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(activeMovie);
		session.getTransaction().commit();
		session.close();
    }
	
	public void createMovie(Movie movie) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(movie);
		session.getTransaction().commit();
		session.close();
	}
	
	public List<Movie> getMovies() {
		Session session = sessionFactory.openSession();
		@SuppressWarnings("unchecked")
		List<Movie> movies = session.createQuery("FROM Movie").list();
		session.close();
		if (movies != null) {
			System.out.println("Found " + movies.size() + " movies");
		} else {
			System.out.println("Found no Active Movies!");
		}
		return movies;
	}
	
	public List<ActiveMovie> getActiveMovies() {
		Session session = sessionFactory.openSession();
		@SuppressWarnings("unchecked")
		List<ActiveMovie> activeMovies = session.createQuery("FROM ActiveMovie").list();
		session.close();
		if (activeMovies != null) {
			System.out.println("Found " + activeMovies.size() + " Active Movies");
		} else {
			System.out.println("Found no Active Movies!");
		}
		return activeMovies;
	}
	
	public int getLatestExecutionCount() {
		int max = 0;
		Session session = sessionFactory.openSession();
		List countResult = session.createQuery("Select count(*) from ActiveMovie").list();
		long count = (Long) countResult.get(0);
		
		if (count > 0) {
			@SuppressWarnings("unchecked")
			List result = session.createQuery("Select max(CAST(executioncount as int)) from ActiveMovie").list();
			if (result != null && !result.isEmpty()) {
				max = (Integer) result.get(0);
			}
		} 

		session.close();
		return max;
	}

}
