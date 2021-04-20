package com.example.start;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private final ArrayList<History> histories;
    private final Context context;
    public HistoryAdapter(Context context, ArrayList<History> histories) {
        this.histories = histories;
        this.context = context;
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView textViewWord;
        TextView textViewDef;
        public HistoryViewHolder(View itemView) {
            super(itemView);
            textViewWord = (TextView) itemView.findViewById(R.id.en_word);
            textViewDef = (TextView) itemView.findViewById(R.id.en_def);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int position = getAdapterPosition();
                        String word = histories.get(position).getEn_word();

                        Meaning frag = new Meaning();
                        Bundle bundle = new Bundle();
                        word =  word.toLowerCase();
                        bundle.putString("searchh",word);
                        frag.setArguments(bundle);

                        FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.FrameContainer, frag);
                        ft.commit();
                        ft.addToBackStack(null);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    @NonNull
    @Override
    public HistoryAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_layout,parent,false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryViewHolder holder, int position) {
        holder.textViewWord.setText(histories.get(position).getEn_word());
        holder.textViewDef.setText(histories.get(position).getEn_def());
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }


}
