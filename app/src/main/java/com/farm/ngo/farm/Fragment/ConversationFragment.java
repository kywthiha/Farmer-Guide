package com.farm.ngo.farm.Fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.farm.ngo.farm.Adapter.ChatAdapter;
import com.farm.ngo.farm.Adapter.SwipeController;
import com.farm.ngo.farm.FarmStatic.RefStatic;
import com.farm.ngo.farm.Model.Chat;
import com.farm.ngo.farm.R;
import com.farm.ngo.farm.Service.ChatHelper;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConversationFragment extends Fragment {

    public ConversationFragment() {

    }

    private Context mContext;

    RecyclerView chatRecycler;
    LinearLayoutManager manager;
    ChatAdapter adapter;

    SharedPreferences  adPref;
    SharedPreferences.Editor adEdit;


    //arrarys
    List<Chat> chats = chats = new ArrayList<>();       //primary chats store

    //db ref
    DatabaseReference ref = null;
    //message
    MediaPlayer mp;

    //Loading circle
    ImageView img_loading;

    LinearLayout loadingLayout;

    //recycler view swipe controller;
    SwipeController swipeController;


    public ConversationFragment(Context context) {
        // Required empty public constructor
        mContext = context;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conversation, container, false);

        //set up pref
        adPref=getActivity().getSharedPreferences("adpref", 0);
        adEdit=adPref.edit();

        ref=RefStatic.chatRef.child(adPref.getString("city",""));



        mp = MediaPlayer.create(getActivity(), R.raw.stairs);

        //get views
        chatRecycler = (RecyclerView) view.findViewById(R.id.chat_recycler);
        manager = new LinearLayoutManager(getActivity());
        final SwipeController swipeController = new SwipeController(getActivity());
        adapter = new ChatAdapter(chats, getActivity(),swipeController);
        chatRecycler.setAdapter(adapter);
        chatRecycler.setLayoutManager(manager);

        //swipe to delete


        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(chatRecycler);
        chatRecycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
        //progress


        loadingLayout = (LinearLayout) view.findViewById(R.id.linear_loading);
        img_loading = (ImageView) view.findViewById(R.id.img_loading);
        Glide.with(getActivity()).load(R.drawable.chat_loading_before).into(img_loading);

        //reference listeners
        ref.orderByChild("date").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Chat chat = dataSnapshot.getValue(Chat.class);
                adapter.addToTop(chat);
                Log.i("data", "date added");
                Log.i("Chat Data", chat.toString());
                loadingLayout.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Chat chat = dataSnapshot.getValue(Chat.class);

                Log.i("change", "data changed");
                for (int i = 0; i < chats.size(); i++) {
                    if (chats.get(i).getId().equals(chat.getId())) {
                        if (!chats.get(i).getLastmessage().equals(chat.getLastmessage())) {
                            Log.i("change", "messagechanged");
                            adapter.removeItem(i);
                            adapter.addToTop(chat);
                            mp.start();
                        } else {
                            adapter.changeIndexData(chat, i);
                            Log.i("change", "other data changed");
                        }
                    }

                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Chat chat = dataSnapshot.getValue(Chat.class);
                for (int i = 0; i < chats.size(); i++) {
                    if (chats.get(i).getId().equals(chat.getId())) {
                        adapter.removeItem(i);
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Chat chat = dataSnapshot.getValue(Chat.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("chat err", "database error");

            }
        });


        return view;
    }

    private void setupRecyclerView() {
        // ...
        chatRecycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }
}
