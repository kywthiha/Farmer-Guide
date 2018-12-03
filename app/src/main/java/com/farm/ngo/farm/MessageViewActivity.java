package com.farm.ngo.farm;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.farm.ngo.farm.Fragment.QuestionAnswerFragment;

public class MessageViewActivity extends AppCompatActivity {

    private FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_view);

        QuestionAnswerFragment fragment = new QuestionAnswerFragment(getApplicationContext());
        transaction.replace(R.id.frameLayout, fragment);
        transaction.disallowAddToBackStack();
        transaction.commit();
    }
}
