package com.filmzz.tmdb.persistence;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class MovieService {	
	
	public static void main(String[] args) {
		MovieService movieService = new MovieService();
		Movie movie = new Movie();
		movie.setImdbId("abcd");
		movie.setImdbTitle("3 idiots");
		movieService.createMovie(movie);
		
		List<Movie> movies = movieService.getMovies();
		System.out.println("First Movie:" + movies.get(0).getImdbTitle());
	}
	
	public SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration
				.buildSessionFactory(builder.build());
		return sessionFactory;
	}

	public Movie createMovie(Movie movie) {
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		session.save(movie);
		session.getTransaction().commit();
		session.close();
		System.out.println("Successfully created " + movie.getImdbId());
		return movie;
	}
	
	public List<Movie> getMovies() {
		Session session = getSessionFactory().openSession();
		@SuppressWarnings("unchecked")
		List<Movie> movies = session.createQuery("FROM Employee").list();
		session.close();
		System.out.println("Found " + movies.size() + " Movies");
		return movies;
	}

}
