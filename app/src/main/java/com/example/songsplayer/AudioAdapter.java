package com.example.songsplayer;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.ViewHolder> {

    private final ArrayList<File> items;
    Context context;

    public AudioAdapter(ArrayList<File> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public AudioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItems = layoutInflater.inflate(R.layout.row_audio_list, parent, false);
        return new ViewHolder(listItems);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String string = items.get(position).toString();
        holder.textViewAudioName.setText(string);
        holder.textViewAudioName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayerActivity.class);
                intent.putExtra("songs", items);
                intent.putExtra("pos", position);
                context.startActivity(intent);
                Toast.makeText(v.getContext(), "clicked", Toast.LENGTH_SHORT).show();

                Log.d("Item", "clicked");
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewAudioName;
        private LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAudioName = itemView.findViewById(R.id.textViewAudioName);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
