package th.ac.kku.cis.saveyouractivity

class StudentItem {
    companion object Factory{ // สร้างเมดทอนแบบย่อ ๆ
        fun create():StudentItem = StudentItem()
    }
    var code:String?=null
    var name:String?=null
    var role:String="student"
    var type:String="input"
}