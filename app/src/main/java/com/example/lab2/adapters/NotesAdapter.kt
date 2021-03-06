package com.example.lab2.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab2.R
import com.example.lab2.entities.Note

class NotesAdapter(private val notes: List<Note>) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private lateinit var clickListener: (Note, View?) -> Unit

    inner class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        var txtTitle: TextView? = null
        var txtDescription: TextView? = null
        var txtTag: TextView? = null
        var cardView: CardView? = null
        var cardViewTag: CardView? = null

        init {
            this.txtTitle = row.findViewById(R.id.txtTitle)
            this.txtDescription = row.findViewById(R.id.txtDescription)
            this.txtTag = row.findViewById(R.id.txtTag)
            this.cardView = row.findViewById(R.id.cardView)
            this.cardViewTag = row.findViewById(R.id.cardViewTag)
        }
    }

    public fun setOnItemClickListener(listener: (Note, View?) -> Unit)
    {
        clickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter.ViewHolder {
        var view = LayoutInflater.from(parent.context).
            inflate(R.layout.recyclerview_item_row, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NotesAdapter.ViewHolder, position: Int) {
        val note = notes.get(position)
        holder.txtTitle?.text = note.title
        holder.txtDescription?.text = note.description
        holder.txtTag?.text = note.tag
        holder.cardView?.setBackgroundColor(Color.parseColor(note.color))
       // Log.i("Test", note.color)
        holder.cardViewTag?.setCardBackgroundColor(Color.parseColor(note.color))

        //holder.bind(note)
        holder.itemView.setOnClickListener { clickListener(note, holder.cardView) }
    }
}
