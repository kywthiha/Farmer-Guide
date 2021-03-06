package com.farm.ngo.farm.fragment;


import android.content.Context;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.farm.ngo.farm.adapter.PostAdapter;
import com.farm.ngo.farm.farmstatic.RefStatic;
import com.farm.ngo.farm.model.Post;
import com.farm.ngo.farm.R;
import com.farm.ngo.farm.utility.Mdetect;
import com.farm.ngo.farm.utility.NavigationCustomDone;
import com.farm.ngo.farm.utility.Rabbit;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.LinkedList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment  {

    private Context mContext;
    private FloatingActionButton postNews;
    private PostAdapter postAdapter;
    private RecyclerView postRecyclerview;
    private ImageView ladingData;
    private LinearLayoutManager layoutManager;
    private LinkedList<Post> postArray = new LinkedList<>();
    public LinkedList<String> id=new LinkedList<>();
    private DatabaseReference mDatabase = RefStatic.postRef;
    private boolean visible;
    public NavigationCustomDone mCallback;
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (NavigationCustomDone) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    public NewsFragment(Context context, boolean visible) {
        // Required empty public constructor
        this.mContext = context;
        this.visible = false;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        postAdapter = new PostAdapter(mContext, postArray, visible);
        Toolbar toolbar = (Toolbar)rootView. findViewById(R.id.news_tool_bar);
        AppCompatActivity mActivity=((AppCompatActivity)getActivity());
       mActivity.setSupportActionBar(toolbar);
       mActivity. getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActivity.setTitle("News");
        postRecyclerview = (RecyclerView) rootView.findViewById(R.id.postRecyclerview);
        layoutManager = new LinearLayoutManager(mContext);
        ladingData=(ImageView)rootView.findViewById(R.id.loading_img);
        postRecyclerview.setLayoutManager(layoutManager);
        postRecyclerview.setAdapter(postAdapter);
        Glide.with(getActivity()).load(R.drawable.verify_code_loading).into(ladingData);
        postNews = (FloatingActionButton) rootView.findViewById(R.id.cp);
        if (visible){
            postNews.setVisibility(View.VISIBLE);
        }

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Post post = dataSnapshot.getValue(Post.class);
                if(!Mdetect.isUnicode()) {
                    post.setTitle(Rabbit.uni2zg(post.getTitle()));
                    post.setInfo(Rabbit.uni2zg(post.getInfo()));
                }
                post.setId(dataSnapshot.getKey());
                postArray.add(0, post);
                id.add(0, dataSnapshot.getKey());
                postAdapter.notifyItemInserted(0);
                ladingData.setVisibility(View.GONE);
                postRecyclerview.setVisibility(View.VISIBLE);
                postRecyclerview.scrollToPosition(0);

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
