package sg.edu.np.mad.happyhabit.ui.Profile;

import static android.content.ContentValues.TAG;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.Priority;
import com.bumptech.glide.annotation.GlideModule;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;
import java.util.Base64;

import de.hdodenhof.circleimageview.CircleImageView;
import sg.edu.np.mad.happyhabit.R;
import sg.edu.np.mad.happyhabit.User;

public class ProfilePic extends Fragment implements Serializable {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap myBitmap;
    Uri picUri;
    private byte[] imageData;

    // firebase auth, database & storage
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    private String userEmail;

    // Define the button and imageview type variable
    FloatingActionButton fab;
    ImageView imageView;

    // a static variable to get a reference of our application context
    public static Context contextOfApplication;

    public static Context getContextOfApplication() {
        return contextOfApplication;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.capture_image, container, false);

        // Floating action button (fab) & captured image
        fab = view.findViewById(R.id.fab);
        imageView = view.findViewById(R.id.pfp);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userEmail = firebaseAuth.getCurrentUser().getEmail().replace(".", "");
        // Get the reference to the current user's profile in the Firebase Realtime Database
        DatabaseReference userRef = databaseReference.child(userEmail).child("image");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Fetch the image URL from the database
                String imageUrl = snapshot.getValue(String.class);
                // Load the image into the ImageView using Glide
                Glide.with(requireActivity())
                        .load(imageUrl)
                        .error(R.drawable.blank_circle_pfp) // Replace with your error drawable
                        .into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error if necessary
                Log.e(TAG, "Failed to fetch image URL: " + error.getMessage());
            }
        });

        // Camera_open button is for open the camera and add the setOnClickListener in this button
        fab.setOnClickListener(v -> {
            captureImage();
            // Navigate back to the profile page
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.navigation_edit_profile);
        });

        return view;
    }

    // capture the image using camera
    private void captureImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        Log.e(TAG, "CAMERA USED");
    }

    // onActivityResult
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        firebaseAuth = FirebaseAuth.getInstance();
        userEmail = Objects.requireNonNull(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail()).replace(".", "");
        // Get the reference to the current user's profile in the Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference userRef = databaseReference.child(userEmail).child("image");
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            if (getPickImageResultUri(data) != null) {
                // option 1: use Uri data to get Bitmap & upload Url to storage
                picUri = getPickImageResultUri(data);
                try {
                    myBitmap = MediaStore.Images.Media.getBitmap(getContextOfApplication().getContentResolver(), picUri);
                    myBitmap = rotateImageIfRequired(myBitmap, picUri);
                    myBitmap = getResizedBitmap(myBitmap, 500);
                    UploadImageToFirebase(picUri);
                    Log.e(TAG, "ACTIVITY SUCCESSFUL");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Option 2: Use Bundle to get Bitmap & upload byte data to storage instead
                Bundle extras = data.getExtras();
                assert extras != null;
                myBitmap = (Bitmap) extras.get("data");
                assert myBitmap != null;
                myBitmap = getResizedBitmap(myBitmap, 500);
                // Convert the Bitmap image to a byte array
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                imageData = bytes.toByteArray();

                // Upload the image to Firebase Cloud Storage and get the download URL
                uploadFirebaseImageAndGetUrl();
                Log.e(TAG, "ACTIVITY SUCCESSFUL");
            }
        } else {
            //cancelled
            Toast.makeText(getActivity(), "Cancelled...", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "ACTIVITY FAILED");
        }
    }

    // Get URI to image received from capture by camera.
    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = ((AppCompatActivity) requireActivity()).getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }

    //     Get the URI of the selected image
//     Will return the correct URI for camera and gallery image.
//     @param data the returned data of the activity result
    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        Log.e(TAG, "IMAGE COLLECTED");
        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }

    // upload & store image into firebase storage (Url Method)
    public void UploadImageToFirebase(Uri image) {
        // Create a unique filename for the image
        String filename = "image_" + System.currentTimeMillis() + ".jpg";
        storageReference = FirebaseStorage.getInstance().getReference();
        final StorageReference fileRef = storageReference.child("pfp_images/" + userEmail + "/" + filename);

        // Get the reference to the current user's profile in the Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference userRef = databaseReference.child(userEmail).child("image");

        fileRef.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Image upload successful, now get the download URL
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri downloadUri) {
                        String imageUrl = downloadUri.toString();
                        // Store the imageUrl in Firebase Realtime Database
                        userRef.setValue(imageUrl).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                fetchUserData(userEmail, imageUrl);
                                // URL stored successfully in the database
                                Log.d(TAG, "IMAGE UPLOADED: URL IS " + downloadUri);
                                Toast.makeText(getActivity(), "Image uploaded successfully!", Toast.LENGTH_SHORT).show();
                            } else {
                                // Handle any errors that occurred while saving the URL
                                Log.e(TAG, "Failed to update profile picture");
                            }
                        });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Failed to update profile picture");
            }
        });
    }

    // upload & store image into firebase storage (Byte Method)
    public void uploadFirebaseImageAndGetUrl() {
        if (imageData == null) {
            Toast.makeText(getActivity(), "No image to upload", Toast.LENGTH_SHORT).show();
            return;
        }
        // Create a unique filename for the image
        String filename = "image_" + System.currentTimeMillis() + ".jpg";

        // Create a reference to the image file in Firebase Cloud Storage
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = storageReference.child("pfp_images/" + userEmail + "/" + filename);
        // Get the reference to the current user's profile in the Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference userRef = databaseReference.child(userEmail).child("image");

        // Upload the image byte array to Firebase Cloud Storage
        UploadTask uploadTask = imageRef.putBytes(imageData);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Image upload successful, now get the download URL
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri downloadUri) {
                        // Download URL obtained
                        String imageUrl = downloadUri.toString();
                        // Store the imageUrl in Firebase Realtime Database under the user's node (identified by their email address)
                        userRef.setValue(imageUrl).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                fetchUserData(userEmail, imageUrl);
                                // URL stored successfully in the user's node
                                Log.d(TAG, "Image URL uploaded to user's node: " + downloadUri);
                                Toast.makeText(getActivity(), "Image uploaded successfully!", Toast.LENGTH_SHORT).show();
                            } else {
                                // Handle any errors that occurred while saving the URL
                                Log.e(TAG, "Failed to update profile picture");
                            }
                        });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Image upload failed
                Log.e(TAG, "Image upload failed: " + e.getMessage());
            }
        });
    }

    private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {

        ExifInterface ei = new ExifInterface(Objects.requireNonNull(selectedImage.getPath()));
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    // fetch the data from User class & set image into user data
    private void fetchUserData(String userEmail, String imageUrl) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference userRef = usersRef.child(userEmail);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // The user with the provided userEmail exists in the database
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        // access user properties like name, email, etc. using 'user' object
                        user.setImage(imageUrl);
                    }
                } else {
                    // The user with the provided userEmail does not exist in the database
                    Log.e(TAG, "USER DOES NOT EXIST");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that occurred while fetching the data
                Log.e(TAG, "Failed to fetch user data: " + databaseError.getMessage());
            }
        });
    }
}