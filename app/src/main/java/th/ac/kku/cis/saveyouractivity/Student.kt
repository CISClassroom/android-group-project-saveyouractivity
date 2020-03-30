package th.ac.kku.cis.saveyouractivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_menu.*

class Student : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)
        if (supportActionBar != null)
            supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()
        B1.setOnClickListener {
            val i = Intent(this, Mycode::class.java)
            startActivity(i)
        }
        B3.setOnClickListener { singOut() }
    }
    private fun singOut() {
        auth.signOut()
        finish()
    }
}
