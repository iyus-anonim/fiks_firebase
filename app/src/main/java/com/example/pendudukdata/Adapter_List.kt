package com.example.pendudukdata

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create.*
import org.w3c.dom.Text

class Adapter_List(val mCtx: Context, val layoutResId: Int, val list: List<pendudukdata> )
    : ArrayAdapter<pendudukdata>(mCtx,layoutResId,list){



    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)

        val textUpdate=view.findViewById<TextView>(R.id.btnUpdate)
        val textDelete=view.findViewById<TextView>(R.id.btnDelete)



        var RNama = view.findViewById<TextView>(R.id.LVNama)
        var RNik = view.findViewById<TextView>(R.id.LVNik)


        var pendudukdata = list[position]


        RNama.text = pendudukdata.nama
        RNik.text = pendudukdata.nik

        //update
        textUpdate.setOnClickListener {
            Update(pendudukdata)
        }


        //delete


        textDelete.setOnClickListener {
            Delete(pendudukdata)
        }
        return view

    }

    private fun Delete(pendudukdata: pendudukdata) {

        val progressDialog = ProgressDialog(context,
            R.style.Theme_MaterialComponents_Light_Dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Deleting...")
        progressDialog.show()
        val mydatabase = FirebaseDatabase.getInstance().getReference("pendudukdata")
        mydatabase.child(pendudukdata.id).removeValue()
        Toast.makeText(mCtx,"Deleted!!",Toast.LENGTH_SHORT).show()
        val intent = Intent(context, DashboardActivity::class.java)
        context.startActivity(intent)

    }

    private fun Update(pendudukdata: pendudukdata) {
        val builder = AlertDialog.Builder(mCtx)

        builder.setTitle("Update")

        val inflater = LayoutInflater.from(mCtx)

        val view = inflater.inflate(R.layout.activity_action, null)

        var RNama = view.findViewById<TextView>(R.id.Unama)
        var RAlamat = view.findViewById<TextView>(R.id.UAlamat)
//        var RStatus=view.findViewById<RadioGroup>(R.id.RgStatus).checkedRadioButtonId
//        var bStts : RadioButton=view.findViewById(R.id.RgStatus)
//        var RPekerjaan=view.findViewById<RadioGroup>(R.id.RgPekerjaan).checkedRadioButtonId

        RNama.setText(pendudukdata.nama)
        RAlamat.setText(pendudukdata.alamat)

        builder.setView(view)

        builder.setPositiveButton("Update") { dialog, which ->

            val dbUsers = FirebaseDatabase.getInstance().getReference("pendudukdata")

            val nama = RNama.text.toString().trim()

            val alamat = RAlamat.text.toString().trim()

            if (nama.isEmpty()){
                RNama.error = "please enter name"
                RNama.requestFocus()
                return@setPositiveButton
            }

            if (alamat.isEmpty()){
                RAlamat.error = "please enter status"
                RAlamat.requestFocus()
                return@setPositiveButton
            }

            val pendudukdata = pendudukdata(pendudukdata.id,nama,pendudukdata.nik,alamat,
                pendudukdata.ttl, pendudukdata.jenisKelamin, pendudukdata.status, pendudukdata.pekerjaan)

            dbUsers.child(pendudukdata.id).setValue(pendudukdata).addOnCompleteListener {
                Toast.makeText(mCtx,"Updated",Toast.LENGTH_SHORT).show()
            }

        }

        builder.setNegativeButton("No") { dialog, which ->

        }

        val alert = builder.create()
        alert.show()
    }

}