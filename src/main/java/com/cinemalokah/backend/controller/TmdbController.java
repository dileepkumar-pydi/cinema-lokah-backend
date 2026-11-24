package com.cinemalokah.backend.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cinemalokah.backend.service.TmdbMovieService;
import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/tmdb")
@RequiredArgsConstructor
public class TmdbController {

	private final TmdbMovieService tmdbMovieService;

	@GetMapping("/movies/search")
	@Operation(summary = "Get movie details by search")
	public Mono<JsonNode> searchMovies(@RequestParam String query) {
		return tmdbMovieService.searchMovies(query);
	}

	@GetMapping("/movies/{movieId}")
	@Operation(summary = "Get complete movie details")
	public Mono<JsonNode> getMovieDetails(@PathVariable Long movieId) {

		return tmdbMovieService.getMovieDetails(movieId);
	}

	@GetMapping("/movies/{movieId}/providers")
	@Operation(summary = "Get movie OTT providers")
	public Mono<JsonNode> getMovieProviders(@PathVariable Long movieId) {

		return tmdbMovieService.getMovieWatchProviders(movieId);
	}

	@GetMapping("/movies/{movieId}/images")
	@Operation(summary = "Get movie posters and backdrops")
	public Mono<JsonNode> getMovieImages(@PathVariable Long movieId) {

		return tmdbMovieService.getMovieImages(movieId);
	}

	@GetMapping("/movies/trending")
	public Mono<JsonNode> getTrendingMovies(@RequestParam(defaultValue = "1") int page) {
		return tmdbMovieService.getTrendingMovies(page);
	}

	@GetMapping("/movies/now-playing")
	public Mono<JsonNode> getNowPlayingMovies(@RequestParam(defaultValue = "1") int page) {
		return tmdbMovieService.getNowPlayingMovies(page);
	}

	@GetMapping("/movies/upcoming")
	public Mono<JsonNode> getUpcomingMovies(@RequestParam(defaultValue = "1") int page) {
		return tmdbMovieService.getUpcomingMovies(page);
	}

	@GetMapping("/movies/popular")
	public Mono<JsonNode> getPopularMovies(@RequestParam(defaultValue = "1") int page) {
		return tmdbMovieService.getPopularMovies(page);
	}

	@GetMapping("/movies/top-rated")
	public Mono<JsonNode> getTopRatedMovies(@RequestParam(defaultValue = "1") int page) {
		return tmdbMovieService.getTopRatedMovies(page);
	}

	@GetMapping("/movies/recent-releases")
	public Mono<JsonNode> getRecentReleases(@RequestParam(defaultValue = "1") int page) {
		return tmdbMovieService.getRecentReleases(page);
	}

	@GetMapping("/home")
	public Mono<Map<String, JsonNode>> getHomePage() {
		return tmdbMovieService.getHomePage();
	}

}
