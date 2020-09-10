//package mockDAOs;
//
//import pl.pjm77.DAO.LastSolutionDAO;
//import pl.pjm77.model.LastSolution;
//
//import java.util.List;
//
//import static misc.TestUtils.*;
//
//public class MockLastSolutionDAO implements LastSolutionDAO {
//
//    @Override
//    public List<LastSolution> loadMostRecentSolutions(long number) {
//        List<LastSolution> recentSolutions = createMultipleLastSolutions();
//        return recentSolutions;
//    }
//
//    @Override
//    public List<LastSolution> loadMostRecentSolutionsByUserId(long id) {
//        return null;
//    }
//}