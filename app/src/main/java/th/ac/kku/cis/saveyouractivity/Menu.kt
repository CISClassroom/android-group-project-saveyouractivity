package th.ac.kku.cis.saveyouractivity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class Menu : AppCompatActivity() {
    lateinit var mDB: DatabaseReference
    var uid:String=""
    lateinit var user:FirebaseUser
    var USER:UserData= UserData()
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
    fun add_st(){
        mDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(uid)) {
                    // it exists!
                } else {
                    // does not exist
                    if("kku.ac.th" in user.email.toString()){
                        mDB.child(uid).child("role").setValue("teacher")
                    }
                    else{
                        mDB.child(uid).child("role").setValue("student")
                    }
                }
                mDB.child(uid).child("name").setValue(USER.getname())
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

    }
}
