package sg.edu.np.mad.happyhabit.ui.User;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {


    private final MutableLiveData<String> mText;

    public UserViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
    // TODO: Implement the ViewModel
}