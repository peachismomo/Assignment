package sg.edu.np.s10179055.says;


import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.concurrent.Executor;

import static android.app.Activity.RESULT_OK;
import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class profileFragment extends Fragment {
    private TextView user, name, sNo, nric, course, email;
    private DatabaseReference r;
    private StorageTask uploadTask;
    public Uri imguri;
    ImageView img;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference("ProfilePhoto");
    final Account thisUser = new Account();

    public profileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View RootView = inflater.inflate(R.layout.fragment_profile, container, false);

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        user = RootView.findViewById(R.id.tvUser);
        name = RootView.findViewById(R.id.tvName);
        sNo = RootView.findViewById(R.id.tvStudentID);
        nric = RootView.findViewById(R.id.tvNRIC);
        course = RootView.findViewById(R.id.tvCourse);
        email = RootView.findViewById(R.id.tvEmail);
        img = RootView.findViewById(R.id.userImg);

        /*SharedPreferences currentUser = RootView.getContext().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        final String currentUsername = currentUser.getString("username", "");

        storageReference = FirebaseStorage.getInstance().getReference("ProfilePhoto");
        r = FirebaseDatabase.getInstance().getReference().child("Member");

        r.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Account CurrentUser = data.getValue(Account.class);
                    if (CurrentUser.getUsername().equals(currentUsername)) {
                        user.setText(CurrentUser.getUsername());
                        name.setText(CurrentUser.getName());
                        sNo.setText(CurrentUser.getStudentNo());
                        nric.setText(CurrentUser.getNRIC());
                        course.setText(CurrentUser.getCourse());
                        email.setText(CurrentUser.getEmail());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

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
                    Picasso.get().load(account.getImgId()).into(img);
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
 /*               if (uploadTask != null && uploadTask.isInProgress()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Photo is uploading, please wait.", Toast.LENGTH_SHORT).show();
                } else */
            }
        });
        return RootView;
    }

    public void onCameraClick(View view) {

    }

    public void fileUpload() {
        Intent fileChoose = new Intent();
        fileChoose.setType("image/*");
        fileChoose.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(fileChoose, 1);
    }

    public void upload() {
        final String imgId = System.currentTimeMillis() + "." + getExtension(imguri);

        final StorageReference ref = storageReference.child(imgId);

        ref.child(imgId).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                uploadTask = storageReference.putFile(imguri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get a URL to the uploaded content
                                String dlLink = ref.child(imgId).getDownloadUrl().toString();
                                thisUser.setFirebaseImgId(dlLink, getActivity().getApplicationContext());
                                Toast.makeText(getActivity().getApplicationContext(), "Image uploaded!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                // ...
                                Toast.makeText(getActivity().getApplicationContext(), "wtf!", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
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
            imguri = data.getData();
            img.setImageURI(imguri);
            upload();
        }
    }

    private void signInAnonymously(FirebaseAuth mAuth) {
        mAuth.signInAnonymously().addOnSuccessListener(getActivity(), new  OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                // do your stuff
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
