package com.farm.ngo.farm.viewholder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.farm.ngo.farm.model.Message;
import com.farm.ngo.farm.R;
import com.farm.ngo.farm.service.SlideshowDialogFragment;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import org.ocpsoft.prettytime.PrettyTime;
import java.util.Date;


public class SendImageViewHolder extends RecyclerView.ViewHolder {
    Context context;
    StorageReference fb=FirebaseStorage.getInstance().getReference();
    String mFileUri;
    RelativeLayout root;
    public ImageView imageView;
    ProgressBar image_load;
    TextView textTime;

    public SendImageViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context=context;
        imageView=(ImageView)itemView.findViewById(R.id.image_view);
        image_load=(ProgressBar)itemView.findViewById(R.id.image_load);
        textTime=(TextView)itemView.findViewById(R.id.time_show);

    }

    public void bindToImage(Message msg){
                mFileUri=msg.getImage_url();
        PrettyTime p=new PrettyTime();
                textTime.setText(p.format(new Date((long)msg.getTime())));
                Glide.with(context)
                        .load(mFileUri)
                        .apply(new RequestOptions().transform(new RoundedCorners(10)).centerCrop().override(400, 300).placeholder(R.drawable.image_placeholder))
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                image_load.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                image_load.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPreview();
            }
        });



    }



    public void showPreview() {
        FragmentManager ft = ((AppCompatActivity)context).getSupportFragmentManager();
        SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance(mFileUri);
        newFragment.show(ft, "slideshow");
    }
}
