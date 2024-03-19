package com.example.birthday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.birthday.adapter.NoteAdapter;
import com.example.birthday.adapter.NoteInteface;
import com.example.birthday.domain.model.Notes;
import com.example.birthday.dp.NoteDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteInteface, TextWatcher {
    RecyclerView notesReci;
    FloatingActionButton buttenAdd;
    NoteAdapter noteAdapter;
    NoteDatabase noteDatabase;
    RelativeLayout mainTopBar;
    LinearLayout deleteTopBar;
    ImageView deleteButten, canselButten;
    EditText searchEdit;


    private List<Notes> list;
    private Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        Initviews();


        noteDatabase = Room.databaseBuilder(getApplicationContext(),NoteDatabase.class,"note_database").allowMainThreadQueries().build();
        List<Notes> list = noteDatabase.noteDao().getAll();
        searchEdit.addTextChangedListener(this);
        noteAdapter = new NoteAdapter(list, this);
        notesReci.setAdapter(noteAdapter);
        buttenAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Birthday_day.class);
                startActivity(intent);
            }
        });

    }



    private void Initviews() {
        buttenAdd = findViewById(R.id.buttenAdd);
        notesReci = findViewById(R.id.notes_RecyclerView);
        deleteTopBar = findViewById(R.id.deleteTopBar);
        mainTopBar = findViewById(R.id.relativeLayout);
        deleteButten = findViewById(R.id.butten_delete);
        canselButten = findViewById(R.id.butten_cancel);
        searchEdit = findViewById(R.id.editText);

    }

    @Override
    public void onLongClick(Notes notes, int position) {
        showDeleteTopbar();
        deleteButten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteDatabase.noteDao().delete(notes);
                noteAdapter.refreshItem(noteDatabase.noteDao().getAll(), position);
                noteAdapter.canselLongClick();
                showMainTopBar();
            }
        });
        canselButten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMainTopBar();
                noteAdapter.canselLongClick();
            }
        });

        canselButten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMainTopBar();
                noteAdapter.canselLongClick();
            }
        });
    }

    @Override
    public void LongClickMore(List<Notes> list) {
        showDeleteTopbar();
        deleteButten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteDatabase.noteDao().deleteSelect(list);
                noteAdapter.refreshItem(noteDatabase.noteDao().getAll(), -1 );
                showMainTopBar();
            }
        });
    }

    public void showDeleteTopbar(){
        mainTopBar.setVisibility(View.GONE);
        deleteTopBar.setVisibility(View.VISIBLE);
    }

    public void showMainTopBar(){
        mainTopBar.setVisibility(View.VISIBLE);
        deleteTopBar.setVisibility(View.GONE);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().isEmpty()){
            noteAdapter.refreshItem(noteDatabase.noteDao().getAll(),-1);
        }else {
            String name = s.toString();
            List<Notes> searches = noteDatabase.noteDao().loadBySearch(name);
            noteAdapter.refreshItem(searches,-1);

        }

    }
}