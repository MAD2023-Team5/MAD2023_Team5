package sg.edu.np.mad.happyhabit.ui.Routines;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import java.io.Closeable;
import java.util.List;

import sg.edu.np.mad.happyhabit.Exercise;
import sg.edu.np.mad.happyhabit.Routine;
import sg.edu.np.mad.happyhabit.User;


public class RoutineCreationViewModel extends ViewModel {

    private List<Integer> routinelist;
    private  List<Exercise> exerciseList;

    private List<String> exericesName;

    private String URL;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user;

    public List<String> getexericesName() {
        return exericesName;
    }



    public void setexericesName(List<String> exericesName) {
        this.exericesName = exericesName;
    }

    public RoutineCreationViewModel(List<Integer> routinelist, List<Exercise> exerciseList,List<String> exericesName,User user) {
        this.routinelist = routinelist;
        this.exerciseList = exerciseList;
        this.exericesName=exericesName;
        this.user=user;

    }

    public RoutineCreationViewModel() {

    }

    public List<Integer> getRoutinelist() {
        return routinelist;
    }

    public void setRoutinelist(List<Integer> routinelist) {
        this.routinelist = routinelist;
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
// TODO: Implement the ViewModel
}