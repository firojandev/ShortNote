package com.demo.shortnote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.shortnote.adapter.NoteRecyclerViewAdapter
import com.demo.shortnote.databinding.ActivityMainBinding
import com.demo.shortnote.factory.NoteViewModelFactory
import com.demo.shortnote.localdb.ShortNoteDB
import com.demo.shortnote.model.MyNote
import com.demo.shortnote.repository.MyNoteRepository
import com.demo.shortnote.viewmodel.NoteViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myNoteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val dao = ShortNoteDB.getInstance(applicationContext).myNoteDAO
        val repository = MyNoteRepository(dao)
        val factory = NoteViewModelFactory(repository)

        myNoteViewModel = ViewModelProvider(this,factory).get(NoteViewModel::class.java)
        binding.myViewModel = myNoteViewModel

        binding.lifecycleOwner = this

        myNoteViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        initRecyclerView()


    }

    private fun initRecyclerView(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        noteAdapter = NoteRecyclerViewAdapter({selectedItem : MyNote -> itemRowClicked(selectedItem)})
        binding.recyclerView.adapter = noteAdapter

        displaySubscribers()
    }

    private fun displaySubscribers(){
        myNoteViewModel.getSaveMyNotes().observe(this, Observer {
            noteAdapter.setList(it)
            noteAdapter.notifyDataSetChanged()
        })
    }

    private fun itemRowClicked(myNote: MyNote) {
        myNoteViewModel.initUpdateOrDelete(myNote)
    }
}