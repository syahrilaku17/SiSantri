package com.syahrilirfani.sisantri.Fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.syahrilirfani.sisantri.R
import com.syahrilirfani.sisantri.Santri
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_person_add.*
import kotlinx.android.synthetic.main.splash.*
import android.content.ContentResolver



class PersonAddFragment : Fragment() {

    lateinit var ref : DatabaseReference
    var contentResolver = activity!!.contentResolver

    var btn_gambar : Button?= null
    var btn_simpan : Button?= null
    var textNama : EditText? = null
    var textKelas : EditText? = null
    var textAlamat : EditText? = null
    var textEmail : EditText? = null
    var textWali : EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_person_add, container, false)
        btn_gambar = v.findViewById(R.id.btn_image_fragment_person_add)
        btn_simpan = v.findViewById(R.id.btn_simpan_person_add)
        textNama = v.findViewById(R.id.edit_text_nama_santri_fragment_person_add)
        textKelas = v.findViewById(R.id.edit_text_kelas_fragment_person_add)
        textAlamat = v.findViewById(R.id.edit_text_alamat_fragment_person_add)
        textEmail = v.findViewById(R.id.edit_text_email_person_add)
        textWali =v.findViewById(R.id.edit_text_wali__person_add)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ref = FirebaseDatabase.getInstance().getReference("SANTRI")

        btn_simpan?.setOnClickListener {
            saveSantritoDatabase()

        }

//        btn_gambar?.setOnClickListener {
//            Log.d("RegisterActivity", "Try to show photo selector")
//            val intent = Intent(Intent.ACTION_PICK)
//            intent.type = "image/*"
//            startActivityForResult(intent, 0)
//        }
    }

    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            // proceed and check what the selected image was....
            Log.d("RegisterActivity", "Photo was selected")
            // location of where that image is stored on the device
            selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
//            btn_image_fragment_person_add.setImageBitmap(bitmap)
            val bitmapDrawable = BitmapDrawable(bitmap)
            btn_image_fragment_person_add.setBackgroundDrawable(bitmapDrawable)
        }
    }
    private fun saveSantritoDatabase() {
        val nama = textNama?.text.toString()
        val kelas = textKelas?.text.toString()
        val alamat = textAlamat?.text.toString()
        val email = textEmail?.text.toString()
        val wali = textWali?.text.toString()


        //check is empty
        if (nama.isEmpty() || kelas.isEmpty() || alamat.isEmpty() || email.isEmpty() || wali.isEmpty()) {
           return Toast.makeText(context, "isi dengan benar !", Toast.LENGTH_SHORT).show()
        }

        Log.d("PersonAddFragment", "Nama  $nama")
        Log.d("PersonAddFragment", "kelas: $kelas")
        Log.d("PersonAddFragment", "Alamat  $alamat")
        Log.d("PersonAddFragment", "Email: $email")
        Log.d("PersonAddFragment", "Wali: $wali")

        val santri = Santri(nama,kelas, alamat, email, wali)
        val santriId = ref.push().key.toString()

        ref.child(santriId).setValue(santri).addOnCompleteListener {
            Toast.makeText(context, "Successs",Toast.LENGTH_SHORT).show()
            textNama?.setText("")
            textKelas?.setText("")
            textAlamat?.setText("")
            textEmail?.setText("")
            textWali?.setText("")
            val list = mutableListOf<Santri>()
            list.removeAll { true }
        }
    }


}

