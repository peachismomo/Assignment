package sg.edu.np.s10179055.says;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static android.os.Environment.getExternalStoragePublicDirectory;
import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class profileFragment extends Fragment {
    private TextView user, name, sNo, nric, course, email;
    ImageView img;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    final Account thisUser = new Account();
    String pathToFile;
    Uri photoUri;

    public profileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View RootView = inflater.inflate(R.layout.fragment_profile, container, false);
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
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
                    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                    storageRef.child(account.getUsername() + "/" + account.getImgId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(getActivity().getApplicationContext()).load(uri).into(img);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                        }
                    });
                }
            }
        });

        final Button changePassword = RootView.findViewById(R.id.btnChangePass);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RootView.getContext(), changePassword.class);
                startActivity(i);
            }
        });

        final Button uploadPhoto = RootView.findViewById(R.id.btnUploadImage);
        uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    fileUpload();
                } else {
                    signInAnonymously(mAuth);
                }
            }
        });

        final Button cameraBtn = RootView.findViewById(R.id.btnTakePic);
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCameraClick();
            }
        });

        return RootView;
    }

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

    public void fileUpload() {
        Intent fileChoose = new Intent();
        fileChoose.setType("image/*");
        fileChoose.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(fileChoose, 1);
    }

    public void upload(final Uri imguri) {
        final Account current = new Account();
        final String imgId = System.currentTimeMillis() + "." + getExtension(imguri);
        current.setFirebaseImgId(imgId, getActivity().getApplicationContext());

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

    private String getExtension(Uri uri) {
        ContentResolver contentResolver = getActivity().getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imguri = data.getData();
            img.setImageURI(imguri);
            upload(imguri);
        }
        if (requestCode == 2) {
            Bitmap bitmap = BitmapFactory.decodeFile(pathToFile);
            img.setImageBitmap(bitmap);
            upload(photoUri);
        }
    }

    private void signInAnonymously(FirebaseAuth mAuth) {
        mAuth.signInAnonymously().addOnSuccessListener(getActivity(), new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

            }
        })
                .addOnFailureListener(getActivity(), new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e(TAG, "signInAnonymously:FAILURE", exception);
                    }
                });
    }
}
