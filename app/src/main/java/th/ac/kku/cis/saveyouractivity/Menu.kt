package th.ac.kku.cis.saveyouractivity

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class Menu : AppCompatActivity() {
    lateinit var mDB: DatabaseReference
    var uid:String=""
    lateinit var user:FirebaseUser
    var USER:UserData= UserData()
    var role:String="student"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        USER.UserData()
        Log.w("OK",USER.getuid())
        uid = USER.getuid()
        user = USER.getuser()
        mDB =FirebaseDatabase.getInstance().reference.child("Auth")//.child(uid!!)
        add_st()
    }
    fun update_bt(){
        
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
    fun add_st(){
        mDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(uid)) {
                    // it exists!
                    role=snapshot.child(uid).child("role").value.toString()
                } else {
                    // does not exist
                    if("kku.ac.th" in user.email.toString()){
                        mDB.child(uid).child("role").setValue("teacher")
                        role="teacher"
                    }
                    else{
                        mDB.child(uid).child("role").setValue("student")
                        role="student"
                    }
                }
                mDB.child(uid).child("name").setValue(USER.getname())
                check_studentid()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

    }
}
