package sg.edu.np.mad.happyhabit.ui.Profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import sg.edu.np.mad.happyhabit.User;

public class ProfilePicViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // for live data later during changes in pfp
    private MutableLiveData<String> imageUrlLiveData = new MutableLiveData<>();

    public LiveData<String> getImageUrlLiveData() {
        return imageUrlLiveData;
    }

    public void setImageUrl(String imageUrl) {
        imageUrlLiveData.setValue(imageUrl);
    }
    private User user;
    public ProfilePicViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
    // TODO: Implement the ViewModel
}