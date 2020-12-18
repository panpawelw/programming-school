package mockDAOs;

import com.panpawelw.DAO.ExerciseDAO;
import com.panpawelw.model.Exercise;

import java.util.List;

import static misc.TestUtils.createMultipleExercises;

public class MockExerciseDAO implements ExerciseDAO {

    @Override
    public int saveExerciseToDB(Exercise exercise) {
        return 1;
    }

    @Override
    public Exercise loadExerciseById(int id) {
        return createMultipleExercises(1).get(0);
    }

    @Override
    public int deleteExercise(Exercise exercise) {
        return 1;
    }

    @Override
    public List<Exercise> loadAllExercises() {
        return createMultipleExercises(10);
    }
}
