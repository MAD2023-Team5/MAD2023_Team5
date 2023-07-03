package sg.edu.np.mad.happyhabit.ui.Routines;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import sg.edu.np.mad.happyhabit.Routine;

public class RoutineViewModel extends ViewModel {



    private  List<Routine> routinelist;
    private  List<Routine> compeleteroutinelist;








    public RoutineViewModel(List<Routine> routinelist,List<Routine> croutinelist) {
        this.routinelist=  routinelist;
        this.compeleteroutinelist=  routinelist;

        // mText.setValue("This is notifications fragment");
    }
    public  RoutineViewModel()
    {

    }

    public List<Routine> getRoutinelist() {
        return routinelist;
    }

    public void setRoutinelist(List<Routine> routinelist) {
        this.routinelist = routinelist;
    }

    public List<Routine> getCompeleteroutinelist() {
        return compeleteroutinelist;
    }

    public void setCompeleteroutinelist(List<Routine> compeleteroutinelist) {
        this.compeleteroutinelist = compeleteroutinelist;
    }
}