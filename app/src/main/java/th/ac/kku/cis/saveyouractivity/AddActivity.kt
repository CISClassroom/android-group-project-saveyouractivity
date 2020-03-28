package th.ac.kku.cis.saveyouractivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {
    var USER:UserData= UserData()
    lateinit var mDB: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        USER.UserData()
        mDB = FirebaseDatabase.getInstance().reference.child("Activity")
        Save.setOnClickListener {
            Add()
            finish()
        }
    }
    fun Add(){
        var key = mDB.push()
        var ac:ActivityItem = ActivityItem.create()
        ac.Name = Name.text.toString()
        ac.ADate = ADate.text.toString()
        ac.Field = Field.text.toString()
        ac.TStart = TStart.text.toString()
        ac.TEnd = TEnd.text.toString()
        ac.Number = Number.text.toString()
        ac.Moderator = Moderator.text.toString()
        ac.objID=key.key
        key.setValue(ac)

    }
}
