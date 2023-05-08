package com.hemanth.movies;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/movies")
@CrossOrigin
public class MovieController {
	

	@Autowired MovieService service;
	
	@GetMapping("")
	@ResponseBody
	public List<Movie> getAllMovies( ){
		
		List<Movie> movies= service.listAllMovies();
		return movies;
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	public Movie getAllMovieById(@PathVariable Integer id ){
		
		Movie movie= service.getbyId(id);
		return movie;
	}
	
	
	
	@PostMapping("/save")
	@ResponseBody
	public HttpStatus createNewMovie(@Validated @RequestBody Movie movie, RedirectAttributes ra) {
		service.save(movie);
		ra.addAttribute("message", "The movie has been saved successfully!");
		return HttpStatus.CREATED;
		
	}
	
	@PutMapping("/edit/{id}")
	@ResponseBody
	public HttpStatus editMovie(@PathVariable Integer id, RedirectAttributes ra,@Validated @RequestBody Movie updatedMovie) {
		try {
			Movie movie = service.get(id);
			movie.setName(updatedMovie.getName());
			movie.setDesc(updatedMovie.getDesc());
			service.save(movie);
			ra.addAttribute("message", "The movie has been saved successfully!");
			
		} catch (MovieNotFoundException e) {
			ra.addAttribute("message", e.getMessage());
		}
		
		return HttpStatus.CREATED;
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteMovie(@PathVariable Integer id, RedirectAttributes ra) {
		try {
			 service.delete(id);
			 ra.addAttribute("message", "The movie has been deleted successfully!");
		} catch (MovieNotFoundException e) {
			
			ra.addAttribute("message", e.getMessage());
		}
		
	}

}
