package com.example.notesapproom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var rv:RecyclerView
    lateinit var etNote:EditText
    lateinit var buAddNote:Button
    lateinit var notes:List<NoteDetail>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv=findViewById(R.id.rv)
        etNote=findViewById(R.id.etNote)
        buAddNote=findViewById(R.id.buAddNote)

        NotesDatabase.getInstance(applicationContext)
        notes= arrayListOf()

        recyclerView()

        buAddNote.setOnClickListener {
            val n=etNote.text.toString()
            val note=NoteDetail(0,n)
            CoroutineScope(IO).launch {
                NotesDatabase.getInstance(applicationContext).NoteDao().insertNote(note)
            }
            Toast.makeText(applicationContext,"data saved",Toast.LENGTH_LONG).show()
            recyclerView()
        }
    }

    fun recyclerView(){
        notes=NotesDatabase.getInstance(applicationContext).NoteDao().getNotes()
        rv.adapter=myAdapter(notes,this)
        rv.layoutManager=LinearLayoutManager(this)
    }
}