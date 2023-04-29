package com.hemanth.movies;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

	@Autowired MovieRepository repo;
	
	
	public List<Movie> listAllMovies(){
		Iterable<Movie> movies = repo.findAll();
		
		return (List<Movie>) movies;
		
	}
	
	public void save(Movie movie) {
		repo.save(movie);
	}
	
	public Movie get(Integer id) throws MovieNotFoundException {
		Optional<Movie> movie = repo.findById(id);
		if(movie.isPresent()) {
			return movie.get();
		}
		
		throw new MovieNotFoundException("The movie couldn't found with id"+id);

	}
	
	
	public void delete(Integer id) throws MovieNotFoundException {
		  Optional<Movie> movie = repo.findById(id);
		  if(movie.isPresent()) {
			  repo.deleteById(id);
		  }else {
			  throw new MovieNotFoundException("The movie couldn't found with id"+id);
		  }
	}
	
}
