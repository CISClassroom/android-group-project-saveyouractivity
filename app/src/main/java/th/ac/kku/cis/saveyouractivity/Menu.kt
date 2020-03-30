package th.ac.kku.cis.saveyouractivity

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_menu.*


class Menu : AppCompatActivity() {
    lateinit var mDB: DatabaseReference
    lateinit var mDBAC: DatabaseReference
    var uid:String=""
    lateinit var user:FirebaseUser
    var USER:UserData= UserData()
    var role:String="student"
    lateinit var auth: FirebaseAuth
    var ItemList: MutableList<ActivityItem>? = null

    lateinit var adapter: ActivityAdapter
    private var listViewItems: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        auth = FirebaseAuth.getInstance()
        USER.UserData()
        Log.w("OK",USER.getuid())
        uid = USER.getuid()
        user = USER.getuser()
        mDB =FirebaseDatabase.getInstance().reference.child("Auth")//.child(uid!!)
        add_st()
        B1.visibility = View.GONE
        B3.setOnClickListener {
            singOut()
            finish()
        }
        ItemList = mutableListOf<ActivityItem>()
        adapter = ActivityAdapter(this, ItemList!!)
        listViewItems = findViewById<View>(R.id.ShowActivity) as ListView
        listViewItems!!.visibility = View.GONE
        listViewItems!!.setAdapter(adapter)
        listViewItems!!.setOnItemClickListener { parent, view, position, id ->
           // Toast.makeText(this,ItemList!![position].objID.toString(),Toast.LENGTH_LONG).show()
            var i = Intent(applicationContext,th.ac.kku.cis.saveyouractivity.ShowActivity::class.java)
            i.putExtra("i", ItemList!![position].objID.toString())
            startActivity(i)
        }
        mDBAC = FirebaseDatabase.getInstance().reference.child("Activity")
        mDBAC.orderByKey().addValueEventListener(itemListener)
    }
    var itemListener: ValueEventListener = object : ValueEventListener {

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // call function
            addDataToList(dataSnapshot)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Item failed, display log a message
            Log.w("MainActivity", "loadItem:onCancelled", databaseError.toException())
        }
    }
    private fun addDataToList(dataSnapshot: DataSnapshot) {
        ItemList?.clear()
        for (datas in dataSnapshot.children) {
            //val name = datas.child("ShrubbedWord").value.toString()
            //val map = datas.getValue() as HashMap<String, Any>
            // add data to object
            val ACItem = ActivityItem.create()
            ACItem.objID = datas.key
            //val urlobj =  datas.child("urlobj").getValue().toString()
            ACItem.Name = datas.child("name").getValue().toString()
            ACItem.ADate = datas.child("adate").getValue().toString()
            ItemList!!.add(ACItem)
        }
        adapter.notifyDataSetChanged()
    }
    private fun singOut() {
        auth.signOut()
    }
    fun update_bt(){
        if(role=="student"){
            /*B1.visibility = View.VISIBLE
            B1.setText("Show My Qr Code")
            B1.setOnClickListener {
                val i = Intent(this, Mycode::class.java)
                startActivity(i)
            }*/
            val i = Intent(this, Student::class.java)
            startActivity(i)
            finish()
        }
        else{
            B1.visibility = View.VISIBLE
            B1.setText("Add Activity")
            B1.setOnClickListener {
                val i = Intent(this, AddActivity::class.java)
                startActivity(i)
            }
            listViewItems!!.visibility = View.VISIBLE
         /*   B2.visibility = View.VISIBLE
            B2.setText("Scan")
            B2.setOnClickListener {
                val i = Intent(this, TScan::class.java)
                startActivity(i)
            }*/

        }
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
                update_bt()
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
                    if(snapshot.child(uid).hasChild("type")){

                    }
                    else{
                        mDB.child(uid).child("type").setValue("auth")
                    }
                } else {
                    // does not exist
                    if("kku.ac.th" in user.email.toString()){
                        mDB.child(uid).child("role").setValue("teacher")
                        mDB.child(uid).child("type").setValue("auth")
                        role="teacher"
                    }
                    else{
                        mDB.child(uid).child("role").setValue("student")
                        mDB.child(uid).child("type").setValue("auth")
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
