package com.cinemalokah.backend.service;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TmdbMovieService {

	private final WebClient tmdbWebClient;

	public Mono<JsonNode> searchMovies(String query) {

		return tmdbWebClient.get().uri(uriBuilder -> uriBuilder.path("/search/movie").queryParam("query", query)
				.queryParam("language", "en-US").build()).retrieve().bodyToMono(JsonNode.class);
	}

	// Movie details + credits + videos + images
	public Mono<JsonNode> getMovieDetails(Long movieId) {

		return tmdbWebClient.get()
				.uri(uriBuilder -> uriBuilder.path("/movie/{movieId}").queryParam("language", "en-US")
						.queryParam("append_to_response", "credits,videos,images,release_dates,external_ids")
						.queryParam("include_image_language", "en,null").build(movieId))
				.retrieve().bodyToMono(JsonNode.class);
	}

	// OTT streaming providers
	public Mono<JsonNode> getMovieWatchProviders(Long movieId) {

		return tmdbWebClient.get().uri("/movie/{movieId}/watch/providers", movieId).retrieve()
				.bodyToMono(JsonNode.class);
	}

	// Movie images separately, if needed
	public Mono<JsonNode> getMovieImages(Long movieId) {

		return tmdbWebClient.get().uri("/movie/{movieId}/images", movieId).retrieve().bodyToMono(JsonNode.class);
	}

	// Reusable method for TMDB movie collections
	private Mono<JsonNode> getMovieList(String path, int page, String region) {

		return tmdbWebClient.get().uri(uriBuilder -> {
			var builder = uriBuilder.path(path).queryParam("language", "en-US").queryParam("page", page);

			if (region != null) {
				builder.queryParam("region", region);
			}

			return builder.build();
		}).retrieve().bodyToMono(JsonNode.class);
	}

	// Trending movies this week
	public Mono<JsonNode> getTrendingMovies(int page) {
		return getMovieList("/trending/movie/week", page, null);
	}

	// Currently playing in Indian theatres
	public Mono<JsonNode> getNowPlayingMovies(int page) {
		return getMovieList("/movie/now_playing", page, "IN");
	}

	// Upcoming Indian theatrical releases
	public Mono<JsonNode> getUpcomingMovies(int page) {
		return getMovieList("/movie/upcoming", page, "IN");
	}

	// Popular movies
	public Mono<JsonNode> getPopularMovies(int page) {
		return getMovieList("/movie/popular", page, "IN");
	}

	// Top-rated movies
	public Mono<JsonNode> getTopRatedMovies(int page) {
		return getMovieList("/movie/top_rated", page, "IN");
	}

	// Recent Indian theatrical and digital releases
	public Mono<JsonNode> getRecentReleases(int page) {

		LocalDate today = LocalDate.now();
		LocalDate fromDate = today.minusDays(90);

		return tmdbWebClient.get()
				.uri(uriBuilder -> uriBuilder.path("/discover/movie").queryParam("language", "en-US")
						.queryParam("region", "IN").queryParam("page", page).queryParam("release_date.gte", fromDate)
						.queryParam("release_date.lte", today).queryParam("with_release_type", "3|4")
						.queryParam("sort_by", "release_date.desc").build())
				.retrieve().bodyToMono(JsonNode.class);
	}

	// Single API for homepage
	public Mono<Map<String, JsonNode>> getHomePage() {

		return Mono
				.zip(getTrendingMovies(1), getNowPlayingMovies(1), getUpcomingMovies(1), getPopularMovies(1),
						getTopRatedMovies(1), getRecentReleases(1))
				.map(result -> Map.of("trending", result.getT1(), "nowPlaying", result.getT2(), "upcoming",
						result.getT3(), "popular", result.getT4(), "topRated", result.getT5(), "recentReleases",
						result.getT6()));
	}
}
