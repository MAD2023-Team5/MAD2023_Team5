package sg.edu.np.mad.happyhabit.ui.User;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.ContentValues.TAG;

import static sg.edu.np.mad.happyhabit.FirebaseDataUploader.onUpdate;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import sg.edu.np.mad.happyhabit.FirebaseDataUploader;
import sg.edu.np.mad.happyhabit.R;

public class ProfilePic extends Fragment implements Serializable {
    Bitmap myBitmap;
    Uri picUri;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private ProfilePicViewModel viewModel;
    
    // Define the button and imageview type variable
    FloatingActionButton fab;
    CircleImageView click_image_id;

    // a static variable to get a reference of our application context
    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }

    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK
                        && result.getData() != null) {
                    picUri = result.getData().getData();
                    //use photoUri here
                }
            }
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.capture_image, container, false);

        // Floating action button (fab) & captured image
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        click_image_id = (CircleImageView) view.findViewById(R.id.img_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        String userEmail = firebaseAuth.getCurrentUser().getEmail().replace(".", "");;
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        viewModel = new ViewModelProvider(requireActivity()).get(ProfilePicViewModel.class);
        databaseReference.child(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data = dataSnapshot.child("image").getValue(String.class);
                Bitmap image = StringToBitMap(data);
                click_image_id.setImageBitmap(image);
                Log.e(TAG, "Database Read");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Log.e(TAG, "Database Error");
            }
        });

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // Here we need to check if the activity that was triggers was the Image Camera.
                    // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
                    // Determine URI of camera image to save.

                    Intent data = result.getData();
                    contextOfApplication = requireActivity().getApplicationContext();
                    Context applicationContext = ProfilePic.getContextOfApplication();
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        ImageView imageView = view.findViewById(R.id.imageView);
                        if (getPickImageResultUri(data) != null) {
                            picUri = getPickImageResultUri(data);

                            // BitMap is data structure of image file which store the image in memory
                            try {
                                myBitmap = MediaStore.Images.Media.getBitmap(applicationContext.getContentResolver(), picUri);
                                myBitmap = getResizedBitmap(myBitmap, 500);

                                CircleImageView croppedImageView = click_image_id;
                                croppedImageView.setImageBitmap(myBitmap);
                                imageView.setImageBitmap(myBitmap);
                                String newImage = BitMapToString(myBitmap);
                                databaseReference.child(userEmail).child(newImage).setValue(newImage);
                                UploadImageToFirebaseStorage(picUri);
                                Log.v(TAG, "ACTIVITY SUCCESSFUL");
                            }
                            catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        else {
                            //cancelled
                            Toast.makeText(ProfilePic.getContextOfApplication(), "Cancelled...", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "ACTIVITY FAILED");
                        }
                    }
                });
        // Camera_open button is for open the camera and add the setOnClickListener in this button
        fab.setOnClickListener(v -> {
            // Create the camera_intent ACTION_IMAGE_CAPTURE it will open the camera for capture the image
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Start the activity with camera_intent
            launcher.launch(intent);
            // Navigate back to the profile page
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.navigation_edit_profile);
        });

        return view;
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


// upload & store image into firebase storage
    private void UploadImageToFirebaseStorage(Uri image) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference fileRef = storageReference.child("images/profile.png");
        fileRef.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(requireActivity().getApplicationContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
                Log.v(TAG, "IMAGE UPLOADED");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(requireActivity().getApplicationContext(), "Failed to update profile picture", Toast.LENGTH_LONG).show();
            }
        });
    }

//    private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {
//
//        ExifInterface ei = new ExifInterface(selectedImage.getPath());
//        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//
//        switch (orientation) {
//            case ExifInterface.ORIENTATION_ROTATE_90:
//                return rotateImage(img, 90);
//            case ExifInterface.ORIENTATION_ROTATE_180:
//                return rotateImage(img, 180);
//            case ExifInterface.ORIENTATION_ROTATE_270:
//                return rotateImage(img, 270);
//            default:
//                return img;
//        }
//    }
//
//    private static Bitmap rotateImage(Bitmap img, int degree) {
//        Matrix matrix = new Matrix();
//        matrix.postRotate(degree);
//        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
//        img.recycle();
//        return rotatedImg;
//    }

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

    // Convert bitmap image to string for database
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    // Convert string from database to bitmap image
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        // save file url in bundle as it will be null on screen orientation
//        // changes
//        if (outState != null) {
//            outState.putParcelable("pic_uri", picUri);
//        } else {
//            Log.e(TAG, "Instance State null");
//        }
//    }

    @TargetApi(Build.VERSION_CODES.M)
    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            result -> {
                if (result) {
                    Log.e(TAG, "onActivityResult: PERMISSION GRANTED");
                    // PERMISSION GRANTED
                } else {
                    Log.e(TAG, "onActivityResult: PERMISSION DENIED");
                    // PERMISSION DENIED
                }
            }
    );
}
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        Log.d("onOptionsItemSelected","yes");
//        if (item.getItemId() == android.R.id.home)
//            NavUtils.navigateUpFromSameTask(this);
//        return true;
//    }
//    return super.onOptionsItemSelected(item);
//}
//
//    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
//        ArrayList<String> result = new ArrayList<String>();
//
//        for (String perm : wanted) {
//            if (!hasPermission(perm)) {
//                result.add(perm);
//            }
//        }
//
//        return result;
//    }

