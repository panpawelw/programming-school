package pl.pjm77.DAO;

import pl.pjm77.model.LastSolution;

public interface LastSolutionDAO {

    LastSolution[] loadMostRecentSolutions(long number);
    LastSolution[] loadMostRecentSolutionsByUserId(long id);

}