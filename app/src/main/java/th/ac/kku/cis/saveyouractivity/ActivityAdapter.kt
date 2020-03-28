package th.ac.kku.cis.saveyouractivity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.BaseAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class ActivityAdapter(context: Context, ItemList: MutableList<ActivityItem>) : BaseAdapter() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    var contextthis: Context = context
    private var itemList = ItemList
    lateinit var mDatabase: DatabaseReference
    lateinit var auth: FirebaseAuth
    var uid: String? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // create object from view
        val objectId: String? = itemList.get(position).objID as String?
        var itemName: String? = itemList.get(position).Name as String?
        val itemDate: String? = itemList.get(position).ADate as String?
        val view: View
        val vh: ListRowHolder


        // get list view
        if (convertView == null) {
            view = mInflater.inflate(R.layout.content_item_activity, parent, false)
            vh = ListRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }
        vh.Name.text = itemName
        vh.ADate.text = itemDate
        return view
    }

    override fun getItem(index: Int): Any {
        return itemList.get(index)
    }

    override fun getItemId(index: Int): Long {
        return index.toLong()
    }

    override fun getCount(): Int {
        return itemList.size
    }

    private class ListRowHolder(row: View?) {
        val Name: TextView = row!!.findViewById<TextView>(R.id.AName) as TextView
        val ADate:TextView = row!!.findViewById<TextView>(R.id.ADate) as TextView
    }
}