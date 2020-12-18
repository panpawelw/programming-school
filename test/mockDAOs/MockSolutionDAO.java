package mockDAOs;

import com.panpawelw.DAO.SolutionDAO;
import com.panpawelw.model.Solution;

import java.util.ArrayList;
import java.util.List;

import static misc.TestUtils.createMultipleSolutions;

public class MockSolutionDAO implements SolutionDAO {

    @Override
    public int saveSolutionToDB(Solution solution) {
        return 1;
    }

    @Override
    public Solution loadSolutionById(long id) {
        return createMultipleSolutions(1).get(0);
    }

    @Override
    public int deleteSolution(Solution solution) {
        return 1;
    }

    @Override
    public List<Solution> loadAllSolutions() {
        return createMultipleSolutions(10);
    }

    @Override
    public List<Solution> loadAllSolutionsByUserId(long id) {
        return createMultipleSolutions(10, id);
    }
}
