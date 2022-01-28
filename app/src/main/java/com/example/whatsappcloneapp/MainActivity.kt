package com.example.whatsappcloneapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    var mEdtName:EditText? = null
    var mEdtEmail:EditText? = null
    var mEdtAge:EditText? = null
    var mBtnSave:Button? = null
    var mBtnView:Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mEdtName = findViewById(R.id.mEdtName)
        mEdtEmail = findViewById(R.id.mEdtEmail)
        mEdtAge = findViewById(R.id.mEdtAge)
        mBtnSave = findViewById(R.id.mBtnsave)
        mBtnView = findViewById(R.id.mBtnview)

        mBtnSave!!.setOnClickListener {
            var name = mEdtName!!.text.toString()
            var email = mEdtEmail!!.text.toString()
            var age = mEdtAge!!.text.toString()
            var time = System.currentTimeMillis()

            var progress = ProgressDialog(this)
            progress.setTitle("Saving")
            progress.setMessage("Please wait...")

            if (name.isEmpty() or email.isEmpty() or age.isEmpty()){
                Toast.makeText(this,"Please fill all the inputs",Toast.LENGTH_LONG).show()
            }else{

                var my_child = FirebaseDatabase.getInstance().reference.child("Names/$time")
                var data = User(name,email,age)


                progress.show()
                my_child.setValue(data).addOnCompleteListener { task->
                    progress.dismiss()
                    if (task.isSuccessful){
                        mEdtName!!.setText(null)
                        mEdtEmail!!.setText(null)
                        mEdtAge!!.setText(null)
                        Toast.makeText(this,"Saving successful",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this,"Saving failed",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        mBtnView!!.setOnClickListener {
            val Intent=intent
            startActivity(Intent(this,VinActivity2::class.java))
        }

//        mBtnView!!.setOnClickListener {
//            startActivity(Intent(this,VinActivity2::class.java))
//        }
    }

    class UsersActivity {

    }
}
