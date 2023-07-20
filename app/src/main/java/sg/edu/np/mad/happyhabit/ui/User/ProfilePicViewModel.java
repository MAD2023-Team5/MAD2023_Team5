package sg.edu.np.mad.happyhabit.ui.User;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfilePicViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public ProfilePicViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
    // TODO: Implement the ViewModel
}