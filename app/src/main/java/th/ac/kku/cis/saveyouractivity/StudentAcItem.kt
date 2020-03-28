package th.ac.kku.cis.saveyouractivity

class StudentAcItem {
    companion object Factory{ // สร้างเมดทอนแบบย่อ ๆ
        fun create():StudentAcItem = StudentAcItem()
    }
    var time:String? = null
    var admin:String? = null
    var objID:String? = null
    var Name:String? = null
    var ID:String? = null
    var type:String? = null
}