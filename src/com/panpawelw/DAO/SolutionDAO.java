package com.panpawelw.DAO;

import com.panpawelw.model.Solution;

import java.util.List;

public interface SolutionDAO {

    int saveSolutionToDB(Solution solution);
    Solution loadSolutionById(long id);
    int deleteSolution(Solution solution);
    List<Solution> loadAllSolutions();
    List<Solution> loadAllSolutionsByUserId(long id);
}