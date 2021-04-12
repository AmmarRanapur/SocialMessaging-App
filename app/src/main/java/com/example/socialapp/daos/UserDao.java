package com.example.socialapp.daos;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.example.socialapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collection;

public class UserDao {
    static FirebaseFirestore db= FirebaseFirestore.getInstance();
    static CollectionReference collection=db.collection("users");

    public void addUser(User user){
        new AddUser().execute(user);
    }

    public static class AddUser extends AsyncTask<User,Void,Void>{
        @Override
        protected Void doInBackground(User... users) {
            collection.document(users[0].Id).set(users[0]);
            return null;
        }
    }
    public Task<DocumentSnapshot> getUserById(String id){
        return collection.document(id).get();
    }

}
