package com.example.birthday.dp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.birthday.domain.model.Notes;

import java.util.List;
@Dao
public interface NoteDao {
    @Query("SELECT * FROM Notes")
    List<Notes> getAll();
    @Insert
    void insertAll(Notes... notes);

    @Query("SELECT * FROM Notes WHERE id IN (:id)")
    Notes loadNoteById(int id);
    @Query("SELECT * FROM Notes WHERE name LIKE '%' || :title || '%'")
    List<Notes> loadBySearch(String title);
    @Delete
    void delete(Notes notes);

    @Query("UPDATE NOTES set name = :name, familya = :familya,phone = :phone, time =:time WHERE id =:id")
    void updatePerson(int id,String name, String familya, String phone, String time );

    @Delete
    void deleteSelect(List<Notes> notes);
}
