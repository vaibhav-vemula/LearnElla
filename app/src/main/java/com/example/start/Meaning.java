package com.example.start;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Meaning#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Meaning extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Meaning() {
        // Required empty public constructor
    }
    public String definition;
    public String synonyms;
    public String example;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Meaning.
     */
    // TODO: Rename and change types and number of parameters
    public static Meaning newInstance(String param1, String param2) {
        Meaning fragment = new Meaning();
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

        View view = inflater.inflate(R.layout.fragment_meaning, container, false);
        TextView tv = (TextView)view.findViewById(R.id.word);
        TextView def = (TextView)view.findViewById(R.id.def);
        TextView ex = (TextView)view.findViewById(R.id.ex);
        TextView synon = (TextView)view.findViewById(R.id.synon);
//        assert this.getArguments() != null;
        assert this.getArguments() != null;
        String s = this.getArguments().getString("searchh");
        DBHelper dbHelper = new DBHelper(getActivity());
       Cursor c = dbHelper.getMeaning(s);
        if (c.moveToFirst()) {

            definition = c.getString(c.getColumnIndex("en_definition"));
            example = c.getString(c.getColumnIndex("example"));
            synonyms = c.getString(c.getColumnIndex("synonyms"));
            dbHelper.insertHistory(s);
        } else {
            s = "Not Available";
            definition = "NA";
            example = "NA";
            synonyms =  "NA";
        }

        tv.setText(s);
        def.setText(definition);
        ex.setText(example);
        synon.setText(synonyms);


        return view;
    }
}