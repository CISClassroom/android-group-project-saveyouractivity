package th.ac.kku.cis.saveyouractivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_show.*

class ShowActivity : AppCompatActivity() {
    lateinit var mDB: DatabaseReference
    lateinit var mDBAC: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        val objID:String= intent.getStringExtra("i")
        mDB = FirebaseDatabase.getInstance().reference.child("Activity").child(objID)
        mDB.orderByKey().addValueEventListener(itemListener)
        Scanbtn.setOnClickListener {
            var i = Intent(this, TScan::class.java)
            i.putExtra("i", objID)
            startActivity(i)
        }
    }
    var itemListener: ValueEventListener = object : ValueEventListener {

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // call function
          //  for (datas in dataSnapshot.children) {
            var datas = dataSnapshot

                Name.text = datas.child("name").value.toString()
                ADate.text = datas.child("adate").value.toString()
                TStart.text = datas.child("tstart").value.toString()
                TEnd.text = datas.child("tend").value.toString()
                Details.text = datas.child("details").value.toString()
                Field.text = datas.child("field").value.toString()
                Moderator.text = datas.child("moderator").value.toString()
           // }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Item failed, display log a message
            Log.w("MainActivity", "loadItem:onCancelled", databaseError.toException())
        }
    }
}
