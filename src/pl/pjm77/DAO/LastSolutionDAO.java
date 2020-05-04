package pl.pjm77.DAO;

import pl.pjm77.model.LastSolution;

import java.util.List;

public interface LastSolutionDAO {

    List<LastSolution> loadMostRecentSolutions(long number);
    List<LastSolution> loadMostRecentSolutionsByUserId(long id);

}