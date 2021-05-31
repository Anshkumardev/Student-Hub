package com.akd.studenthub;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentViewHolder extends RecyclerView.ViewHolder {

    ImageView profileImage;
    TextView username,comment;

    public CommentViewHolder(@NonNull View itemView) {
        super(itemView);

        profileImage = itemView.findViewById(R.id.profileDpComment);
        username = itemView.findViewById(R.id.usernameTextViewComment);
        comment = itemView.findViewById(R.id.descriptionTextView);
    }
}
