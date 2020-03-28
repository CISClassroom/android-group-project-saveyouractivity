package th.ac.kku.cis.saveyouractivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddActivity : AppCompatActivity() {
    var USER:UserData= UserData()
    lateinit var mDB: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        USER.UserData()
        mDB = FirebaseDatabase.getInstance().reference.child("Activity")
    }
    fun Add(){
        var key = mDB.push()
        var ac:ActivityItem = ActivityItem.create()
        //ac.Name = ""
        //ac.objID=key.key
        key.setValue(ac)

    }
}
