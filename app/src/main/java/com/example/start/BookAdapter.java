package com.example.start;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{

    private final ArrayList<Books> bookInfoArrayList;
    private final Context mcontext;

    public BookAdapter(ArrayList<Books> bookInfoArrayList, Context mcontext) {
        this.bookInfoArrayList = bookInfoArrayList;
        this.mcontext = mcontext;
    }
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_rv_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {

        Books bookInfo = bookInfoArrayList.get(position);
        holder.nameTV.setText(bookInfo.getTitle());
        holder.publisherTV.setText(bookInfo.getPublisher());
        holder.pageCountTV.setText("No of Pages : " + bookInfo.getPageCount());
        holder.dateTV.setText(bookInfo.getPublishedDate());

        Picasso.get().load(bookInfo.getThumbnail()).into(holder.bookIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle i = new Bundle();
                BookDetails frag = new BookDetails();

                i.putString("title", bookInfo.getTitle());
                i.putString("subtitle", bookInfo.getSubtitle());
                i.putStringArrayList("authors", bookInfo.getAuthors());
                i.putString("publisher", bookInfo.getPublisher());
                i.putString("publishedDate", bookInfo.getPublishedDate());
                i.putString("description", bookInfo.getDescription());
                i.putInt("pageCount", bookInfo.getPageCount());
                i.putString("thumbnail", bookInfo.getThumbnail());
                i.putString("previewLink", bookInfo.getPreviewLink());
                i.putString("infoLink", bookInfo.getInfoLink());
                i.putString("buyLink", bookInfo.getBuyLink());
                frag.setArguments(i);

                FragmentTransaction ft = ((AppCompatActivity)mcontext).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.FrameContainer, frag);
                ft.commit();
                ft.addToBackStack(null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookInfoArrayList.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        TextView nameTV, publisherTV, pageCountTV, dateTV;
        ImageView bookIV;

        public BookViewHolder(View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.idTVBookTitle);
            publisherTV = itemView.findViewById(R.id.idTVpublisher);
            pageCountTV = itemView.findViewById(R.id.idTVPageCount);
            dateTV = itemView.findViewById(R.id.idTVDate);
            bookIV = itemView.findViewById(R.id.idIVbook);
        }
    }

}
