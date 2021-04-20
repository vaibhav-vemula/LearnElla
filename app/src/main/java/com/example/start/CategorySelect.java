package com.example.start;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategorySelect#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategorySelect extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategorySelect() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategorySelect.
     */
    // TODO: Rename and change types and number of parameters
    public static CategorySelect newInstance(String param1, String param2) {
        CategorySelect fragment = new CategorySelect();
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

    ImageButton animals,vehicles,cs,videogame,nature,gk,art,gadgets,music,maths;
    ArrayList<String> correct = new ArrayList<>();
    ArrayList<String> opts = new ArrayList<>();
    ArrayList<String> ques = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_select, container, false);

        animals = view.findViewById(R.id.animals);
        vehicles = view.findViewById(R.id.vehicles);
        cs = view.findViewById(R.id.cs);
        videogame = view.findViewById(R.id.videogame);
        nature = view.findViewById(R.id.nature);
        gk = view.findViewById(R.id.gk);
        art = view.findViewById(R.id.art);
        gadgets = view.findViewById(R.id.gadgets);
        music = view.findViewById(R.id.music);
        maths = view.findViewById(R.id.maths);


        animals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getquestions("27");
            }
        });
        vehicles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getquestions("28");
            }
        });
        cs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getquestions("18");
            }
        });
        videogame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getquestions("15");
            }
        });
        nature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getquestions("17");
            }
        });
        gk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getquestions("9");
            }
        });
        art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getquestions("25");
            }
        });
        gadgets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getquestions("30");
            }
        });
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getquestions("12");
            }
        });
        maths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getquestions("19");
            }
        });

        return view;

    }

    public void getquestions(String cat){

        String url = "https://opentdb.com/api.php?amount=20&category="+cat+"&type=multiple";
        Log.d("data",url);
       RequestQueue mRequestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));

        mRequestQueue.getCache().clear();
        RequestQueue queue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));

        JsonObjectRequest qaObject = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response)
            {
                try {
                    JSONArray itemsArray = response.getJSONArray("results");

                    for (int i = 0; i < itemsArray.length(); i++) {
                        JSONObject itemsObj = itemsArray.getJSONObject(i);

                        String question = itemsObj.optString("question");
                        String correct_answer = itemsObj.optString("correct_answer");
                        JSONArray incorrect_answers = itemsObj.getJSONArray("incorrect_answers");

                        Log.d("data", String.valueOf(incorrect_answers));


                        ques.add(question);
                        correct.add(correct_answer);

                        if (incorrect_answers.length() != 0) {
                            for (int j = 0; j < incorrect_answers.length(); j++) {
                                opts.add(incorrect_answers.optString(j));
                            }
                            opts.add(correct_answer);
                        }
                    }

                    qnaFragment frag = new qnaFragment();
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("questions",ques);
                    bundle.putStringArrayList("correctans",correct);
                    bundle.putStringArrayList("option",opts);
                    frag.setArguments(bundle);

        assert getFragmentManager() != null;
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.FrameContainer, frag);
        ft.commit();


                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error " + e, Toast.LENGTH_SHORT).show();
                }

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // also displaying error message in toast.
                Toast.makeText(getContext(), "Error " + error, Toast.LENGTH_SHORT).show();
                Log.e("er", String.valueOf(error));
            }
        });
        queue.add(qaObject);
    }
}