package sg.edu.np.mad.happyhabit;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

@SuppressLint("ParcelCreator")
public class Routine extends AbstractRoutine implements Serializable {
    private int routineNo;
    private List<Sets> setsList;
    private User user;
    private String description;

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private List<String> Tags;
    private int LikeCount;

    private int DislikeCount;



    public Routine() {
        // Default constructor required for Firebase
    }

    public Routine(int routineNo, User user, String description,List<String> tags) {
        this.routineNo = routineNo;
        this.setsList = setsList;
        this.user = user;
        this.description = description;
        this.Tags=tags;

    }

    public Routine(int routineNo, User user, String description, List<String> tags, List<Sets> setsList,int likeCount, int dislikeCount) {
        this.routineNo = routineNo;
        this.setsList = setsList;
        this.user = user;
        this.description = description;
        LikeCount = likeCount;
        DislikeCount = dislikeCount;
        this.Tags=tags;
    }
    // Getters and setters

    public List<String> getTags() {
        return Tags;
    }

    public void setTags(List<String> tags) {
        Tags = tags;
    }

    public List<Sets> getSetsList() {
        return setsList;
    }

    public void setSetsList(List<Sets> setsList) {
        this.setsList = setsList;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public int getLikeCount() {
        return LikeCount;
    }

    public void setLikeCount(int likeCount) {
        LikeCount = likeCount;
    }

    public int getDislikeCount() {
        return DislikeCount;
    }

    public void setDislikeCount(int dislikeCount) {
        DislikeCount = dislikeCount;
    }

    public int getRoutineNo() {
        return routineNo;
    }

    public void setRoutineNo(int routineNo) {
        this.routineNo = routineNo;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void AddSets(Sets sets)

    {
        if (this.checkValue(sets)==false)
        {
            setsList.add(sets);
        }

    }

    public void RemoveSets(Sets sets)

    {
        //change the return value if needed


        if (this.checkValue(sets)==true)
        {
            setsList.remove(sets);
        }
    }

    public boolean checkValue(Sets sets)
    {

        //check if the value is inside first
        for (Sets i: setsList)
        {
            if (i==sets)
            {

                return true;
            }

        }

        return false;

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {

    }
}