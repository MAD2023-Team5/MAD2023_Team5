package sg.edu.np.mad.happyhabit.ui.SignIn;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SignUpviewmodel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SignUpviewmodel() {
        mText = new MutableLiveData<>();
        // mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
    // TODO: Implement the ViewModel
    // TODO: Implement the ViewModel
}