package mockDAOs;

import com.panpawelw.DAO.LastSolutionDAO;
import com.panpawelw.model.LastSolution;

import java.util.ArrayList;
import java.util.List;

public class MockLastSolutionDAO implements LastSolutionDAO {

    @Override
    public List<LastSolution> loadMostRecentSolutions(long number) {
        return new ArrayList<>();
    }

    @Override
    public List<LastSolution> loadMostRecentSolutionsByUserId(long id) {
        return new ArrayList<>();
    }
}