package com.farm.ngo.farm.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farm.ngo.farm.Holder.ChatViewHolder;
import com.farm.ngo.farm.Model.Chat;
import com.farm.ngo.farm.R;
import com.farm.ngo.farm.Service.ChatHelper;
import com.farm.ngo.farm.Service.MessageHelper;
import com.farm.ngo.farm.Utility.DeleteChatDialog;

import java.util.List;


public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {
    private List<Chat> chats;
    private Context context;
    private SwipeController controller;
    SharedPreferences preferences;

    public ChatAdapter(List<Chat> chats, Context context,SwipeController controller) {
        this.chats = chats;
        this.context = context;
        this.controller=controller;
        this.setHasStableIds(true);


    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_items, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Log.i("Position", position + "*****");
        final Chat model=chats.get(position);
        holder.onBind(model,position,controller);

        this.controller.setSwipeAction(new SwipeController.SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                super.onRightClicked(position);
                final Chat c=chats.get(position);

                DeleteChatDialog deleteChatDialog=new DeleteChatDialog(context, c, new DeleteChatDialog.OnDialogResult() {
                    @Override
                    public void onYesClicked() {
                        preferences=context.getSharedPreferences("adpref",0);
                        ChatHelper.getChatRef(preferences.getString("city","")).child(c.getId()).setValue(null);
                        MessageHelper.messageRef.child(c.getId()).setValue(null);
                    }
                });
                deleteChatDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public void removeItem(int index){
        chats.remove(index);
        notifyDataSetChanged();
    }

    public void addToTop(Chat c){
        chats.add(0,c);
        notifyDataSetChanged();
        notifyItemInserted(0);


    }
    public void addToBottom(Chat c){
        chats.add(chats.size(),c);
        notifyDataSetChanged();
    }
    public void changeIndexData(Chat c,int i){
        chats.get(i).setAdmin(c.isAdmin());
        chats.get(i).setDate(c.getDate());
        chats.get(i).setProfileurl(c.getProfileurl());
        chats.get(i).setUsername(c.getUsername());
        chats.get(i).setId(c.getId());
        chats.get(i).setSeen(c.isSeen());
        chats.get(i).setPhoto(c.isPhoto());
        notifyDataSetChanged();
    }

    //delete btn listeneer







}
