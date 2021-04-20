package com.example.start;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookDetails extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static BookDetails newInstance(String param1, String param2) {
        BookDetails fragment = new BookDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    String title, subtitle, publisher, publishedDate, description, thumbnail, previewLink, infoLink, buyLink;
    int pageCount;
    private ArrayList<String> authors;

    TextView titleTV, subtitleTV, publisherTV, descTV, pageTV, publishDateTV;
    Button previewBtn, buyBtn;
    private ImageView bookIV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_details, container, false);

        titleTV = view.findViewById(R.id.idTVTitle);
        subtitleTV = view.findViewById(R.id.idTVSubTitle);
        publisherTV = view.findViewById(R.id.idTVpublisher);
        descTV = view.findViewById(R.id.idTVDescription);
        pageTV = view.findViewById(R.id.idTVNoOfPages);
        publishDateTV = view.findViewById(R.id.idTVPublishDate);
        previewBtn = view.findViewById(R.id.idBtnPreview);
        buyBtn = view.findViewById(R.id.idBtnBuy);
        bookIV = view.findViewById(R.id.idIVbook);

        assert getArguments() != null;
        title = getArguments().getString("title");
        subtitle = getArguments().getString("subtitle");
        publisher = getArguments().getString("publisher");
        publishedDate = getArguments().getString("publishedDate");
        description = getArguments().getString("description");
        pageCount = getArguments().getInt("pageCount", 0);
        thumbnail = getArguments().getString("thumbnail");
        previewLink = getArguments().getString("previewLink");
        infoLink = getArguments().getString("infoLink");
        buyLink = getArguments().getString("buyLink");

        titleTV.setText(title);
        subtitleTV.setText(subtitle);
        publisherTV.setText(publisher);
        publishDateTV.setText("Published On : " + publishedDate);
        descTV.setText(description);
        pageTV.setText("No Of Pages : " + pageCount);
        Picasso.get().load(thumbnail).into(bookIV);

        previewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (previewLink.isEmpty()) {
                    // below toast message is displayed when preview link is not present.
                    Toast.makeText(getContext(), "No preview Link present", Toast.LENGTH_SHORT).show();
                    return;
                }

                Uri uri = Uri.parse(previewLink);
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);

            }
        });

        // initializing on click listener for buy button.
        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buyLink.isEmpty()) {
                    // below toast message is displaying when buy link is empty.
                    Toast.makeText(getContext(), "No buy page present for this book", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getContext(), "wait", Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse(buyLink);
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });

        return view;
    }
}