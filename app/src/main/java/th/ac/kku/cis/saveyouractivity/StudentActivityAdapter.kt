package th.ac.kku.cis.saveyouractivity

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class StudentActivityAdapter(context: Context, ItemList: MutableList<StudentAcItem>) : BaseAdapter() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var itemList = ItemList
    lateinit var mDatabase: DatabaseReference
    lateinit var auth: FirebaseAuth
    private var uid: String? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // create object from view
        val vh: ListRowHolder
        val objectId: String? = itemList.get(position).objID as String?
        /*var itemName: String? = itemList.get(position).Name as String?
        val itemID: String? = itemList.get(position).ID as String?*/
        val view: View

        mDatabase = FirebaseDatabase.getInstance().reference.child("Auth").child(objectId!!)



        // get list view
        if (convertView == null) {
            view = mInflater.inflate(R.layout.content_item_student_activity, parent, false)
            vh = ListRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }
        mDatabase.orderByKey().addListenerForSingleValueEvent(object :ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // call function
                //  for (datas in dataSnapshot.children) {
                var datas = dataSnapshot

                vh.code.text = datas.child("code").value.toString()
                vh.Name.text = datas.child("name").value.toString()

                // }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Item failed, display log a message
                Log.w("MainActivity", "loadItem:onCancelled", databaseError.toException())
            }
        })
        return view
    }
   /* var itemListener: ValueEventListener = object : ValueEventListener {

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // call function
            //  for (datas in dataSnapshot.children) {
            var datas = dataSnapshot

            vh.code.text = datas.child("code").value.toString()
            vh.Name.text = datas.child("name").value.toString()

            // }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Item failed, display log a message
            Log.w("MainActivity", "loadItem:onCancelled", databaseError.toException())
        }
    }*/

    override fun getItem(index: Int): Any {
        return itemList.get(index)
    }

    override fun getItemId(index: Int): Long {
        return index.toLong()
    }

    override fun getCount(): Int {
        return itemList.size
    }

    class ListRowHolder(row: View?) {
        val Name: TextView = row!!.findViewById<TextView>(R.id.Stname) as TextView
        var code: TextView = row!!.findViewById<TextView>(R.id.Stcode) as TextView
    }
}