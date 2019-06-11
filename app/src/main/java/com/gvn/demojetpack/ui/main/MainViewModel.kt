package com.gvn.demojetpack.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gvn.demojetpack.database.AppDatabase
import com.gvn.demojetpack.models.Note
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val context: Context, private val listener: ViewListener) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val noteDao = AppDatabase.getInstance(context)?.noteDao()
    private var liveNotes: LiveData<List<Note>>? = null

    fun getAllNotes() {
        noteDao?.getAllNotes()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ data ->
                listener.onGetNoteSuccess(data)
            },
                { _ ->
                    listener.onGetNoteError()
                })?.let {
                disposable.add(
                    it
                )
            }
    }

    fun deleteNote(note: Note) {
        val result = noteDao?.deleteNote(note)
        if (result == -1)
            listener.onDeleteError()
        else
            listener.onDeleteSuccess()
    }

    fun getLiveNotes() = liveNotes

    fun dispose() {
        disposable.dispose()
    }
}

interface ViewListener {
    fun onGetNoteSuccess(liveNotes: List<Note>)

    fun onGetNoteError()

    fun onDeleteSuccess()

    fun onDeleteError()
}