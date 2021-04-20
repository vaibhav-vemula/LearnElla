package com.example.start;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link qnaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class qnaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView tv;
    Button submitbutton, quitbutton;
    RadioGroup radio_g;
    RadioButton rb1,rb2,rb3,rb4;


    int flag=0;
    public static int marks=0,correct=0,wrong=0,ques=0;

    public qnaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment qnaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static qnaFragment newInstance(String param1, String param2) {
        qnaFragment fragment = new qnaFragment();
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
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        flag=0;
        marks=0;
        correct=0;
        wrong=0;
        ques = 0;

        view = inflater.inflate(R.layout.fragment_qna, container, false);

        submitbutton=(Button)view.findViewById(R.id.nextb);
        quitbutton=(Button)view.findViewById(R.id.endb);
        tv=(TextView)view.findViewById(R.id.question);

        radio_g=(RadioGroup)view.findViewById(R.id.answersgrp);
        rb1=(RadioButton)view.findViewById(R.id.radioButton1);
        rb2=(RadioButton)view.findViewById(R.id.radioButton2);
        rb3=(RadioButton)view.findViewById(R.id.radioButton3);
        rb4=(RadioButton)view.findViewById(R.id.radioButton4);

        assert getArguments() != null;

        ArrayList<String> questions = getArguments().getStringArrayList("questions");
        ArrayList<String> opt = getArguments().getStringArrayList("option");
        ArrayList<String> answers = getArguments().getStringArrayList("correctans");

        tv.setText(Html.fromHtml(questions.get(flag)));
        rb1.setText(Html.fromHtml(opt.get(0)));
        rb2.setText(Html.fromHtml(opt.get(1)));
        rb3.setText(Html.fromHtml(opt.get(2)));
        rb4.setText(Html.fromHtml(opt.get(3)));

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(radio_g.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(getContext(), "Please select one choice", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton uans = (RadioButton) view.findViewById(radio_g.getCheckedRadioButtonId());
                String ansText = uans.getText().toString();
//                Toast.makeText(getApplicationContext(), ansText, Toast.LENGTH_SHORT).show();
                if(ansText.equals(answers.get(flag))) {
                    correct++;
                    ques++;
                }
                else {
                    wrong++;
                    ques++;
                }
                flag++;
                if(flag<questions.size())
                {
                    tv.setText(Html.fromHtml(questions.get(flag)));
                    rb1.setText(Html.fromHtml(opt.get(flag * 4)));
                    rb2.setText(Html.fromHtml(opt.get(flag * 4 + 1)));
                    rb3.setText(Html.fromHtml(opt.get(flag * 4 + 2)));
                    rb4.setText(Html.fromHtml(opt.get(flag * 4 + 3)));
                }
                else
                {
                    marks=correct;
                    resultscreen();
                }
                radio_g.clearCheck();
            }
        });
        quitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultscreen();
            }
        });

        return view;
    }

    public void resultscreen(){
        assert getFragmentManager() != null;


        QuizResultsFragment fragment2 = new QuizResultsFragment();
        Bundle bundle = new Bundle();
        marks=correct;

        bundle.putString("kkk", String.valueOf(marks));
        bundle.putString("wrong",String.valueOf(wrong));
        bundle.putString("questions",String.valueOf(ques));
        fragment2.setArguments(bundle);

        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.FrameContainer, fragment2);
        ft.commit();

    }
}