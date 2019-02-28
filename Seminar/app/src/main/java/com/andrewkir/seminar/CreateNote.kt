package com.andrewkir.seminar

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AppCompatDelegate
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_create_note.*

class CreateNote : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme)
        } else {
            setTheme(R.style.AppTheme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        val notes = getNotes()
        println(notes.size)
        val adapter = ArrayAdapter(this, R.layout.raw_list, notes)
        NoteslistView.adapter = adapter

        addBtn.setOnClickListener {
            val text = noteEditText.text.toString()
            if(text.isNotEmpty()) {
                notes.add(text)
                saveNotes(notes)
                adapter.notifyDataSetChanged()
                noteEditText.setText("")
            }
        }
        delBtn.setOnClickListener {
            notes.clear()
            saveNotes(notes)
            adapter.notifyDataSetChanged()
        }
    }


    private fun saveNotes(notes:MutableList<String>){
        var res = ""
        for(note in notes){
            res += "$note;;;"
        }
        val sPref = getSharedPreferences("MyPref", MODE_PRIVATE)
        val editor = sPref.edit()
        editor.putString("NOTES", res.slice(0..res.length-4))
        editor.apply()
    }
    private fun getNotes(): MutableList<String> {
        val sPref = getSharedPreferences("MyPref", MODE_PRIVATE)
        val text = sPref.getString("NOTES","")
        val notes = if(text.isEmpty()) mutableListOf() else text.split(";;;").toMutableList()
        return notes
    }
}
