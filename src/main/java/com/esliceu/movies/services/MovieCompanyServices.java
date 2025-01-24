package com.esliceu.movies.services;

import com.esliceu.movies.models.Movie;
import com.esliceu.movies.models.MovieCompany;
import com.esliceu.movies.models.MovieCompanyId;
import com.esliceu.movies.models.ProductionCompany;
import com.esliceu.movies.repos.MovieCompanyRepo;
import com.esliceu.movies.repos.MovieRepo;
import com.esliceu.movies.repos.PCompaniesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieCompanyServices {
    @Autowired
    PCompaniesRepo pCompaniesRepo;
    @Autowired
    MovieCompanyRepo movieCompanyRepo;
    @Autowired
    MovieRepo movieRepo;


    public List<MovieCompany> getMovieCompanies(Movie movie) {
        List<MovieCompany> movieCompanies = movie.getMovieCompanies();
        return movieCompanies;
    }

    public String addMovieCompany(String companyName, Integer movieId) {
        if (companyName.isEmpty()) return "Falta introducir el nombre";
        Movie movie = movieRepo.findById(movieId).get();
        List<ProductionCompany> productionCompanies = pCompaniesRepo.findProductionCompanyByCompanyName(companyName);
        if (productionCompanies.size() > 1) {
            return "Hay más de una compañia con ese nombre";
        } else if (productionCompanies.isEmpty()) {
            return "No existe una compañia con ese nombre";
        }
        ProductionCompany productionCompany = productionCompanies.get(0);
        return createKeyAndMovieCompany(movie,productionCompany);
    }

    private String createKeyAndMovieCompany(Movie movie, ProductionCompany productionCompany) {
        MovieCompanyId mci = new MovieCompanyId();
        mci.setCompanyId(productionCompany.getCompanyId());
        mci.setMovieId(movie.getMovieId());

        Optional<MovieCompany> movieCompanySearch = movieCompanyRepo.findById(mci);
        if (movieCompanySearch.isPresent()){
            return "Este registro ya está en la lista";
        }
        saveMovieCompany(movie, productionCompany, mci);
        return null;
    }

    private void saveMovieCompany(Movie movie, ProductionCompany productionCompany, MovieCompanyId mci) {
        MovieCompany movieCompany = new MovieCompany();
        movieCompany.setMovie(movie);
        movieCompany.setProductionCompany(productionCompany);
        movieCompany.setId(mci);
        movieCompanyRepo.save(movieCompany);
    }

    public String deleteMovieCompany(Integer movieId, Integer companyId) {
        MovieCompanyId movieCompanyId = new MovieCompanyId();
        movieCompanyId.setCompanyId(companyId);
        movieCompanyId.setMovieId(movieId);

        Optional<MovieCompany> movieCompany = movieCompanyRepo.findById(movieCompanyId);
        if (movieCompany.isPresent()) {
            movieCompanyRepo.deleteById(movieCompanyId);
            return null;
        } else {
            return "Esa compañia no está relacionada con la película";
        }
    }
}
