package pl.pjm77.DAO;

import pl.pjm77.model.Exercise;

public interface ExerciseDAO {

    void saveExerciseToDB(Exercise exercise);
    Exercise loadExerciseById(int id);
    void deleteExercise(Exercise exercise);

}
