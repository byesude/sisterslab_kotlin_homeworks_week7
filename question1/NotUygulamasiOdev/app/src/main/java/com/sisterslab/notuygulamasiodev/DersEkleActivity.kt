package com.sisterslab.notuygulamasiodev

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.sisterslab.notuygulamasiodev.databinding.ActivityDersEkleBinding

class DersEkleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDersEkleBinding
    private lateinit var refNotlar: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDersEkleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarDersEkle.title = "Yeni Kayıt"
        setSupportActionBar(binding.toolbarDersEkle)

        val db = FirebaseDatabase.getInstance()
        refNotlar = db.getReference("notlar")

        binding.buttonEkle.setOnClickListener{
            val ders_adi = binding.editTextDers.text.toString().trim()
            val vize_notu = binding.editTextVize.text.toString().trim()
            val final_notu = binding.editTextFinal.text.toString().trim()

            if(TextUtils.isEmpty(ders_adi)){
                Snackbar.make(binding.toolbarDersEkle,"Ders adı boş bırakılamaz.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(TextUtils.isEmpty(vize_notu)){
                Snackbar.make(binding.toolbarDersEkle,"Vize notu boş bırakılamaz.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(TextUtils.isEmpty(final_notu )){
                Snackbar.make(binding.toolbarDersEkle,"Final notu boş bırakılamaz.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val not = Notlar("", ders_adi, vize_notu.toInt(), final_notu.toInt())

            refNotlar.push().setValue(not)

            startActivity(Intent(this@DersEkleActivity,MainActivity::class.java))
            finish()
        }
    }
}