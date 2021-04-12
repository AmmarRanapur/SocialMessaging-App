package com.example.socialapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.socialapp.daos.PostDao;

public class AddPostActivity extends AppCompatActivity {

    EditText postText;
    Button postbutton;
    PostDao postDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        postText = findViewById(R.id.postInput);
        postbutton = findViewById(R.id.post_button);
        postDao = new PostDao();

        postbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = postText.getText().toString();
                if(input!=null){
                    String mInput=input.trim();
                    Log.d("firebasse_error","before add post");
                   postDao.addPost(mInput);
                    Log.d("firebasse_error","after add post");
                   finish();
                }
            }
        });
    }
}