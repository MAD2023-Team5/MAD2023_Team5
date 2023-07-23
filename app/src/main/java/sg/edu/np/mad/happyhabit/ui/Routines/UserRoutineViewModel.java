package sg.edu.np.mad.happyhabit.ui.Routines;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import java.io.Closeable;

import sg.edu.np.mad.happyhabit.User;

public class UserRoutineViewModel extends ViewModel {

    private User user;

    public UserRoutineViewModel(User user) {
        this.user = user;
    }

    public UserRoutineViewModel(User user, @NonNull Closeable... closeables) {
        super(closeables);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
// TODO: Implement the ViewModel
}