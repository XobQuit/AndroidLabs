package com.example.lab2

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab2.adapters.NotesAdapter
import com.example.lab2.entities.Note
import com.example.lab2.viewModel.NotesViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import timber.log.Timber


class MainFragment : Fragment() {
    private lateinit var viewModel: NotesViewModel

    private lateinit var notes : List<Note>
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var getAllNotes: LiveData<List<Note>>

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        //recyclerView.layoutManager = LinearLayoutManager(this)

        Log.i("INFO: ", "RECYCLER VIEW CREATE")

        getAllNotes = viewModel.getAllNotes()
        getAllNotes.observe(viewLifecycleOwner, Observer {
            notes = getAllNotes.value!!
            notesAdapter = NotesAdapter(notes)

            notesAdapter.setOnItemClickListener { note, view ->
                showNotePopup(note, view)
            }

            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = notesAdapter
        })

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()

        Timber.i("Lab4::onResume")
    }

    override fun onStop() {
        super.onStop()

        Timber.i("Lab4::onStop")
    }

    override fun onDestroy() {
        super.onDestroy()

        Timber.i("Lab4::onDestroy")
    }

    override fun onStart() {
        super.onStart()

        Timber.i("Lab4::onStart")
    }

    override fun onPause() {
        super.onPause()

            val buzzer = activity?.getSystemService<Vibrator>()

            buzzer?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    buzzer.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 2000), -1))
                } else {
                    //deprecated in API 26
                    buzzer.vibrate(longArrayOf(0, 2000), -1)
                }
            }

        Timber.i("Lab4::onPause")
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Timber.i("Lab4::onDestroyView")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addNoteFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                Toast.makeText(context, "action_share", Toast.LENGTH_LONG).show()
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
                return true
            }
            R.id.action_about ->{
                Toast.makeText(context, "action_about", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_mainFragment_to_aboutFragment)
                return true
            }
            R.id.action_termsofuse ->{
                Toast.makeText(context, "action_termsofuse", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_mainFragment_to_termsOfUsageFragment)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        /*if(requestCode == add && resultCode == Activity.RESULT_OK) {
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
        }*/
    }



    private fun showNotePopup(note: Note, noteView: View?) {
        val popup = PopupMenu(context, noteView)
        popup.inflate(R.menu.note_popup_menu)

        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->
            when (item!!.itemId) {
                R.id.action_edit -> {
                    val id = note.id

                    val bundle = bundleOf("note_id" to id)
                    findNavController().navigate(R.id.action_mainFragment_to_editNoteFragment, bundle)

                    /*var editIntent = Intent(this, EditNoteActivtiy::class.java)
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
                    Toast.makeText(this@MainActivity, item.title, Toast.LENGTH_SHORT).show()*/
                }
                R.id.action_share -> {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, note.title + " " + note.description)
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)
                }
                R.id.action_delete -> {
                    viewModel.delete(note)
                    Toast.makeText(context,"Note Deleted", Toast.LENGTH_SHORT).show()
                }
            }

            true
        })
        popup.show()
    }
}