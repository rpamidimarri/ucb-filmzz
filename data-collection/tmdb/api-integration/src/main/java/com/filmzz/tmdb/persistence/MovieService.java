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
		MovieService movieService = new MovieService();
		Movie movie = new Movie();
		movie.setTitleAndYear("3 Idiots 2009");
		movie.setImdbId("abcd");
		movie.setImdbTitle("3 idiots");
		movieService.createMovie(movie);
		
		List<Movie> movies = movieService.getMovies();
		System.out.println("First Movie:" + movies.get(0).getImdbTitle());
	}
	
    private SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration().configure();
		ServiceRegistryBuilder builder = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration.buildSessionFactory(builder.buildServiceRegistry());
		return sessionFactory;
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
		System.out.println("Found " + movies.size() + " Movies");
		return movies;
	}

}
