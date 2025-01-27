package com.esliceu.movies;


import com.esliceu.movies.filters.LoginForNavInterceptor;
import com.esliceu.movies.filters.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MoviesApplication implements WebMvcConfigurer {

	@Autowired
	LoginInterceptor loginInterceptor;

	@Autowired
	LoginForNavInterceptor loginForNavInterceptor;


	public static void main(String[] args) {
		SpringApplication.run(MoviesApplication.class, args);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor)
				.addPathPatterns("/permissions", "/acceptPermission/**", "/rejectPermission/**", "/deletePermission/**", "/requestPermission/**", "/countries", "/allCountries",
						"/searchCountries", "/createCountry", "/deleteCountry", "/updateCountry/**", "/departments", "/allDepartments", "/searchDepartments", "/createDepartment", "/deleteDepartment", "/updateDepartment/**",
						"/manage/**", "/genders", "/allGenders", "/searchGenders", "/createGender", "/deleteGender", "/updateGender/**", "/genres", "/allGenres", "/searchGenres", "/createGenre",
						"/deleteGenre", "/updateGenre/**", "/keywords", "/allKeywords", "/searchKeywords", "/createKeyword", "/deleteKeyword", "/updateKeyword/**", "/languages",
						"/allLanguages", "/searchLanguages", "/createLanguage", "/deleteLanguage", "/updateLanguage/**", "/languageRoles", "/allLanguageRoles", "/searchLanguageRoles", "/createLanguageRole", "/deleteLanguageRole",
						"/updateLanguageRole/**", "/movieCast/**", "/addMovieCast", "/deleteMovieCast", "/movieCompany/**", "/addMovieCompany", "/deleteMovieCompany", "/movies", "/allMovies", "/searchMovies/**", "/createMovie",
						"/deleteMovie", "/updateMovie/**", "/entityMovieForUpdate", "/movieCrew/**", "/addMovieCrew", "/deleteMovieCrew", "/movieGenre/**", "/addMovieGenre",
						"/deleteMovieGenre", "/movieKeyword/**", "/addMovieKeyword", "/deleteMovieKeyword", "/movieLanguage/**", "/addMovieLanguage", "/deleteMovieLanguage", "/productionCompany", "/allCompanies", "/searchCompanies",
						"/createCompany", "/deleteCompany", "/updateCompany/**", "/persons", "/allPersons", "/searchPersons", "/createPerson", "/deletePerson", "/updatePerson/**", "/productionCountry/**", "/addProductionCountry",
						"/deleteProductionCountry");
		registry.addInterceptor(loginForNavInterceptor)
				.addPathPatterns("/moviesQuerys", "/allMovieQuerys/**", "/searchMovieByType/**", "/seeMovieInfo/**");
	}

}

