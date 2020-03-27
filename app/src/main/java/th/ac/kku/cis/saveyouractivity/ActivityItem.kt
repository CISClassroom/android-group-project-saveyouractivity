package th.ac.kku.cis.saveyouractivity

import java.util.*

class ActivityItem {
    companion object Factory{ // สร้างเมดทอนแบบย่อ ๆ
        fun create():ActivityItem = ActivityItem()
    }
    var objID:String? = null
    var Name:String? = null
    var Details:String? = null
    var Image_url:String? = null
    var Status:Boolean? = null
    var Member = null
}