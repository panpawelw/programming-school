package mockDAOs;

import com.panpawelw.DAO.SolutionDAO;
import com.panpawelw.model.Solution;

import java.util.ArrayList;
import java.util.List;

public class MockSolutionDAO implements SolutionDAO {

    @Override
    public int saveSolutionToDB(Solution solution) {
        return 1;
    }

    @Override
    public Solution loadSolutionById(long id) {
        return new Solution();
    }

    @Override
    public int deleteSolution(Solution solution) {
        return 1;
    }

    @Override
    public List<Solution> loadAllSolutions() {
        return new ArrayList<>();
    }

    @Override
    public List<Solution> loadAllSolutionsByUserId(long id) {
        return new ArrayList<>();
    }
}
