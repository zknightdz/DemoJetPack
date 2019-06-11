package com.gvn.demojetpack.ui.note_detail

import android.content.Context
import com.gvn.demojetpack.models.Note

class NoteDetailViewModel(private val context: Context, private val listener: NoteDetailListener) {

}

interface NoteDetailListener {

    fun onDeleteSuccess()

    fun onDeleteError()
}