package pl.pjm77.DAO;

import pl.pjm77.model.Solution;

public interface SolutionDAO {

    void saveSolutionToDB(Solution solution);
    Solution loadSolutionById(long id);
    void deleteSolution(Solution solution);
    Solution[] loadAllSolutions();
    Solution[] loadAllSolutionsByUserId(long id);

}
