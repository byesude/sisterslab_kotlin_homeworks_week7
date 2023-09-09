package com.sisterslab.notuygulamasiodev

import java.io.Serializable

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Notlar(var not_id : String? = "",
                  var ders_adi : String? = "",
                  var vize_notu : Int? = 0,
                  var final_notu : Int? = 0) : Serializable {
}