package com.example.np.mad.happy_habbits.ui.SignIn;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.np.mad.happy_habbits.BrowsingRoutines;
import com.example.np.mad.happy_habbits.MainActivity;
import com.example.np.mad.happy_habbits.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInViewModel extends ViewModel {
    private String title ="SignIN";

    private  final MutableLiveData<String> mText ;
    private MutableLiveData<Boolean> loginResult = new MutableLiveData<>();

    public SignInViewModel() {

        mText = new MutableLiveData<>();;
    }
    public LiveData<Boolean> getLoginResult() {
        return loginResult;
    }

    public  LiveData<String> getText() {
        return mText;
    }

    public void isValidCredential(String username, String password) {

        DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference("Users").child(username.replace(".", ""));
        Log.i(title, String.valueOf(mPostReference));

        ValueEventListener postListener = new ValueEventListener() {

            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {


                // Get Post object and use the values to update the UI
                Log.i(title,"in listerner" +  dataSnapshot.exists());


                try{



                    User user = dataSnapshot.getValue(User.class);
                    Log.i(title, String.valueOf(user.getPassword()));

                    Log.i(title, "email " + user.getEmail() + username.trim() + String.valueOf(user.getEmail() == username.trim()));
                    Log.i(title, "password " + String.valueOf(user.getPassword() == password));


                    if (user.getEmail().equals(username) & user.getPassword().equals("password123")) {
                        Log.i("viewmodel", "true");
                        loginResult.setValue(true);

                    } else {
                        Log.i("viewmodel", "true");

                        loginResult.setValue(false);

                    }
                }
                catch(java.lang.NullPointerException e)
                {
                    Log.i(title, String.valueOf(dataSnapshot));
                    loginResult.setValue(false);

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.i(title, "loadPost:onCancelled", databaseError.toException());
                //loginResult.setValue(false);

            }
        };
        Log.i(title, String.valueOf(mPostReference.equalTo(null)));

        try {
            mPostReference.addValueEventListener(postListener);
        }
        catch(java.lang.NullPointerException e){
            loginResult.setValue(false);

        }//loginResult.setValue(true);



    }



        // TODO: Implement the ViewModel
}
