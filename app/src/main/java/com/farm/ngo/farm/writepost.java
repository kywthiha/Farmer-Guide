package com.farm.ngo.farm;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.farm.ngo.farm.FarmStatic.RefStatic;
import com.farm.ngo.farm.Model.Post;
import com.farm.ngo.farm.Utility.FileUtil;
import com.farm.ngo.farm.Utility.ImageUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static android.content.DialogInterface.BUTTON_POSITIVE;

public class writepost extends AppCompatActivity {
    private DatabaseReference mDatabase=RefStatic.postRef;
    private FirebaseStorage storage=FirebaseStorage.getInstance();
    private StorageReference storageReference= storage.getReference();
    private Uri filePath;
    private String UploadImageUrl;
    String s1,s2,today;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        final Button uploadPost= findViewById(R.id.uploadPost);
        Button selectImage= findViewById(R.id.selectImage);
        uploadPost.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(final View v) {

                AlertDialog.Builder alert=new AlertDialog.Builder(writepost.this)
                        .setCancelable(false)
                        .setTitle("Share post")
                        .setMessage("Are you sure that you want to share post?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Post", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                EditText t1= findViewById(R.id.titleCreatePost);
                                s1=t1.getText().toString();
                                EditText t2= findViewById(R.id.infoCreatePost);
                                s2=t2.getText().toString();
                                Date todayDate = Calendar.getInstance().getTime();
                                SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
                                today = formatter.format(todayDate);
                                SimpleDateFormat formatter2 = new SimpleDateFormat("h:mm a");
                                today+=" at "+formatter2.format(todayDate);

                                //start indsert img
                                if(filePath != null)
                                {
                                    final ProgressDialog progressDialog = new ProgressDialog(writepost.this);
                                    progressDialog.setTitle("Uploading...");
                                    progressDialog.show();
                                     File actualImage=null;
                                    try {
                                         actualImage = FileUtil.from(v.getContext(), filePath);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    ImageUtil imageUtil=new ImageUtil(actualImage,v.getContext(), null,true);
                                    Bitmap bitmap= BitmapFactory.decodeFile(actualImage.getAbsolutePath());
                                    bitmap.compress(Bitmap.CompressFormat.WEBP, 100, baos);
                                    byte[] data = baos.toByteArray();
                                    UploadImageUrl="po/"+ UUID.randomUUID().toString();
                                    final StorageReference ref = storageReference.child(UploadImageUrl);
                                    ref.putBytes(  data)
                                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    //start
                                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                          String id=mDatabase.push().getKey();
                                                            // Got the download URL for 'users/me/profile.png'
                                                            Post user = new Post(id,s1,s2,today,uri.toString(),2);
                                                            mDatabase.child(id).setValue(user);

                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception exception) {
                                                            // Handle any errors
                                                        }
                                                    });
                                                    progressDialog.dismiss();
                                                    Toast.makeText(writepost.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(writepost.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                                    double progress = ((100.0*taskSnapshot.getBytesTransferred())/taskSnapshot
                                                            .getTotalByteCount());
                                                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
                                                }
                                            });

                                }
                                else{
                                    String id=mDatabase.push().getKey();
                                    Post user = new Post(id,s1,s2,today,1);
                                    mDatabase.child(id).setValue(user);
                                    finish();
                                }
                            }})
                        .setNegativeButton(android.R.string.no,  new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = alert.create();
                dialog.show();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));

            }
        });



        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);

            }
        });


    }//oncreate


    public void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode==1 && resultCode==Activity.RESULT_OK && data!=null ){
            filePath=data.getData();
          Bitmap bitmap = null;

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);

            } catch (IOException e) {
                e.printStackTrace();
            }

            ImageView img= findViewById(R.id.img);

            img.setImageBitmap(bitmap);
        }
    }




}

