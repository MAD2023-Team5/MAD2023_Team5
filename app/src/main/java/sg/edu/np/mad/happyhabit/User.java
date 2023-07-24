package sg.edu.np.mad.happyhabit;

import android.graphics.Bitmap;

public class User {
    private int userNo;
    private String email;
    private String password;
    private String description;
    private Bitmap image;

    private String Name;

    public User() {
        // Default constructor required for Firebase
    }

    public User(int userNo, String email, String password, String description, Bitmap image, String name) {
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

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
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
