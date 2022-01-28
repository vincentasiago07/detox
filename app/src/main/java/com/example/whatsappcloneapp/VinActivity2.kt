package com.example.whatsappcloneapp

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class VinActivity2 : AppCompatActivity() {
    var mListpeople:ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vin2)
        mListpeople = findViewById(R.id.mListPeople)

        var users:ArrayList<User> = ArrayList()
        var myAdapter = CustomAdapter(applicationContext,users)
        var progress = ProgressDialog(this)
        progress.setTitle("Loading")
        progress.setMessage("Please wait...")



        var my_db = FirebaseDatabase.getInstance().reference.child("Names")

        progress.show()
        my_db.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                users.clear()
                for (snap in p0.children){
                    var person = snap.getValue(User::class.java)
                    users.add(person!!)
                }
                myAdapter.notifyDataSetChanged()
                progress.dismiss()
            }

            override fun onCancelled(p0: DatabaseError) {
                progress.dismiss()
                Toast.makeText(applicationContext,"DB Locked", Toast.LENGTH_LONG).show()
            }
        })


        mListpeople!!.adapter = myAdapter
    }
}

