package com.example.lab2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lab2.entities.Note
import com.example.lab2.viewModel.NotesViewModel
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.android.synthetic.main.fragment_add_note.view.*
import java.util.*

class AddNoteFragment : Fragment(), View.OnClickListener {
    private var color:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAdd.setOnClickListener {
            val title = addTitle.text.toString()
            val description = addDescription.text.toString()
            val tag = addTag.text.toString()

            val viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
            var noteColor = "#FFFFFF"
            if (color != null) {
                noteColor = color.toString()
            }

            val note = Note(0, title, description, noteColor, tag, Calendar.getInstance().time, Calendar.getInstance().time)

            Toast.makeText(context,"Note Saved", Toast.LENGTH_SHORT).show()

            viewModel.insert(note)
            //parentFragmentManager.popBackStackImmediate()
        }
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
        val v = inflater.inflate(R.layout.fragment_add_note, container, false)

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

    companion object {
        @JvmStatic
        fun newInstance() = AddNoteFragment() // factory method
    }
}