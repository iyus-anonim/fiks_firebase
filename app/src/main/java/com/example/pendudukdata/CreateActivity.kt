package com.example.pendudukdata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_create.*

class CreateActivity() : AppCompatActivity() {

//    lateinit var CNama:EditText
//    lateinit var Cnik:EditText
//    lateinit var CAlamat:EditText
//
//    lateinit var save:ImageButton

    lateinit var ref : DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)



//
//        CNama = findViewById(R.id.CNama)
//        Cnik = findViewById(R.id.CNIK)
//        CAlamat = findViewById(R.id.CAlamat)
//
//        save = findViewById(R.id.save)



        save.setOnClickListener {
            saveData()
        }

    }




    private fun saveData(){
        ref=FirebaseDatabase.getInstance().getReference("pendudukdata")
        val nama =CNama.text.toString().trim()
        val nik = CNIK.text.toString().trim()
        val alamat = CAlamat.text.toString().trim()
        val TTL=CTTL.text.toString().trim()





        val jk: String
    val id=CRGJenKel.checkedRadioButtonId
           if (id==CPria.id) {
              jk= CPria.text.toString().trim()
           } else {
               jk=CWanita.text.toString().trim()
           }

//status
        val status: String
        val sts=CRGStat.checkedRadioButtonId
        if (sts==CRMenikah.id) {
            status= CRMenikah.text.toString().trim()
        } else {
            status=CRBelumNikah.text.toString().trim()
        }

//pekerjaan

        val pekerjaan: String
        val PK=CRGKerja.checkedRadioButtonId
        if (PK==CRPNS.id) {
            pekerjaan= CRPNS.text.toString().trim()
        } else if(PK==CRPelajar.id) {
            pekerjaan=CRPelajar.text.toString().trim()
        }else{
            pekerjaan=CRSwasta.text.toString().trim()
        }



        if(nama.isEmpty()){
            CNama.error ="Masukkan nama dengan benar"
            return
        }

        val id_penduduk= ref.push().key.toString()

        val data = pendudukdata(id_penduduk,nama,nik,alamat,TTL,jk,status,pekerjaan)

        ref.child(id_penduduk).setValue(data).addOnSuccessListener {
            Toast.makeText(this@CreateActivity,"success",Toast.LENGTH_SHORT).show()
        }


    }
}
