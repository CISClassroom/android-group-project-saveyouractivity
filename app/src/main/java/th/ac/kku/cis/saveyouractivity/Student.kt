package th.ac.kku.cis.saveyouractivity

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.WindowManager
import android.widget.EditText
import android.widget.LinearLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_menu.*
import java.text.SimpleDateFormat
import java.util.*

class Student : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var mDB: DatabaseReference
    var uid:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)
        if (supportActionBar != null)
            supportActionBar?.hide()
        mDB = FirebaseDatabase.getInstance().reference.child("Auth").ref
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        auth = FirebaseAuth.getInstance()
        uid=auth.currentUser!!.uid
        check_studentid()
        B1.setOnClickListener {

            val i = Intent(applicationContext, Mycode::class.java)
            startActivity(i)

        }

        B3.setOnClickListener { singOut() }
    }
    private fun singOut() {
        auth.signOut()
        val i = Intent(applicationContext, MainActivity::class.java)
        startActivity(i)
        finish()
    }
    fun check_studentid(){
        mDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child(uid).child("role").value=="student") {
                    // it exists!
                    if((snapshot.child(uid).hasChild("code")&&snapshot.child(uid).child("code").value!=null)&&(snapshot.child(uid).hasChild("name")&&snapshot.child(uid).child("name").value!=null)){

                    }
                    else{
                        addNewItem()
                    }
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
    fun add_studentid(){
        var taskEditText: EditText =  EditText(this)
        val dialog: AlertDialog = AlertDialog.Builder(this)
            .setTitle("Input Student ID")
            .setMessage("Ex 60XXXXXXX-X")
            .setView(taskEditText)
            .setPositiveButton("Save",
                DialogInterface.OnClickListener { dialog, which ->
                    val task: String = java.lang.String.valueOf(taskEditText.getText())
                    mDB.child(uid).child("code").setValue(task)
                })
            // .setNegativeButton("Cancel", null)
            .create()
        dialog.show()
    }
    fun addNewItem(){
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage("Add Student")
        dialog.setTitle("Enter Student ID and Full Name")

        val context: Context? = this
        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL

        val et = EditText(context)
        et.hint = "Student ID (Ex 60XXXXXXX-X)"
        et.inputType= InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS //"textWebEmailAddress"
        layout.addView(et) // Notice this is an add method

        val descriptionBox = EditText(context)
        descriptionBox.hint = "Full Name"
        layout.addView(descriptionBox) // Another add method
        dialog.setView(layout)

        dialog.setPositiveButton("Submit"){
                dialog,positiveButton ->
            //var newURL = URLItem.create()
            //  var url: String = c.clean(et.text.toString())
            var name: String = descriptionBox.text.toString()
           var id : String =et.text.toString()
            mDB.child(uid).child("code").setValue(id)
            mDB.child(uid).child("name").setValue(name)

            dialog.dismiss()
        }
        dialog.show()
    }

}
