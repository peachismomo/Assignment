package sg.edu.np.s10179055.says;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static android.os.Environment.getExternalStoragePublicDirectory;


/**
 * A simple {@link Fragment} subclass.
 */
public class profileFragment extends Fragment {
    final Account thisUser = new Account();
    ImageView img;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    String pathToFile;
    Uri photoUri;
    private TextView user, name, sNo, nric, course, email, wlc;


    public profileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View RootView = inflater.inflate(R.layout.fragment_profile, container, false);

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }

        user = RootView.findViewById(R.id.tvUser);
        name = RootView.findViewById(R.id.tvName);
        sNo = RootView.findViewById(R.id.tvStudentID);
        nric = RootView.findViewById(R.id.tvNRIC);
        course = RootView.findViewById(R.id.tvCourse);
        email = RootView.findViewById(R.id.tvEmail);
        img = RootView.findViewById(R.id.userImg);
        wlc = RootView.findViewById(R.id.TextView5);
        wlc.setText("Welcome back,");


        thisUser.getCurrentUser(getActivity().getApplicationContext(), new Account.callBack() {
            @Override
            public void onCallBack(Account account) {
                user.setText(account.getUsername());
                name.setText(account.getName());
                sNo.setText(account.getStudentNo());
                nric.setText(account.getNRIC());
                course.setText(account.getCourse());
                email.setText(account.getEmail());
                if (!account.getImgId().equals("none")) {
                    //Retrieve image from database by username and image id
                    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                    storageRef.child(account.getUsername() + "/" + account.getImgId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            //use glide to load image into imgview
                            Glide.with(getActivity().getApplicationContext()).load(uri).into(img);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getContext(), "Error loading image", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        final Button changePassword = RootView.findViewById(R.id.btnChangePass);

        //Go to change password activity
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RootView.getContext(), changePassword.class);
                startActivity(i);
            }
        });

        //When user uploads photo
        final Button uploadPhoto = RootView.findViewById(R.id.btnUploadImage);
        uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileUpload();
            }
        });

        //When user wants to takes picture with camera
        final Button cameraBtn = RootView.findViewById(R.id.btnTakePic);
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCameraClick();
            }
        });

        final Button changeModeBtn = RootView.findViewById(R.id.btnChangeMode);
        changeModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String currentusername = thisUser.getCurrentUsername(getContext());
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Member");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot memberSnapshot : dataSnapshot.getChildren()) {
                            Account current = memberSnapshot.getValue(Account.class);
                            if(current.getUsername().equals(currentusername)){
                                if(current.getMode()==1){
                                    reference.child(memberSnapshot.getKey()).child("mode").setValue(0);
                                    Toast.makeText(getContext(),"Mode set to offline.", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    reference.child(memberSnapshot.getKey()).child("mode").setValue(1);
                                    Toast.makeText(getContext(),"Mode set to online.", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        return RootView;
    }

    //Open camera
    public void onCameraClick() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (camera.resolveActivity(getContext().getPackageManager()) != null) {
            File file = null;
            file = createPhotoFile();

            if (file != null) {
                pathToFile = file.getAbsolutePath();
                photoUri = FileProvider.getUriForFile(getActivity(), "sg.edu.np.s10179055.says.provider", file);
                camera.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(camera, 2);
            }
        }
    }

    //Open image gallery
    public void fileUpload() {
        Intent fileChoose = new Intent();
        fileChoose.setType("image/*");
        fileChoose.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(fileChoose, 1);
    }

    //Upload photo to firebase storage as uri
    public void upload(final Uri imguri) {
        final Account current = new Account();
        final String imgId = System.currentTimeMillis() + "." + getExtension(imguri);
        current.setFirebaseImgId(imgId, getActivity().getApplicationContext());

        //User's profile photos are stored under their username
        final StorageReference ref = storageReference.child(current.getCurrentUsername(getContext()) + "/" + imgId);

        ref.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getActivity().getApplicationContext(), "Image uploaded", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    }
                });
    }

    //Create image with temporary file
    public File createPhotoFile() {
        File image = null;
        String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storage = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        try {
            image = File.createTempFile(name, ".jpg", storage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    //Get extension of uri
    private String getExtension(Uri uri) {
        ContentResolver contentResolver = getActivity().getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    //request code 1 is upload photo, 2 is camera
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imguri = data.getData();
            img.setImageURI(imguri);
            //upload to database
            upload(imguri);
        }
        if (requestCode == 2) {
            Bitmap bitmap = BitmapFactory.decodeFile(pathToFile);
            img.setImageBitmap(bitmap);
            //upload to database
            upload(photoUri);
        }
    }
}
