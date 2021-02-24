package com.example.keepsclone.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.keepsclone.R;
import com.example.keepsclone.keeps_data;

import java.util.ArrayList;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private final ArrayList<keeps_data> keepsData;

    public NotesAdapter(ArrayList<keeps_data> keepsData) {
        this.keepsData = keepsData;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_note_container,
                        parent,
                        false
                )
        );
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {

        com.example.keepsclone.keeps_data keepsdata = keepsData.get(position);

        holder.TextTitle.setText(keepsdata.getTitle().substring(0, 1).toUpperCase() + keepsdata.getTitle().substring(1).toLowerCase());
        holder.TextDateTime.setText((CharSequence) keepsdata.getDate_time());
        holder.TextBody.setText(keepsdata.getBody());

    }

    @Override
    public int getItemCount() {
        return keepsData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    // view Holder for notes
    public class NotesViewHolder extends RecyclerView.ViewHolder {
        private TextView TextTitle, TextBody, TextDateTime;

        public NotesViewHolder(View itemView) {
            super(itemView);
            TextTitle = (TextView) itemView.findViewById(R.id.textTitle);
            TextBody = itemView.findViewById(R.id.textBody);
            TextDateTime = itemView.findViewById(R.id.textDateTime);

        }

    }
}
