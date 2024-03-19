package com.example.birthday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.birthday.domain.model.Notes;
import com.example.birthday.dp.NoteDao;
import com.example.birthday.dp.NoteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Birthday_day extends AppCompatActivity {
    ImageView buttenSave;
    EditText editname, editfamilya, editphone, editime;
    NoteDatabase noteDatabase;
    Notes notes;
    int notesId = -1 ;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday_day);
        initViews();
        noteDatabase = Room.databaseBuilder(getApplicationContext(),NoteDatabase.class,"note_database").allowMainThreadQueries().build();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Birthday_day.this,MainActivity.class);
                startActivity(intent);
            }
        });

        buttenSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editname.getText().toString();
                String familya = editfamilya.getText().toString();
                String phone = editphone.getText().toString();
                String time = editime.getText().toString();
                if (name.isEmpty() || familya.isEmpty()){
                    Toast.makeText(Birthday_day.this, "Bo`sh maydonlarni to`ldiring", Toast.LENGTH_SHORT).show();
                    return;
                }
                Notes notes = new Notes(name,familya,phone,time);
                if (notesId > 0){
                    noteDatabase.noteDao().updatePerson(notesId, name, familya,phone,time);
                }else {

                    noteDatabase.noteDao().insertAll(notes);
                }
                Intent intent = new Intent(Birthday_day.this,MainActivity.class);
                startActivity(intent);
            }

        });
    }

    private void initViews() {
        buttenSave = findViewById(R.id.buttenadd);
        editname = findViewById(R.id.name);
        editfamilya = findViewById(R.id.familya);
        editphone = findViewById(R.id.phone);
        editime = findViewById(R.id.data);
        back = findViewById(R.id.back);
    }


}