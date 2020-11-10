package mockDAOs;

import com.panpawelw.DAO.SolutionDAO;
import com.panpawelw.model.Solution;

import java.util.List;

public class MockSolutionDAO implements SolutionDAO {

    @Override
    public int saveSolutionToDB(Solution solution) {
        return 0;
    }

    @Override
    public Solution loadSolutionById(long id) {
        return null;
    }

    @Override
    public int deleteSolution(Solution solution) {
        return 0;
    }

    @Override
    public List<Solution> loadAllSolutions() {
        return null;
    }

    @Override
    public List<Solution> loadAllSolutionsByUserId(long id) {
        return null;
    }
}
