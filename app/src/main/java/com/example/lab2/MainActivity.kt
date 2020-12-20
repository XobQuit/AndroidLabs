package com.example.lab2

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var appRunTimer: AppRunTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.plant(Timber.DebugTree())

        appRunTimer = AppRunTimer(this.lifecycle)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*return when (item.itemId) {
            R.id.action_about -> true
            else -> super.onOptionsItemSelected(item)
        }*/
        return false
    }

}
/*
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

        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                Toast.makeText(applicationContext, "action_share", Toast.LENGTH_LONG).show()
                true
            }
            R.id.action_about ->{
                Toast.makeText(applicationContext, "action_about", Toast.LENGTH_LONG).show()
                return true
            }
            R.id.action_termsofuse ->{
                Toast.makeText(applicationContext, "action_termsofuse", Toast.LENGTH_LONG).show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
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

 */
