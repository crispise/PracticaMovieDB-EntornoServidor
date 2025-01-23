package com.esliceu.movies.services;

import com.esliceu.movies.models.Movie;
import com.esliceu.movies.models.MovieCompany;
import com.esliceu.movies.models.MovieCompanyId;
import com.esliceu.movies.models.ProductionCompany;
import com.esliceu.movies.repos.MovieCompanyRepo;
import com.esliceu.movies.repos.MovieRepo;
import com.esliceu.movies.repos.PCompaniesRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieCompanyServices {
    @Autowired
    PCompaniesRepo pCompaniesRepo;
    @Autowired
    MovieCompanyRepo movieCompanyRepo;


    public List<ProductionCompany> getMovieCompanies(Movie movie) {
        List<ProductionCompany> productionCompanies = movie.getMovieCompanies().stream()
                .map(movieCompany -> movieCompany.getProductionCompany())
                .toList();
        return productionCompanies;
    }

    public String addMovieCompany(String companyName, Movie movie) {
        List<ProductionCompany> productionCompanies = pCompaniesRepo.findProductionCompanyByCompanyName(companyName);
        if (productionCompanies.size() == 1) {
            ProductionCompany productionCompany = productionCompanies.get(0);
            boolean exists = false;
            for (MovieCompany mc : movie.getMovieCompanies()) {
                if (mc.getProductionCompany().getCompanyId().equals(productionCompany.getCompanyId())) {
                    exists = true;
                    break;
                }
            }
            if (exists) {
                return "Esta compañia ya está añadida";
            }
            return createAndSaveMovieCompany(movie, productionCompany);
        }
        return "Hay más de una compañia con ese nombre";
    }

    private String createAndSaveMovieCompany(Movie movie, ProductionCompany productionCompany) {
        MovieCompany movieCompany = new MovieCompany();
        movieCompany.setMovie(movie);
        movieCompany.setProductionCompany(productionCompany);
        MovieCompanyId mci = new MovieCompanyId();
        mci.setCompanyId(productionCompany.getCompanyId());
        mci.setMovieId(movie.getMovieId());
        movieCompany.setId(mci);
        movieCompanyRepo.save(movieCompany);
        return null;
    }

    public String deleteMovieCompany(Integer companyId, Integer movieId) {
        System.out.println("entra en movie company");
        MovieCompanyId movieCompanyId = new MovieCompanyId();
        movieCompanyId.setMovieId(movieId);
        movieCompanyId.setCompanyId(companyId);
        Optional<MovieCompany> movieCompany = movieCompanyRepo.findById(movieCompanyId);
        if (movieCompany.isPresent()) {
            System.out.println("entra en moviCompany present");
            System.out.println(movieCompany.get());
            movieCompanyRepo.deleteById(movieCompanyId);
            return null;
        } else {
            return "Esa compañia no está relacionada con la película";
        }
    }
}
