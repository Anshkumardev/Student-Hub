package com.akd.studenthub;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    private FirebaseAuth mAuth;
    TextView textMessage;
    String name;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;

    ImageView calendar,notes,questionPapers,books,syllabus;

    CircleImageView circleImageView;
    TextView greetingTextView;
    String userID;
    CardView eventsCardView,societyCardView,newsCard,extras,achievementsCard,mentorCard,blogCard,notice,photoCard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home,container,false);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();



        //-------------------------------Setting Click Listners On Academic Section------------------------------//

        //PhotosCard = view.findViewById(R.id.photosMainCard);
        syllabus = view.findViewById(R.id.syllabus);
        calendar = view.findViewById(R.id.calendar);
        notes = view.findViewById(R.id.notes);
        questionPapers = view.findViewById(R.id.questionPaper);
        achievementsCard = view.findViewById(R.id.achievementsCard);
        mentorCard = view.findViewById(R.id.mentorCard);
        blogCard = view.findViewById(R.id.blogsCard);
        extras = view.findViewById(R.id.extrasCard);
        books = view.findViewById(R.id.books);
        notice = view.findViewById(R.id.noticeCard);
        photoCard = view.findViewById(R.id.PhotosCardMain);

        photoCard.setOnClickListener(new View.OnClickListener() {
            //this is news now
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),NewsActivity.class);
                startActivity(intent);
            }
        });

        syllabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });

        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),NoticeActivity.class);
                startActivity(intent);
            }
        });

        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),BooksActivity.class);
                startActivity(intent);
            }
        });

        extras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ExtrasACtivity.class);
                startActivity(intent);
            }
        });

        questionPapers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),QuestionPaperActivity.class);
                startActivity(intent);
            }
        });

        blogCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),BlogsActivity.class);
                startActivity(intent);
            }
        });

        mentorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),MentorActivity.class);
                startActivity(intent);
            }
        });

        achievementsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),AchiementsActivity.class);
                startActivity(intent);
            }
        });

        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),NotesActivity.class);
                startActivity(intent);
            }
        });

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),CalendarActivity.class);
                startActivity(intent);
            }
        });


        eventsCardView = view.findViewById(R.id.eventsCard);
        societyCardView = view.findViewById(R.id.joinClubCard);
        newsCard = view.findViewById(R.id.newsCard);

        societyCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),JoinSocietiesActivity.class);
                startActivity(intent);
            }
        });

        eventsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),EventsActivity.class);
                startActivity(intent);
            }
        });

        newsCard.setOnClickListener(new View.OnClickListener() {
            //this is gallery now
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Photos.class);
                startActivity(intent);
            }
        });


        //----------------------------- Image Slider--------------------------//

        SliderView sliderView = view.findViewById(R.id.imageSlider);

        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.welcomebanner);
        images.add(R.drawable.boostc);
        images.add(R.drawable.contributebanner);



        ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(images);

        sliderView.setSliderAdapter(imageSliderAdapter);
        sliderView.setAutoCycle(true);

        sliderView.setScrollTimeInSec(4);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);

        sliderView.startAutoCycle();


        return view;

    }

}
