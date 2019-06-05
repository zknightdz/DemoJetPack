package com.gvn.demojetpack.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gvn.demojetpack.databinding.ItemNoteBinding
import com.gvn.demojetpack.models.Note

class NoteAdapter(
    private val context: Context,
    private val notes: List<Note>,
    private val listener: NoteAdapterCallBack
) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val itemBinding = ItemNoteBinding.inflate(inflater, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(notes[position], position, listener)
    }

    inner class ViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Note, position: Int, listener: NoteAdapterCallBack) {
            binding.note = item
            binding.position = position
            binding.listener = listener
            binding.executePendingBindings()
        }
    }
}

interface NoteAdapterCallBack {
    fun clickItem(item: Note, position: Int)

    fun deleteItem(item: Note, position: Int)
}