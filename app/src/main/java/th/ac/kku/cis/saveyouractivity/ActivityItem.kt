package th.ac.kku.cis.saveyouractivity

import java.util.*

class ActivityItem {
    companion object Factory{ // สร้างเมดทอนแบบย่อ ๆ
        fun create():ActivityItem = ActivityItem()
    }
    var Name:String? = null
    var ADate:String? = null
    var TStart:String? = null
    var TEnd:String? = null
    var Field:String? = null
    var Number:String? = null
    var Moderator:String? = null
    var objID:String? = null

}