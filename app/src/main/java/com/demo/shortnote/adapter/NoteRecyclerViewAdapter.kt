package com.demo.shortnote.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.demo.shortnote.R
import com.demo.shortnote.databinding.NoteItemRowBinding
import com.demo.shortnote.model.MyNote

class NoteRecyclerViewAdapter(private val onClickListener: (MyNote) -> Unit): RecyclerView.Adapter<MyViewHolder>() {

    private val myNotesList = ArrayList<MyNote>()

    fun setList(myNotes: List<MyNote>){
        myNotesList.clear()
        myNotesList.addAll(myNotes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: NoteItemRowBinding = DataBindingUtil.inflate(layoutInflater,
            R.layout.note_item_row,parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = myNotesList[position]
        holder.bind(note,onClickListener)
    }

    override fun getItemCount(): Int {
        return myNotesList.size
    }
}

class MyViewHolder(val binding: NoteItemRowBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(myNote: MyNote,onClickListener: (MyNote) -> Unit){
        binding.tvTitle.text = myNote.title
        binding.tvDateTime.text = myNote.dateTime
        binding.tvDetails.text = myNote.details
        binding.llItemRow.setOnClickListener{
            onClickListener(myNote)
        }
    }
}