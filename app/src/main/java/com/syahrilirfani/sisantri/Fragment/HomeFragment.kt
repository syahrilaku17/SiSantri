package com.syahrilirfani.sisantri.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.google.firebase.database.*
import com.syahrilirfani.sisantri.Adapter
import com.syahrilirfani.sisantri.R
import com.syahrilirfani.sisantri.Santri

class HomeFragment : Fragment() {

    lateinit var ref : DatabaseReference
    lateinit var list : MutableList<Santri>
    lateinit var listView: ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_home, container, false)
        ref = FirebaseDatabase.getInstance().getReference("SANTRI")
        listView = v.findViewById(R.id.ListView_fragment_home)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                list = mutableListOf()
                if (p0.exists()){

                    for (h in p0.children){
                        val santri = h.getValue(Santri::class.java)
                        list.add(santri!!)
                        val adapter = Adapter(context!!.applicationContext,R.layout.santri,list)
                        listView.adapter = adapter
                    }

                }
            }
        })
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}// Required empty public constructor