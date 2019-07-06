package com.syahrilirfani.sisantri

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class Adapter(val mCtx: Context, val layoutResId: Int, val list: List<Santri> )
    : ArrayAdapter<Santri>(mCtx,layoutResId,list){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)

        val textNama = view.findViewById<TextView>(R.id.tv_nama_santri)
        val textKelas = view.findViewById<TextView>(R.id.tv_kelas_santri)
        val textAlamat = view.findViewById<TextView>(R.id.tv_alamat_santri)
        val textEmail = view.findViewById<TextView>(R.id.tv_email_santri)
        val textWali = view.findViewById<TextView>(R.id.tv_wali_santri)

        val santri = list[position]

        textNama.text = santri.nama
        textKelas.text = santri.kelas
        textAlamat.text = santri.alamat
        textEmail.text = santri.email
        textWali.text = santri.wali

        return view

    }
}