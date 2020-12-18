package mockDAOs;

import com.panpawelw.DAO.LastSolutionDAO;
import com.panpawelw.model.LastSolution;

import java.util.List;

import static misc.TestUtils.createMultipleLastSolutions;

public class MockLastSolutionDAO implements LastSolutionDAO {

    @Override
    public List<LastSolution> loadMostRecentSolutions(long number) {
        return createMultipleLastSolutions(number);
    }

    @Override
    public List<LastSolution> loadMostRecentSolutionsByUserId(long id) {
        return createMultipleLastSolutions(10);
    }
}
