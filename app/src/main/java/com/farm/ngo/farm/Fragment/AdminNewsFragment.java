package com.farm.ngo.farm.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farm.ngo.farm.Adapter.AdminPostAdapter;
import com.farm.ngo.farm.Adapter.PostAdapter;
import com.farm.ngo.farm.FarmStatic.RefStatic;
import com.farm.ngo.farm.Model.Post;
import com.farm.ngo.farm.R;
import com.farm.ngo.farm.writepost;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.LinkedList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminNewsFragment extends Fragment {

    private Context mContext;
    private FloatingActionButton postNews;
    private AdminPostAdapter postAdapter;
    private RecyclerView postRecyclerview;
    private LinearLayoutManager layoutManager;
    private LinkedList<Post> postArray = new LinkedList<>();
    public LinkedList<String> id=new LinkedList<>();
    private DatabaseReference mDatabase = RefStatic.postRef;
    private boolean visible;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.mContext=getActivity();
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        postAdapter = new AdminPostAdapter(mContext, postArray, visible);
        postRecyclerview = (RecyclerView) rootView.findViewById(R.id.postRecyclerview);
        layoutManager = new LinearLayoutManager(mContext);
        postRecyclerview.setLayoutManager(layoutManager);
        postRecyclerview.setAdapter(postAdapter);
        postNews = (FloatingActionButton) rootView.findViewById(R.id.cp);
        postNews.setVisibility(View.VISIBLE);

        postNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),writepost.class);
                startActivity(i);
            }
        });
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Post user = dataSnapshot.getValue(Post.class);
                user.setId(dataSnapshot.getKey());
                postArray.add(0, user);
                id.add(0, dataSnapshot.getKey());
                postAdapter.notifyItemInserted( 0);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                String Key = dataSnapshot.getKey();

                // [START_EXCLUDE]
                int postIndex = id.indexOf(Key);
                if (postIndex > -1) {
                    // Remove data from the list
                    id.remove(postIndex);
                    postArray.remove(postIndex);
                    postAdapter.notifyItemRemoved(postIndex);
                }

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                postAdapter.notifyDataSetChanged();
            }
        };
        mDatabase.addChildEventListener(childEventListener);


        return rootView;
    }

}
