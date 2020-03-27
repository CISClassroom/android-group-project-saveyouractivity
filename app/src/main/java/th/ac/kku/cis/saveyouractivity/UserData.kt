package th.ac.kku.cis.saveyouractivity

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class UserData {
    lateinit var auth: FirebaseAuth
    lateinit var user: FirebaseUser
    fun UserData(){
        auth = FirebaseAuth.getInstance()
        user=auth.currentUser!!
    }
    public fun getuid():String{
        return user.uid
    }
    public  fun getuser():FirebaseUser{
        return  user
    }
    public fun getname():String{
        return  user.displayName!!.toString()
    }
}