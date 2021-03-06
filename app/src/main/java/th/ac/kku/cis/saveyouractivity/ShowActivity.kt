package th.ac.kku.cis.saveyouractivity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_show.*
import th.ac.kku.cis.saveyouractivity.ActivityItem.Factory.create
import th.ac.kku.cis.saveyouractivity.StudentAcItem.Factory.create
import java.text.SimpleDateFormat
import java.util.*

class ShowActivity : AppCompatActivity() {
    lateinit var mDB: DatabaseReference
    lateinit var mDB2: DatabaseReference
    lateinit var mDBAC: DatabaseReference


    var ItemList: MutableList<StudentAcItem>? = null

    lateinit var adapter: StudentActivityAdapter
    private var listViewItems: ListView? = null

    var USER:UserData= UserData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        if (supportActionBar != null)
            supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        USER.UserData()
        val objID:String= intent.getStringExtra("i")
        mDB = FirebaseDatabase.getInstance().reference.child("Activity").child(objID)
        mDB2 = FirebaseDatabase.getInstance().reference
        mDB.orderByKey().addValueEventListener(itemListener)

        Scanbtn.setOnClickListener {
            var i = Intent(this, TScan::class.java)
            i.putExtra("i", objID)
            startActivity(i)
        }

        ItemList = mutableListOf<StudentAcItem>()

        adapter = StudentActivityAdapter(this, ItemList!!)
        listViewItems = findViewById<View>(R.id.ShowSt) as ListView
        listViewItems!!.setAdapter(adapter)
        mDBAC = FirebaseDatabase.getInstance().reference.child("Activity").child(objID).child("Member")
        mDBAC.orderByKey().addValueEventListener(itemListener2)
        AddStAc.setOnClickListener {
            // กด
            addNewItem()
        }
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
                var note: String = descriptionBox.text.toString()
            var j = mDB2.child("Auth").push()
            var st:StudentItem = StudentItem.create()
            st.code=et.text.toString()
            st.name = descriptionBox.text.toString()
            j.setValue(st)
            var t=mDBAC.child(j.key.toString())
            t.child("admin").setValue(USER.getuid())
            t.child("objID").setValue(j.key.toString())
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            t.child("time").setValue(currentDate)
            t.child("type").setValue("input")

                //newURL.url = url
               // Log.w("URL", newURL.url)
                /* try{
               newURL.URLtitle = Content().gettilte(url)
            }
            catch (ex:Exception){
                Log.e("url ->",ex.toString())
                dialog.dismiss()
            }*/

                var key: String? = ""
         //       newURL2user.Note = note
                var i = 0


                Log.w("Key Key", key)



            dialog.dismiss()
        }
        dialog.show()
    }

    var itemListener2: ValueEventListener = object : ValueEventListener {

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // call function
            //  for (datas in dataSnapshot.children) {
            ItemList?.clear()
            for (datas in dataSnapshot.children) {
                val Item = StudentAcItem.create()
                Item.objID = datas.key
                ItemList!!.add(Item)
            }
            adapter.notifyDataSetChanged()
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Item failed, display log a message
            Log.w("MainActivity", "loadItem:onCancelled", databaseError.toException())
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
