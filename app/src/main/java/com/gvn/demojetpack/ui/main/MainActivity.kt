package com.gvn.demojetpack.ui.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.gvn.demojetpack.database.AppDatabase
import com.gvn.demojetpack.models.Note
import kotlinx.android.synthetic.main.activity_main.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.gvn.demojetpack.ui.note_detail.NoteDetailActivity
import com.gvn.demojetpack.R
import com.gvn.demojetpack.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), NoteAdapterCallBack, ViewListener {

    private var notes: MutableList<Note> = ArrayList()
    private var adapter: NoteAdapter? = null
    private var binding: ActivityMainBinding? = null
    private var handlers: MyClickHandlers? = null
    private var viewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        supportActionBar?.title = "List of note"

        getData()

        handlers = MyClickHandlers(this)
        binding?.handler = handlers

        initRecyclerView()
    }

    private fun getData() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel?.getAllNotes()
    }

    private fun initRecyclerView() {
        adapter = NoteAdapter(this, notes, this)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = this@MainActivity.adapter
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
//            notes.clear()
//            val list = AppDatabase.getInstance(this)?.noteDao()?.getAllNotes()
//            list?.let { notes.addAll(it) }
//            adapter?.notifyDataSetChanged()
            viewModel?.getAllNotes()
        }
    }

    override fun clickItem(item: Note, position: Int) {
        startActivityForResult(Intent(this, NoteDetailActivity::class.java).apply {
            putExtra(KEY_NOTE, item)
        }, 1)
    }

    override fun deleteItem(item: Note, position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Do you want delete this?")
            .setPositiveButton("Yes") { dialog, _ ->
                viewModel?.deleteNote(item)
                dialog.dismiss()
            }
            .setNegativeButton("No", null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    override fun onGetNoteSuccess(liveNotes: List<Note>) {
        Log.d("MainActivity", "onGetNoteSuccess")
//        liveNotes.observe(this, Observer<List<Note>> { data ->
            notes.clear()
            notes.addAll(liveNotes)
            adapter?.notifyDataSetChanged()
//        })
    }

    override fun onGetNoteError() {
        Toast.makeText(this, "Data error", Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteSuccess() {
        Toast.makeText(this, "Delete success", Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteError() {
        Toast.makeText(this, "Delete error", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel?.dispose()
    }

    companion object {
        const val KEY_NOTE = "KEY_NOTE"
    }

    class MyClickHandlers(var activity: AppCompatActivity) {
        fun onClickFloatBtn(view: View) {
            Log.d("MyClickHandlers", "onClickFloatBtn")
            activity.startActivityForResult(Intent(activity, NoteDetailActivity::class.java), 1)
        }
    }
}
