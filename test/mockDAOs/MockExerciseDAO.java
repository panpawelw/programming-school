package mockDAOs;

import com.panpawelw.DAO.ExerciseDAO;
import com.panpawelw.model.Exercise;

import java.util.List;

public class MockExerciseDAO implements ExerciseDAO {

    @Override
    public int saveExerciseToDB(Exercise exercise) {
        return 0;
    }

    @Override
    public Exercise loadExerciseById(int id) {
        return null;
    }

    @Override
    public int deleteExercise(Exercise exercise) {
        return 0;
    }

    @Override
    public List<Exercise> loadAllExercises() {
        return null;
    }
}
