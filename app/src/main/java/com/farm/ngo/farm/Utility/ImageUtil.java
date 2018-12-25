package com.farm.ngo.farm.Utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.farm.ngo.farm.Model.Message;
import com.farm.ngo.farm.Model.MessageType;
import com.farm.ngo.farm.Model.Type;
import com.farm.ngo.farm.Model.User;
import com.farm.ngo.farm.Service.ChatHelper;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.UUID;

import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class ImageUtil {
    boolean isAdmin;
    private ProgressBar upload_load;
    private Bitmap bitmap;
    private String path;
    private StorageReference fb=FirebaseStorage.getInstance().getReference();
    DatabaseReference database=FirebaseDatabase.getInstance().getReference().child("messages");
    private File actualImage;
    private File compressedImage;
    private String downloadURI;
    private Context context;
    private User user;
    private LoadingDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new LoadingDialog(context);
            mProgressDialog.setCancelable(false);

        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void setUpload_load(ProgressBar upload_load) {
        this.upload_load = upload_load;
    }
    public String getPath() {
        return path;
    }
    public File getActualImage() {
        return actualImage;
    }
    public void setActualImage(File actualImage) {
        this.actualImage = actualImage;
    }
    public File getCompressedImage() {
        return compressedImage;
    }
    public String getDownloadURI() {
        return downloadURI;
    }
    public Context getContext() {
        return context;
    }
    public void setContext(Context context) {
        this.context = context;
    }
    public ImageUtil(File actualImage, Context context, User user,boolean admin) {
        this.actualImage=actualImage;
        this.isAdmin=admin;
        this.user=user;
        this.context = context;
    }

    private void upload() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.WEBP, 100, baos);
        byte[] data = baos.toByteArray();
        UUID randromid=UUID.randomUUID();
        path="messagesphoto/"+randromid+".webp";
        StorageReference storageReference=FirebaseStorage.getInstance().getReference(path);
        UploadTask uploadTask=storageReference.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fb.child(path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        downloadURI = uri.toString();
                        Message message=null;
                        if(isAdmin) {
                           message = new Message(user.getId(), Type.admin, "", ServerValue.TIMESTAMP, downloadURI, MessageType.image, false);
                        }else{
                          message=new Message(user.getId(),Type.user,"",ServerValue.TIMESTAMP,downloadURI,MessageType.image,false);
                        }
                        database=database.child(user.getId());
                        String key = database.push().getKey();
                        database.child(key).setValue(message);
                        ChatHelper.sendChat(user.getTownship(),user.getId(),message,user.getName(),isAdmin);
                        hideProgressDialog();
                    }
                });

            }
        });


    }
    public void setCompressedImage() {
        bitmap=BitmapFactory.decodeFile(compressedImage.getAbsolutePath());
    }

    public void showError(String errorMessage) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
    }
    public void uploadImage() {
        if (actualImage == null) {
            showError("Please choose an image!");
        } else {
            // Compress image in main thread using custom Compressor
            showProgressDialog();
            new Compressor(context)
                    .setMaxWidth(640)
                    .setMaxHeight(480)
                    .setQuality(75)
                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .compressToFileAsFlowable(actualImage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<File>() {
                        @Override
                        public void accept(File file) {
                            compressedImage = file;
                            setCompressedImage();
                            upload();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) {
                            throwable.printStackTrace();
                            showError(throwable.getMessage());
                        }
                    });

        }
    }
}
