package pl.pjm77.DAO;

import pl.pjm77.model.Exercise;

import java.util.List;

public interface ExerciseDAO {

    int saveExerciseToDB(Exercise exercise);
    Exercise loadExerciseById(int id);
    int deleteExercise(Exercise exercise);
    List<Exercise> loadAllExercises();
}