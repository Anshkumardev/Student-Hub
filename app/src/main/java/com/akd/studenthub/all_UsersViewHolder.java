package com.akd.studenthub;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class all_UsersViewHolder extends RecyclerView.ViewHolder {

    TextView username;
    TextView experties;
    CircleImageView dpCirleImageView;
    ImageView img_on;
    ImageView img_off;



    public all_UsersViewHolder(@NonNull View itemView) {
        super(itemView);
        username = itemView.findViewById(R.id.username_allUsers_TextView);
        experties = itemView.findViewById(R.id.experties_AllUsers_TextVIew);
        dpCirleImageView = itemView.findViewById(R.id.dp_allUsers_circleImageView);
        img_on = itemView.findViewById(R.id.img_on);
        img_off = itemView.findViewById(R.id.img_off);
    }


}
