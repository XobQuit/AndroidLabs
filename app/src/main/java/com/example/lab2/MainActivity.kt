package com.example.lab2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab2.adapters.NotesAdapter
import com.example.lab2.entities.Note
import com.example.lab2.viewModel.NotesViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var notes : List<Note>
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var getAllNotes: LiveData<List<Note>>

    //private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var viewModel: NotesViewModel

    companion object{
        val add = 1
        val edit =2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*notes = listOf(
            Note(1, "Note1", "Note1_desc", "red", "tag1"),
            Note(1, "Note2", "Note2_desc", "green", "tag1"),
            Note(1, "Note3", "Note3_desc", "blue", "tag1"),
            Note(1, "Note4", "Note4_desc", "purple", "tag1"),
            Note(1, "Note5", "Note5_desc", "cyan", "tag1")
        )*/

        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        //recyclerView.layoutManager = LinearLayoutManager(this)

        getAllNotes = viewModel.getAllNotes()
        getAllNotes.observe(this, Observer {
            notes = getAllNotes.value!!
            notesAdapter = NotesAdapter(notes)
            
            notesAdapter.setOnItemClickListener { note, view ->
                showNotePopup(note, view)
            }
            
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = notesAdapter
        })

        //Floating action button
        fab.setOnClickListener {
            var addIntent = Intent(this, AddNoteActivity::class.java)
            startActivityForResult(addIntent, add)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == add && resultCode == Activity.RESULT_OK) {
            var title = data?.getStringExtra("title")
            var description =  data?.getStringExtra("description")
            var tag = data?.getStringExtra("tag")
            var color = data?.getStringExtra("color")
            val note = Note(0,title.toString(), description.toString(), color.toString(), tag.toString())

            viewModel.insert(note)
            Toast.makeText(applicationContext,"Note Saved", Toast.LENGTH_SHORT).show()
        }

        if(requestCode == edit && resultCode == Activity.RESULT_OK) {
            var title = data?.getStringExtra("title")
            var description =  data?.getStringExtra("description")
            var tag = data?.getStringExtra("tag")
            var id = data?.getStringExtra("id")
            var color = data?.getStringExtra("color")
            val note  = Note(id?.toInt()!!, title.toString(), description.toString(), color.toString(), tag.toString())

            viewModel.update(note)
            Toast.makeText(applicationContext,"Note Updated", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showNotePopup(note: Note, noteView: View?) {
        val popup = PopupMenu(this, noteView)
        popup.inflate(R.menu.note_popup_menu)

        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->
            when (item!!.itemId) {
                R.id.action_edit -> {
                    val id = note.id

                    var editIntent = Intent(this, EditNoteActivtiy::class.java)
                    var title  = note.title
                    var description = note.description
                    var tag = note.tag
                    var color = note.color

                    editIntent.putExtra("title",title)
                    editIntent.putExtra("description",description)
                    editIntent.putExtra("tag",tag)
                    editIntent.putExtra("id",id.toString())
                    editIntent.putExtra("color",color.toString())

                    startActivityForResult(editIntent, edit)

                    Log.e("MainActivityclick",title + description + tag)
                    //Toast.makeText(this@MainActivity, item.title, Toast.LENGTH_SHORT).show()
                }
                R.id.action_delete -> {
                    viewModel.delete(note)
                    Toast.makeText(applicationContext,"Note Deleted",Toast.LENGTH_SHORT).show()
                    //Toast.makeText(this@MainActivity, item.title, Toast.LENGTH_SHORT).show()
                }
            }

            true
        })
        popup.show()
    }
}