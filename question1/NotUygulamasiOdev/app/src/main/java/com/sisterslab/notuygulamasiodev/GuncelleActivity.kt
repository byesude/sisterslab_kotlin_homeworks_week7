package com.sisterslab.notuygulamasiodev

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.sisterslab.notuygulamasiodev.databinding.ActivityGuncelleBinding

class GuncelleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGuncelleBinding
    private lateinit var not: Notlar
    private lateinit var refNotlar: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuncelleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarGuncelle.title = "Ders Güncelle"
        setSupportActionBar(binding.toolbarGuncelle)

        val db = FirebaseDatabase.getInstance()
        refNotlar = db.getReference("notlar")

        not = intent.getSerializableExtra("nesne") as Notlar

        binding.editTextDers.setText(not.ders_adi)
        binding.editTextVize.setText((not.vize_notu).toString())
        binding.editTextFinal.setText((not.final_notu).toString())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_sil -> {
                Snackbar.make(binding.toolbarGuncelle, "Silinsin mi?", Snackbar.LENGTH_SHORT)
                    .setAction("EVET"){
                        refNotlar.child(not.not_id!!).removeValue()
                        startActivity(Intent(this@GuncelleActivity,MainActivity::class.java))
                        finish()
                    }.show()
                return true
            }
            R.id.action_guncelle ->{
                val ders_adi = binding.editTextDers.text.toString().trim()
                val vize_notu = binding.editTextVize.text.toString().trim()
                val final_notu = binding.editTextFinal.text.toString().trim()

                if(TextUtils.isEmpty(ders_adi)){
                    Snackbar.make(binding.toolbarGuncelle,"Ders adı boş bırakılamaz.", Snackbar.LENGTH_SHORT).show()
                    return false
                }

                if(TextUtils.isEmpty(vize_notu)){
                    Snackbar.make(binding.toolbarGuncelle,"Vize notu boş bırakılamaz.", Snackbar.LENGTH_SHORT).show()
                    return false
                }

                if(TextUtils.isEmpty(final_notu )){
                    Snackbar.make(binding.toolbarGuncelle,"Final notu boş bırakılamaz.", Snackbar.LENGTH_SHORT).show()
                    return false
                }

                val bilgiler = HashMap<String, Any>()
                bilgiler.put("ders_adi", ders_adi)
                bilgiler.put("vize_notu", vize_notu.toInt())
                bilgiler.put("final_notu", final_notu.toInt())

                refNotlar.child(not.not_id!!).updateChildren(bilgiler)
                startActivity(Intent(this@GuncelleActivity,MainActivity::class.java))
                finish()
                return true
            }
            else -> return false
        }
    }
}