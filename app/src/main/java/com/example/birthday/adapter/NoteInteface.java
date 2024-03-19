package com.example.birthday.adapter;

import com.example.birthday.domain.model.Notes;

import java.util.List;

public interface NoteInteface {
    void onLongClick(Notes notes,int position );

    void LongClickMore(List<Notes> list);
}
