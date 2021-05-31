package com.akd.studenthub;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;



public class ImageSliderAdapter extends SliderViewAdapter<ImageSliderAdapter.MyViewHolder> {

    List<Integer> imageList;

    private Context context;

    public ImageSliderAdapter(Context context) {
        this.context = context;
    }

    ImageSliderAdapter(List<Integer> list)
    {
        this.imageList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, final int position) {

        viewHolder.imageView.setImageResource(imageList.get(position));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 1){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://akdblogs.online/10-tips-to-boost-your-confidence/"));
                    v.getContext().startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    class MyViewHolder extends SliderViewAdapter.ViewHolder
    {

        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewSlider);

        }
    }

}
