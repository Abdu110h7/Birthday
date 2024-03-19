package com.example.birthday.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Delete;

import com.example.birthday.Birthday_day;
import com.example.birthday.R;
import com.example.birthday.SendActivity;
import com.example.birthday.domain.model.Notes;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    List<Notes> list;

    NoteInteface noteInteface;
    String[] colorArray = {"#FFDACF", "#D1FAF6", "#DCFBDD", "#FDF3E3", "#9CF6EE", "#FACFDE"};
    boolean isLongClick = false;
    List<Notes> deletes = new ArrayList<>();

    public NoteAdapter(List<Notes> list, NoteInteface noteInteface) {
        this.list = list;
        this.noteInteface = noteInteface;

    }

    public void updateList(List<Notes> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note,parent,false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.select_radio_butten.setVisibility(View.GONE);
        holder.note_card.setStrokeWidth(0);
        holder.note_card.setStrokeColor(Color.parseColor("#5066DD"));
        holder.note_card.setAlpha(1.f);
        holder.name.setText(list.get(position).getName());
        holder.Familya.setText(list.get(position).getFamilya());
        holder.time.setText(list.get(position).getTime());
        holder.phone.setText(list.get(position).getPhone());
        holder.note_card.setCardBackgroundColor(Color.parseColor(colorArray[position % 5]));
        holder.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), SendActivity.class);
                holder.itemView.getContext().startActivity(intent);
            }

        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLongClick){
                    holder.select_radio_butten.setVisibility(View.VISIBLE);
                    holder.note_card.setStrokeWidth(8);
                    holder.note_card.setStrokeColor(Color.parseColor("#5066DD"));
                    holder.note_card.setAlpha(0.6f);
                    deletes.add(list.get(holder.getAdapterPosition()));
                    noteInteface.LongClickMore(deletes);
                }else {
                    Intent intent = new Intent(holder.itemView.getContext(), Birthday_day.class);
                    intent.putExtra("notes_id",list.get(holder.getAdapterPosition()).getId());
                    holder.itemView.getContext().startActivity(intent);
                }

            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                 holder.select_radio_butten.setVisibility(View.VISIBLE);
                 holder.note_card.setStrokeWidth(8);
                 holder.note_card.setStrokeColor(Color.parseColor("#5066DD"));
                 holder.note_card.setAlpha(0.6f);
                 deletes.add(list.get(holder.getAdapterPosition()));
                 isLongClick = true;

                 noteInteface.onLongClick(list.get(holder.getAdapterPosition()), holder.getAdapterPosition());
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView name, Familya, phone, time;
        MaterialCardView note_card;
        RadioButton select_radio_butten;
        ImageView send;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.note_name);
            Familya = itemView.findViewById(R.id.note_familya);
            phone = itemView.findViewById(R.id.note_phone);
            time = itemView.findViewById(R.id.note_time);
            note_card = itemView.findViewById(R.id.note_card);
            select_radio_butten = itemView.findViewById(R.id.select_radio_butten);
            send = itemView.findViewById(R.id.send);
        }
    }
    public void refreshItem(List<Notes> list, int position){
        if (position == - 1){
            notifyDataSetChanged();
            this.list = list;
        }else {
            notifyItemRemoved(position);
            this.list = list;
            isLongClick = false;
        }
    }
    public void canselLongClick(){
        isLongClick = false;
        notifyDataSetChanged();
    }
}
