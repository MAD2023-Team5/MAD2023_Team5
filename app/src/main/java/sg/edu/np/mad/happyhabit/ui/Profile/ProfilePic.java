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
import com.bumptech.glide.Priority;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
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

public class ProfilePic extends Fragment implements Serializable  {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap myBitmap;
    Uri picUri;
    private byte[] imageData;

    // firebase storage
    private FirebaseAuth firebaseAuth;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private String userEmail;
    private ProfilePicViewModel viewModel;
    
    // Define the button and imageview type variable
    FloatingActionButton fab;
    CircleImageView circle_image_id;
    ImageView imageView;

    // a static variable to get a reference of our application context
    public static Context contextOfApplication;
    public static Context getContextOfApplication() { return contextOfApplication; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.capture_image, container, false);

        // Floating action button (fab) & captured image
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        circle_image_id = (CircleImageView) view.findViewById(R.id.img_profile);
        imageView = view.findViewById(R.id.pfp);
        firebaseAuth = FirebaseAuth.getInstance();
        userEmail = Objects.requireNonNull(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail()).replace(".", "");

//        // Initialize Glide with default options
//        RequestOptions requestOptions = new RequestOptions()
//                .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache both original and resized images
//                .skipMemoryCache(true) // Skip caching images in memory to save memory
//                .priority(Priority.HIGH); // Set high priority for image loading

//        StorageReference imagesRef = storageReference.child("pfp_images/" + userEmail);
//
//        // Fetch the list of images in the folder
//        imagesRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
//            @Override
//            public void onSuccess(ListResult listResult) {
//                if (imagesRef != null) {
//                    // Get the list of items (images) in the folder
//                    List<StorageReference> items = listResult.getItems();
//
//                    // Sort the items by timeCreated (timestamp) in descending order
//                    items.sort(new Comparator<StorageReference>() {
//                        @Override
//                        public int compare(StorageReference o1, StorageReference o2) {
//                            return o2.getName().compareTo(o1.getName());
//                        }
//                    });
//
//                    // Get the reference to the last image (most recent upload)
//                    StorageReference lastImageRef = items.get(0);
//
//                    // Download the last image from Firebase Cloud Storage
//                    lastImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            // Load the image into the ImageView using Glide
//                            RequestOptions requestOptions = new RequestOptions();
//
//                            Glide.with(requireContext())
//                                    .load(uri)
//                                    .apply(requestOptions)
//                                    .into(imageView);
//                        }
//                    });
//                }
//                else {
//
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.e(TAG, "No pfp to show");
//            }
//        });

        viewModel = new ViewModelProvider(requireActivity()).get(ProfilePicViewModel.class);

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
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            if (getPickImageResultUri(data) != null) {
                // option 1: use Uri data to get Bitmap & upload Url to storage
                picUri = getPickImageResultUri(data);
                try {
                    myBitmap = MediaStore.Images.Media.getBitmap(getContextOfApplication().getContentResolver(), picUri);
                    myBitmap = rotateImageIfRequired(myBitmap, picUri);
                    myBitmap = getResizedBitmap(myBitmap, 500);
                    CircleImageView croppedImageView = circle_image_id;
                    croppedImageView.setImageBitmap(myBitmap);
//                    imageView.setImageBitmap(myBitmap);
//                    int newImage = BitMapToString(myBitmap).hashCode();
//                    UploadImageToFirebaseDatabase(userEmail, myBitmap);
                    UploadImageToFirebaseStorage(picUri);
                    Log.e(TAG, "ACTIVITY SUCCESSFUL");
                }
                catch (IOException e) {e.printStackTrace();}
            }
            else {
                // Option 2: Use Bundle to get Bitmap & upload byte data to storage instead
                Bundle extras = data.getExtras();
                assert extras != null;
                myBitmap = (Bitmap) extras.get("data");
                assert myBitmap != null;
                myBitmap = getResizedBitmap(myBitmap, 500);
                CircleImageView croppedImageView = circle_image_id;
                croppedImageView.setImageBitmap(myBitmap);
//                imageView.setImageBitmap(myBitmap);
//                int newImage = BitMapToString(myBitmap).hashCode();
//                UploadImageToFirebaseDatabase(userEmail, myBitmap);
                // Convert the Bitmap image to a byte array
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                imageData = bytes.toByteArray();

                // Upload the image to Firebase Cloud Storage and get the download URL
                uploadFirebaseImageAndGetUrl();
                Log.e(TAG, "ACTIVITY SUCCESSFUL");
            }
        }
        else {
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

//    // upload image into firebase database
//    private void UploadImageToFirebaseDatabase(String imageId, Bitmap newImage) {
//
//        // Encode the image byte array into Base 64 hash and upload it to Firebase Database
//        databaseReference.child(imageId).child("image").setValue(newImage)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        // Image upload successful
//                        Toast.makeText(getActivity(), "Image uploaded successfully!", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // Image upload failed
//                        Toast.makeText(getActivity(), "Image upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//    }

// upload & store image into firebase storage (Url Method)
    private void UploadImageToFirebaseStorage(Uri image) {
        // Create a unique filename for the image
        String filename = "image_" + System.currentTimeMillis() + ".jpg";
        final StorageReference fileRef = storageReference.child("pfp_images/" + userEmail + "/" + filename);

        fileRef.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Image upload successful, now get the download URL
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri downloadUri) {
                        // Initialize Glide with default options
                        RequestOptions requestOptions = new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache both original and resized images
                                .skipMemoryCache(true) // Skip caching images in memory to save memory
                                .priority(Priority.HIGH); // Set high priority for image loading
                        // Load the image into the ImageView using an image loading library like Glide or Picasso
                        Glide.with(requireActivity())
                                .load(picUri)
                                .apply(requestOptions)
                                .into(circle_image_id);
                        Log.d(TAG, "IMAGE UPLOADED: URL IS " + picUri.toString());
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG,"Failed to update profile picture");
            }
        });
    }

    // upload & store image into firebase storage (Byte Method)
    private void uploadFirebaseImageAndGetUrl() {
        if (imageData == null) {
            Toast.makeText(getActivity(), "No image to upload", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a unique filename for the image
        String filename = "image_" + System.currentTimeMillis() + ".jpg";

        // Create a reference to the image file in Firebase Cloud Storage
        StorageReference imageRef = storageReference.child("pfp_images/" + userEmail + "/" + filename);

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
                        // Initialize Glide with default options
                        RequestOptions requestOptions = new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache both original and resized images
                                .skipMemoryCache(true) // Skip caching images in memory to save memory
                                .priority(Priority.HIGH); // Set high priority for image loading
                        // Load the image into the ImageView using an image loading library like Glide or Picasso
                        Glide.with(requireActivity())
                                .load(imageUrl)
                                .apply(requestOptions)
                                .into(circle_image_id);

                        // Can now use the imageUrl for any further use, such as storing it in Firebase Database or using it in the app.
                        // displaying it in a Log message.
                        Log.e(TAG, "Image uploaded successfully! URL: " + imageUrl);
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

    // Convert bitmap image to string for database
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b = baos.toByteArray();
        return Base64.getEncoder().encodeToString(b);
    }

    // Convert string from database to bitmap image
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= new byte[0];
            encodeByte = Base64.getDecoder().decode(encodedString);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
//    // Convert hash codes of strings to bitmap image
//    public static Bitmap createBitmapFromImageData(int width, int height, Byte[] imageData) {
//        if (imageData == null) {
//            return null;
//        }
//
//        try {
//            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//            // Convert the byte array into a Bitmap
//            // Set the pixel values to the Bitmap
//            int[] pixels = new int[imageData.length];
//            for (int i = 0; i < imageData.length; i++) {
//                pixels[i] = imageData.get(i);
//            }
//            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//
//            return bitmap;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

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

