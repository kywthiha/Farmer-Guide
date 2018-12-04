package com.farm.ngo.farm.Adapter;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.farm.ngo.farm.Model.Post;
import com.farm.ngo.farm.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.BreakIterator;
import java.util.LinkedList;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LinkedList<Post> ary;
    private  LayoutInflater mInflater;
    private boolean deletable;

    public PostAdapter(Context context, LinkedList<Post>ary, boolean deletable) {
        setHasStableIds(false);
        mInflater = LayoutInflater.from(context);
        this.ary = ary;
        this.deletable = deletable;
    }



    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View TextItemView = mInflater.inflate(R.layout.activity_post, viewGroup, false);
        View ImageItemView = mInflater.inflate(R.layout.includeimage, viewGroup, false);
        if(i==1){
            return new myviewholder(TextItemView);
        }
        return new imgviewholder(ImageItemView);

    }


        @Override
       public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
           Post m=ary.get(i);
           switch (viewHolder.getItemViewType()){
               case 1:
                   final myviewholder sendViewHolder=(myviewholder) viewHolder;
                   sendViewHolder.bindToMessage(m);
                   break;
               case 2:
                   final imgviewholder receiveViewHolder=(imgviewholder) viewHolder;
                   receiveViewHolder.bindToMessage(m,i,ary);
                   break;
           }

       }


    @Override
    public int getItemViewType(int position) {
        Post m=ary.get(position);
        if(m.getType()==1){
            return 1;
        }
        return 2;
    }




    public int getItemCount() {
        return ary.size();
    }


    //My View Holder without Image
    public class myviewholder extends RecyclerView.ViewHolder {
        TextView title,info,date;
        ImageView delete;
        int lineCnt;
        LinearLayout card;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            info = (TextView) itemView.findViewById(R.id.info);
            date = (TextView) itemView.findViewById(R.id.date);
            card=(LinearLayout) itemView.findViewById(R.id.card);

        }
        public void bindToMessage(final Post user){

            title.setText(user.getTitle());
            info.setText(user.getInfo());
            date.setText(user.getDate());
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Toast.makeText(v.getContext(),user.getTitle(),Toast.LENGTH_LONG).show();
                    Intent i=new Intent(view.getContext(),com.farm.ngo.farm.activity.postshowdetail.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("post", (Serializable) user);
                    view.getContext().startActivity(i);
                }
            });



        }

    }

    //ForView Holder with Image
    public class imgviewholder extends RecyclerView.ViewHolder {
        TextView title,info,date;
        ImageView img,delete;
        CardView card;
        public imgviewholder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            info = (TextView) itemView.findViewById(R.id.info);
            date = (TextView) itemView.findViewById(R.id.date);
            img=(ImageView)itemView.findViewById(R.id.postimg);
            delete=(ImageView)itemView.findViewById(R.id.delete);
            card=(CardView)itemView.findViewById(R.id.card);

            //  my_message=(TextView)itemView.findViewById(R.id.my_message_body);
        }
        public void bindToMessage(final Post user,final int i,final LinkedList<Post> ary){
            //my_message.setText(msg.messagetext);
            title.setText(user.getTitle());
            info.setText(user.getInfo());


            date.setText(user.getDate());
            RequestOptions placeholderRequest = new RequestOptions();
            placeholderRequest.placeholder(R.drawable.loading);
            Glide.with(itemView).setDefaultRequestOptions(placeholderRequest).load(user.getUrl()).into(img);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference().child("user");
                    mDatabase.child(user.getId()).setValue(null);
                    Toast.makeText(v.getContext(),"Deleted",Toast.LENGTH_SHORT).show(); }
            });
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(v.getContext(),user.getTitle(),Toast.LENGTH_LONG).show();
                    Intent i=new Intent(v.getContext(),com.farm.ngo.farm.activity.postshowdetail.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("post", (Serializable) user);
                    v.getContext().startActivity(i);

                }
            });

        }
    }

}
