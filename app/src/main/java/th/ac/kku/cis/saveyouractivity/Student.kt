package th.ac.kku.cis.saveyouractivity

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_menu.*

class Student : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var mDB: DatabaseReference
    var uid:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)
        if (supportActionBar != null)
            supportActionBar?.hide()
        mDB = FirebaseDatabase.getInstance().reference.child("Auth")
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        auth = FirebaseAuth.getInstance()
        B1.setOnClickListener {
            val i = Intent(this, Mycode::class.java)
            startActivity(i)
        }
        check_studentid()
        B3.setOnClickListener { singOut() }
    }
    private fun singOut() {
        auth.signOut()
        finish()
    }
    fun check_studentid(){
        mDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child(uid).child("role").value=="student") {
                    // it exists!
                    if(snapshot.child(uid).hasChild("code")){

                    }
                    else{
                        add_studentid()
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
}
