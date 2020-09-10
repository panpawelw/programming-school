package com.panpawelw.DAO;

import com.panpawelw.model.LastSolution;

import java.util.List;

public interface LastSolutionDAO {

    List<LastSolution> loadMostRecentSolutions(long number);
    List<LastSolution> loadMostRecentSolutionsByUserId(long id);
}