package pl.pjm77.DAO;

import pl.pjm77.model.Solution;

import java.util.List;

public interface SolutionDAO {

    int saveSolutionToDB(Solution solution);
    Solution loadSolutionById(long id);
    int deleteSolution(Solution solution);
    List<Solution> loadAllSolutions();
    List<Solution> loadAllSolutionsByUserId(long id);
}