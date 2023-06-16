//package com.example.np.mad.happy_habbits;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.gson.Gson;
//
//import java.util.HashMap;
//
//public abstract class FireBase {
//
//    private DatabaseReference databaseReference;
//
//    public FireBase() {
//        // Default constructor required for Firebase
//        databaseReference = FirebaseDatabase.getInstance().getReference();
//    }
//
//    public void setvalue(Integer Id) {
//        // Logic to update specific data in Firebase
//        // using the key and newData parameters
//        String key = Id.toString();
//        HashMap<String, FireBase> datamap = new HashMap<>();
//        datamap.put(key,this);
//        Gson gson = new Gson();
//        String json = gson.toJson(datamap);
//        DatabaseReference childRef = databaseReference.child(getChildNode());
//        childRef.child(key).setValue(json);
//    }
//
//    public void getvalue(String key) {
//        // Logic to delete specific data from Firebase
//        // using the key parameter
//        DatabaseReference childRef = databaseReference.child(getChildNode());
//        childRef.child(key).removeValue();
//    }
//
//    protected abstract String getChildNode();
//
//
//
//
//}
