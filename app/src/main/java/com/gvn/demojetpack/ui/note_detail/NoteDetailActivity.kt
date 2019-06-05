package com.gvn.demojetpack.ui.note_detail

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.gvn.demojetpack.R
import com.gvn.demojetpack.database.AppDatabase
import com.gvn.demojetpack.databinding.ActivityNoteDetailBinding
import com.gvn.demojetpack.models.Note
import com.gvn.demojetpack.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_note_detail.*
import java.text.SimpleDateFormat
import java.util.*

class NoteDetailActivity : AppCompatActivity() {

    private var binding: ActivityNoteDetailBinding? = null
    private var note: Note? = null
    private var isEdit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_note_detail)

        note = if (intent != null) {
            if (intent.hasExtra(MainActivity.KEY_NOTE)) {
                isEdit = true
                intent.getSerializableExtra(MainActivity.KEY_NOTE) as Note
            } else
                Note("", "", "")
        } else
            Note("", "", "")

        if (isEdit)
            supportActionBar?.title = "Detail of note"
        else
            supportActionBar?.title = "Add note"

        binding?.note = note
        binding?.activity = this
    }

    fun onClickSave(view: View) {
        if (edtTitle.text.toString().isEmpty() || edtDescription.text.toString().isEmpty()) {
            Toast.makeText(this, "Field must be not empty", Toast.LENGTH_SHORT).show()
        }

        val df = SimpleDateFormat("ddMMyyyy")
        val note = Note(edtTitle.text.toString(), edtDescription.text.toString(), df.format(Date()))
        if (isEdit)
            edit(note)
        else
            add(note)
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun add(note: Note) {
        AppDatabase.getInstance(this)?.noteDao()?.insert(note)
    }

    private fun edit(note: Note) {
        AppDatabase.getInstance(this)?.noteDao()?.update(note)
    }
}
