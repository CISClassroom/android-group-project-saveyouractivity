package th.ac.kku.cis.saveyouractivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class Menu : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var mDB: DatabaseReference
    var uid:String=""
    lateinit var user:FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        auth = FirebaseAuth.getInstance()
        user=auth.currentUser!!
        uid = user!!.uid
        mDB =FirebaseDatabase.getInstance().reference.child("Auth")//.child(uid!!)
        add_st()
    }
    fun add_st(){
        mDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(uid)) {
                    mDB.child(uid).child("name").setValue(user.displayName.toString())
                    // it exists!
                } else {
                    // does not exist
                    if("kku.ac.th" in user.email.toString()){
                        mDB.child(uid).child("role").setValue("teacher")
                    }
                    else{
                        mDB.child(uid).child("role").setValue("student")
                    }
                    mDB.child(uid).child("name").setValue(user.displayName.toString())
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

    }
}
