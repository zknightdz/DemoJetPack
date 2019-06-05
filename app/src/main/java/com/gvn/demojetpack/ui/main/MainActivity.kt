package com.gvn.demojetpack.ui.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.gvn.demojetpack.database.AppDatabase
import com.gvn.demojetpack.models.Note
import kotlinx.android.synthetic.main.activity_main.*
import androidx.databinding.DataBindingUtil
import com.gvn.demojetpack.ui.note_detail.NoteDetailActivity
import com.gvn.demojetpack.R
import com.gvn.demojetpack.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), NoteAdapterCallBack {

    private var notes: MutableList<Note> = ArrayList()
    private var adapter: NoteAdapter? = null
    private var binding: ActivityMainBinding? = null
    private var handlers: MyClickHandlers? = null

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
        val list = AppDatabase.getInstance(this)?.noteDao()?.getAllNotes()
        list?.let { notes.addAll(it) }
    }

    private fun initRecyclerView() {
        adapter = NoteAdapter(this, notes, this)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            notes.clear()
            val list = AppDatabase.getInstance(this)?.noteDao()?.getAllNotes()
            list?.let { notes.addAll(it) }
            adapter?.notifyDataSetChanged()
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
                AppDatabase.getInstance(this)?.noteDao()?.deleteNote(item)
                notes.clear()
                val list = AppDatabase.getInstance(this)?.noteDao()?.getAllNotes()
                list?.let { notes.addAll(it) }
                adapter?.notifyDataSetChanged()
                dialog.dismiss()
            }
            .setNegativeButton("No", null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
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
