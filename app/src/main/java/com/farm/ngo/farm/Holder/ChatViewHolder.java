package com.farm.ngo.farm.Holder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.farm.ngo.farm.Adapter.ChatBinder;
import com.farm.ngo.farm.Adapter.ChatItemClickListener;
import com.farm.ngo.farm.Adapter.SwipeController;
import com.farm.ngo.farm.Model.Chat;
import com.farm.ngo.farm.R;
import com.farm.ngo.farm.Service.ChatHelper;
import com.farm.ngo.farm.Service.MessageHelper;
import com.farm.ngo.farm.Utility.DeleteChatDialog;
import com.google.firebase.database.FirebaseDatabase;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

public class ChatViewHolder extends RecyclerView.ViewHolder implements ChatBinder {
    //declare views
    public TextView name,lastmsg,date,you;
    public ImageView profile;
    public CardView chatItem;
    private Button delete_button;
    //*************
    
    private Context context;
    
    //bind
    private PrettyTime prettyTime=new PrettyTime();
    private int lastPosition=-1;
    

    public ChatViewHolder(View itemView) {
        super(itemView);

        //init views
        name=(TextView)itemView.findViewById(R.id.txt_chat_name);
        lastmsg=(TextView)itemView.findViewById(R.id.txt_chat_last_msg);
        date=(TextView)itemView.findViewById(R.id.txt_chat_date);
        profile=(ImageView)itemView.findViewById(R.id.img_profile);
        chatItem=(CardView) itemView.findViewById(R.id.chat_item);
        you=(TextView)itemView.findViewById(R.id.txt_lbl_you);
        context=itemView.getContext();

        //********

    }

    @Override
    public void onBind(final Chat c, int position,SwipeController controller) {
        //defaults
        this.lastmsg.setTypeface(Typeface.DEFAULT);
        this.you.setVisibility(View.VISIBLE);
        this.chatItem.setBackgroundColor(Color.WHITE);
        //>>>>>>>>

        setAnimation(this.lastmsg,position);
        this.name.setText(c.getUsername());
        if(c.isPhoto()){
            this.lastmsg.setText("Image");
            this.lastmsg.setTextColor(Color.GRAY);
        }else {
            this.lastmsg.setText(c.getLastmessage());
        }
        Log.i("chat",c.toString());
        Date date=new Date((long)(c.getDate()));
        this.date.setText(prettyTime.format(date));
        Glide.with(context).
                load(c.getProfileurl())
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(50))
                        .placeholder(R.drawable.ic_farmer)
                .error(R.drawable.ic_farmer))
                .into(this.profile);

        if(!c.isAdmin()){
            this.you.setVisibility(View.GONE);
            if(!c.isSeen()){
                this.chatItem.setBackgroundResource(R.color.chat_unseen);
                this.lastmsg.setTypeface(Typeface.DEFAULT_BOLD);
            }

        }
        this.chatItem.setOnClickListener(new ChatItemClickListener(c,context));

        //set up delete



    }

    //recycler view animator
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }



}
