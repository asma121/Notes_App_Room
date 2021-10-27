package com.example.notesapproom

import androidx.room.*

@Dao
interface NoteDao {

    @Query("SELECT * FROM Notes ORDER BY Note DESC")
    fun getNotes(): List<NoteDetail>

    @Insert
    fun insertNote(noteDetail: NoteDetail)

    @Delete
    fun deleteNote(noteDetail: NoteDetail)

    @Query("UPDATE Notes SET Note=:n WHERE id=:id")
    fun updateNote(id:Int,n:String)

}