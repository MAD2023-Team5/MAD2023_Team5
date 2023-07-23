package sg.edu.np.mad.happyhabit.ui.Routines;

import androidx.lifecycle.ViewModel;

import java.util.List;

import sg.edu.np.mad.happyhabit.Exercise;
import sg.edu.np.mad.happyhabit.User;

public class SetCreationViewModel extends ViewModel {

    private  List<Exercise> exerciseList;

    private List<String> exericesName;



    public List<String> getexericesName() {
        return exericesName;
    }

    public void setexericesName(List<String> exericesName) {
        this.exericesName = exericesName;
    }

    public SetCreationViewModel( List<Exercise> exerciseList,List<String> exericesName) {

        this.exerciseList = exerciseList;
        this.exericesName=exericesName;


    }

    public SetCreationViewModel() {

    }



    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }
}