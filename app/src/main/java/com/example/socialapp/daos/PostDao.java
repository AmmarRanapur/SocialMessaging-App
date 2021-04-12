package com.example.socialapp.daos;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.socialapp.models.Post;
import com.example.socialapp.models.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class PostDao {
    Task<DocumentSnapshot> mTask;
    public FirebaseFirestore db= FirebaseFirestore.getInstance();
    public CollectionReference collection=db.collection("posts");
    public FirebaseAuth auth = FirebaseAuth.getInstance();

    public void addPost(String text){
        String currentUserId = auth.getCurrentUser().getUid();
        UserDao userDao = new UserDao();
        userDao.getUserById(currentUserId).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                User user = task.getResult().toObject(User.class);
                if(user==null) Log.d("firebase_user","user is null");
                Long currTime = System.currentTimeMillis();
                Post post = new Post(text,user,currTime,new ArrayList<>());
                Log.d("firebase_error",post.text+" "+user.Name);
                new AddPostAsyncTask().execute(post);
            }
        });
    }
    class AddPostAsyncTask extends AsyncTask<Post,Void,Void>{

        @Override
        protected Void doInBackground(Post... posts) {
            collection.document().set(posts[0]);
            return null;
        }
    }
    public Task<DocumentSnapshot> getPostById(String postId){
        return collection.document(postId).get();
    }
    public void updateLikes(String postId){
        assert auth.getCurrentUser()!=null;
        String currentUser=auth.getCurrentUser().getUid();
        getPostById(postId).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               Post post=task.getResult().toObject(Post.class);
               assert post != null;
               boolean isLiked=post.likedBy.contains(currentUser);
               if(isLiked){
                   post.likedBy.remove(currentUser);
               }else{
                   post.likedBy.add(currentUser);
               }
               collection.document(postId).set(post);
           }
       });
    }
}
