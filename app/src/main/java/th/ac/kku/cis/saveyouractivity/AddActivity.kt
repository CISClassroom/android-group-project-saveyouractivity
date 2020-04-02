package th.ac.kku.cis.saveyouractivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add.*
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : AppCompatActivity() {
    var USER:UserData= UserData()
    lateinit var mDB: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        if (supportActionBar != null)
            supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        USER.UserData()
        mDB = FirebaseDatabase.getInstance().reference.child("Activity")
        Save.setOnClickListener {
            Add()
            finish()
        }
        btncancel.setOnClickListener {

            var i = Intent(this, Menu::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        }//ปุ่มยกเลิกกิจกรรม
    }
    fun Add(){ //แอดกิจกรรมลงfibase เอามาจาก class activityitems
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        var key = mDB.push()
        var ac:ActivityItem = ActivityItem.create()
        ac.TimeAdd = currentDate
        ac.Name = Name.text.toString()
        ac.ADate = ADate.text.toString()
        ac.Field = Field.text.toString()
        ac.TStart = TStart.text.toString()
        ac.TEnd = TEnd.text.toString()
        ac.Number = Number.text.toString()
        ac.TId = USER.getuid()
        ac.Details = Details.text.toString()
        ac.Moderator = Moderator.text.toString()
        ac.objID=key.key
        key.setValue(ac)

    }
}
