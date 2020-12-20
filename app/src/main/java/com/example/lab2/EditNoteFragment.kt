package com.example.lab2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lab2.entities.Note
import com.example.lab2.viewModel.NotesViewModel
import kotlinx.android.synthetic.main.fragment_add_note.view.*
import kotlinx.android.synthetic.main.fragment_edit_note.*

private const val ARG_NOTE_ID = "note_id"

class EditNoteFragment : Fragment(), View.OnClickListener {
    private var noteId: Int? = null
    private var color:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            noteId = it.getInt(ARG_NOTE_ID, -1)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        var noteLiveData = viewModel.getSingleNote(noteId!!)
        noteLiveData.observe(viewLifecycleOwner, Observer {
            var note = noteLiveData.value!!
            var title = note?.title
            var description = note?.description
            var tag = note?.tag
            var id = noteId

            //Toast.makeText(context, note?.title, Toast.LENGTH_SHORT).show()

            editTitle.setText(title)
            editDescription.setText(description)
            editTag.setText(tag)

            btnEdit.setOnClickListener {
                title = editTitle.text.toString()
                description = editDescription.text.toString()
                tag = editTag.text.toString()

                //sending values via intent
                //editIntent.putExtra("title",title)
                //editIntent.putExtra("description",description)
                //editIntent.putExtra("tag",tag)
                //editIntent.putExtra("id",id)

                var noteColor = "#FFFFFF"
                if (color != null) {
                    noteColor = color.toString()
                }

                val note = Note(id!!, title!!, description!!, noteColor, tag!!)

                if (note != null) {
                    viewModel.update(note)
                }

                Toast.makeText(context,"Note Updated", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun buttonColor(view: View){
        val id = view.id
        if (id == R.id.btncolorLightBrown) {
            cardView.setCardBackgroundColor(resources.getColor(R.color.colorBrown))
            color = "#A1887F"
        } else if (id == R.id.btncolorPink) {
            cardView.setCardBackgroundColor(resources.getColor(R.color.colorPink))
            color = "#E880FC"
        } else if (id == R.id.btncolorYellow) {
            context?.let { ContextCompat.getColor(it, R.color.colorYellow) }?.let {
                cardView.setCardBackgroundColor(
                    it
                )
            }
            color = "#FFC400"
        } else if (id == R.id.btncolorLightBlue) {
            cardView.setCardBackgroundColor(resources.getColor(R.color.colorLightBlue))
            color = "#BBDEFB"
        } else if (id == R.id.btncolorBlue) {
            cardView.setCardBackgroundColor(resources.getColor(R.color.colorBlue))
            color = "#64FFDA"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_edit_note, container, false)

        v.btncolorBlue.setOnClickListener(this)
        v.btncolorLightBlue.setOnClickListener(this)
        v.btncolorLightBrown.setOnClickListener(this)
        v.btncolorPink.setOnClickListener(this)
        v.btncolorYellow.setOnClickListener(this)

        // Inflate the layout for this fragment
        return v
    }

    override fun onClick(view: View?) {
        if (view != null) {
            buttonColor(view)
        }
    }
}