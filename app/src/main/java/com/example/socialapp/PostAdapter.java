package com.example.socialapp;

import android.net.IpPrefix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialapp.models.Post;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

public class PostAdapter extends FirestoreRecyclerAdapter<Post, PostAdapter.PostHolder> {

    LikeClickHandler likeClickHandler;

    public PostAdapter(@NonNull FirestoreRecyclerOptions<Post> options, LikeClickHandler listener) {
        super(options);
        likeClickHandler=listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull PostHolder holder, int position, @NonNull Post model) {
         holder.postText.setText(model.text);
         holder.likeCount.setText(String.valueOf(model.likedBy.size()));
         holder.userName.setText(model.createdBy.Name);
         Glide.with(holder.userImage.getContext()).load(model.createdBy.Photo).circleCrop().into(holder.userImage);
         holder.createdAt.setText(Utils.getTimeAgo(model.createdAt));

         FirebaseAuth auth = FirebaseAuth.getInstance();
         assert auth.getCurrentUser()!=null;
         String currentUser = auth.getCurrentUser().getUid();
         boolean isLiked=model.likedBy.contains(currentUser);
         if(isLiked){
             holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.getContext(),R.drawable.ic_liked));
         }else{
             holder.likeButton.setImageDrawable(ContextCompat.getDrawable(holder.likeButton.getContext(),R.drawable.ic_unliked));
         }
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View v=  LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
         return new PostHolder(v);
    }

    class PostHolder extends RecyclerView.ViewHolder {
        ImageView userImage;
        TextView userName;
        TextView createdAt;
        TextView postText;
        TextView likeCount;
        ImageView likeButton;
        public PostHolder(View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.user_image);
            userName = itemView.findViewById(R.id.user_name);
            createdAt = itemView.findViewById(R.id.post_time);
            postText = itemView.findViewById(R.id.postTitle);
            likeCount = itemView.findViewById(R.id.likeCount);
            likeButton = itemView.findViewById(R.id.likeButton);

            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    likeClickHandler.onLikeClicked(getSnapshots().getSnapshot(getAdapterPosition()).getId());
                }
            });
        }
    }
}
interface LikeClickHandler {
    void onLikeClicked(String postId);
}
