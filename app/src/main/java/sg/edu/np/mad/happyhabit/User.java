package sg.edu.np.mad.happyhabit;

import static java.lang.System.out;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class User implements Serializable {
    private int userNo;
    private String email;
    private String password;
    private String description;
    private String image;
    private String Name;

    public User() {
        // Default constructor required for Firebase
    }

    public User(int userNo, String email, String password, String description, String image, String name) {
        this.userNo = userNo;
        this.email = email;
        this.password = password;
        this.description = description;
        this.image = image;
        this.Name=name;
    }

    // Getters and setters
    public String getName(){return Name;}

    public void setName(String name)
    {
        this.Name=name;

    }
    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {return image;}
    public void setImage(String image) {
        this.image = image;
    }
    public void read_database()
    {

        //convert class to database//
    }
    public void write_database()
    {




    }
}
