package com.example.start;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DictionaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DictionaryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public DictionaryFragment() {
        // Required empty public constructor
    }
    ArrayList<History> historyArrayList;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    DBHelper dbHelper;
    RelativeLayout emptyHistory;
    Cursor cursorHistory;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DictionaryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DictionaryFragment newInstance(String param1, String param2) {
        DictionaryFragment fragment = new DictionaryFragment();
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
        View view= inflater.inflate(R.layout.fragment_dictionary, container, false);

        Button searchbutton = view.findViewById(R.id.searchbutton);
        Button clearhistory = view.findViewById(R.id.clearhistory);
        TextView tw = view.findViewById(R.id.search);

        emptyHistory = (RelativeLayout) view.findViewById(R.id.empty_history);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_history);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        try {
            historyArrayList = new ArrayList<>();
            adapter = new HistoryAdapter(getActivity(), historyArrayList);
            recyclerView.setAdapter(adapter);

                History history;
                dbHelper = new DBHelper(getActivity());
                cursorHistory = dbHelper.getHistory();

                if (cursorHistory.moveToFirst()) {
                    do {
                        String definition = cursorHistory.getString(cursorHistory.getColumnIndex("en_definition"));
                        definition =  definition.substring(0,1).toUpperCase() + definition.substring(1).toLowerCase();
                        history = new History(cursorHistory.getString(cursorHistory.getColumnIndex("word")),definition );
                        historyArrayList.add(history);

                    }
                    while (cursorHistory.moveToNext());
                }
                adapter.notifyDataSetChanged();


            if (adapter.getItemCount() == 0) {
                emptyHistory.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                emptyHistory.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }


        } catch (Exception e) {
            e.printStackTrace();

        }
        searchbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        assert getFragmentManager() != null;
                        String data = tw.getText().toString();
                        Meaning frag = new Meaning();
                        Bundle bundle = new Bundle();

                        bundle.putString("searchh",data);
                        frag.setArguments(bundle);
                        tw.setText(null);
                        final FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.FrameContainer, frag);
                        ft.commit();
                        ft.addToBackStack(null);
                    }
                });

        clearhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = new DBHelper(getActivity());
                dbHelper.deleteHistory();

                assert getFragmentManager() != null;
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.FrameContainer, new DictionaryFragment());
                ft.commit();

            }
        });
        return view;
    }

}