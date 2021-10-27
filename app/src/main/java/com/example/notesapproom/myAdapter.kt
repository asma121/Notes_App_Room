package com.example.notesapproom

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

class myAdapter(private val notes:List<NoteDetail>,val activity: MainActivity): RecyclerView.Adapter<myAdapter.ItemViewHolder>() {
    class ItemViewHolder(itemView : View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val note=notes[position]
        holder.itemView.apply {
            textView.text=note.note

            imageButtonEdit.setOnClickListener {
                dailog(note,activity)
                activity.recyclerView() // this line does not work you have to rotate the device to update the recyclerview
            }

            imageButtonDelete.setOnClickListener {
               NotesDatabase.getInstance(activity).NoteDao().deleteNote(note)
                activity.recyclerView()
            }
        }

    }

    override fun getItemCount()=notes.size
}

fun dailog(noteDetail: NoteDetail,activity: MainActivity){
    val builder = AlertDialog.Builder(activity)
    val input= EditText(activity)
    input.setText(noteDetail.note)
    builder.setPositiveButton("Save", DialogInterface.OnClickListener{
            dialog, id -> NotesDatabase.getInstance(activity).NoteDao().updateNote(noteDetail.id,input.text.toString())
    })
    builder.setNegativeButton("Cancel", DialogInterface.OnClickListener(){
            dialog, id -> dialog.cancel()
    })

    val alert = builder.create()
    alert.setTitle("Update Note")
    alert.setView(input)
    alert.show()

}