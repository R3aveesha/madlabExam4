package com.example.madlab4

import NotesDatabaseHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.madlab4.databinding.ActivityAddnoteBinding

class ADDnoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddnoteBinding
    private lateinit var db: NotesDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddnoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)

        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val note = Note(0, title, content) // Assuming you have defined Note class
            db.insertNote(note) // Assuming you have implemented insertNote() method in NotesDatabaseHelper
            finish()
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
        }
    }
}
