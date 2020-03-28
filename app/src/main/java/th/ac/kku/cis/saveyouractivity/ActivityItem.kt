package th.ac.kku.cis.saveyouractivity

import java.util.*

class ActivityItem {
    companion object Factory{ // สร้างเมดทอนแบบย่อ ๆ
        fun create():ActivityItem = ActivityItem()
    }
    var objID:String? = null

}