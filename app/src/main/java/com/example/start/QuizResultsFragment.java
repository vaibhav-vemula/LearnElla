package com.example.start;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizResultsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuizResultsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuizResultsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuizResultsFragment newInstance(String param1, String param2) {
        QuizResultsFragment fragment = new QuizResultsFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_quiz_results, container, false);
        TextView qa= (TextView)view.findViewById(R.id.qa);
        TextView correc= (TextView)view.findViewById(R.id.correc);
        TextView wron= (TextView)view.findViewById(R.id.wron);

        LottieAnimationView c = (LottieAnimationView)view.findViewById(R.id.cccc);
        LottieAnimationView w = (LottieAnimationView)view.findViewById(R.id.wwww);

        c.setMinFrame(45);
        w.setMinFrame(45);


//        assert this.getArguments() != null;
        String marks = Objects.requireNonNull(this.getArguments()).getString("kkk");
        String wrong = Objects.requireNonNull(this.getArguments()).getString("wrong");
        String ques = Objects.requireNonNull(this.getArguments()).getString("questions");

//        Log.d("bla","jdnjnjs"+s);
        qa.setText(ques);
        correc.setText(marks);
        wron.setText(wrong);
        return view;
    }
}