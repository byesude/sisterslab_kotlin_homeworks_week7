package com.sisterslab.notuygulamasiodev

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.SharedValues.SharedValuesListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sisterslab.notuygulamasiodev.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var notlarListe : ArrayList<Notlar>
    private lateinit var adapter : NotlarAdapter
    private lateinit var refNotlar: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = "Ders Notları"
        setSupportActionBar(binding.toolbar)

        binding.rv.setHasFixedSize(true)
        binding.rv.layoutManager = LinearLayoutManager(this)

        val db = FirebaseDatabase.getInstance()
        refNotlar = db.getReference("notlar")

        notlarListe = ArrayList()

        var ders1 = Notlar("", "İnternet Progamcılığı", 95, 80)
        var ders2 = Notlar("", "Android Programlama", 85,75)
        var ders3 = Notlar("", "İçerik Yönetim Sistemi", 50, 60)
        var ders4 = Notlar("", "Web Tasarım Temelleri", 100, 85)

        notlarListe.add(ders1)
        notlarListe.add(ders2)
        notlarListe.add(ders3)
        notlarListe.add(ders4)

        adapter = NotlarAdapter(this, notlarListe)

        binding.rv.adapter = adapter

        tumNotlar()

        binding.fab.setOnClickListener{
            startActivity(Intent(this@MainActivity,DersEkleActivity::class.java))
        }
    }

    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    fun tumNotlar(){
        refNotlar.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                notlarListe.clear()
                for (i in snapshot.children){
                    val not = i.getValue(Notlar::class.java)

                    if (not != null){
                        not.not_id = i.key
                        notlarListe.add(not)
                    }
                }

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}