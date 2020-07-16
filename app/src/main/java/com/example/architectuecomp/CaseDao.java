package com.example.architectuecomp;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CaseDao {

    @Insert
    void insert(Case aCase);
    @Update
    void update(Case aCase);

    @Delete
    void delete(Case aCase);

    @Query("select * from case_table order by total desc")
    LiveData<List<Case>> getAllCases();

    @Query("delete from case_table")
    void deleteAll();
}
