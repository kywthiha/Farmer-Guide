package com.farm.ngo.farm.Model;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import com.farm.ngo.farm.Service.ChatHelper;
import com.farm.ngo.farm.Service.LoadingDialog;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class ImageUpload extends AsyncTask<Image,Void,Void> {
    Context context;
    boolean isAdmin;
    public ImageUpload(Context context,boolean isAdmin){
        this.context=context;
        this.isAdmin=isAdmin;
        mProgressDialog = new LoadingDialog(context,"Image Uploading ...");
    }
    private LoadingDialog mProgressDialog;
    @Override
    protected Void doInBackground(Image... images) {
        Image image=images[0];
        upload(image);
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        showProgressDialog();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

    }
    public void showProgressDialog() {
            mProgressDialog.setCancelable(false);
             mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
    private void upload(final Image image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UUID randromid=UUID.randomUUID();
        final String path="messagesphoto/"+randromid+".jpg";
        StorageReference storageReference=FirebaseStorage.getInstance().getReference(path);
        UploadTask uploadTask=storageReference.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                StorageReference fb=FirebaseStorage.getInstance().getReference();
                fb.child(path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        DatabaseReference database=FirebaseDatabase.getInstance().getReference().child("messages");
                        String downloadURI = uri.toString();
                        Message message=null;
                        if(isAdmin) {
                           message= new Message(image.getUser().getId(), Type.admin, "", ServerValue.TIMESTAMP, downloadURI, MessageType.image, false);
                        }else {
                           message = new Message(image.getUser().getId(), Type.user, "", ServerValue.TIMESTAMP, downloadURI, MessageType.image, false);

                        }
                        database=database.child(image.getUser().getId());
                        String key = database.push().getKey();
                        database.child(key).setValue(message);
                        if(isAdmin) {
                            ChatHelper.sendChat(image.getUser().getTownship(), image.getUser().getId(), message,image.getUser().getName(), true);
                        }else {
                            ChatHelper.sendChat(image.getUser().getTownship(), image.getUser().getId(), message,image.getUser().getName(), false);

                        }
                        hideProgressDialog();
                    }
                });

            }
        });


    }

}
