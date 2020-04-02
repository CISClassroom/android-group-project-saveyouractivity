package th.ac.kku.cis.saveyouractivity

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import dev.turingcomplete.kotlinonetimepassword.HmacAlgorithm
import dev.turingcomplete.kotlinonetimepassword.TimeBasedOneTimePasswordConfig
import dev.turingcomplete.kotlinonetimepassword.TimeBasedOneTimePasswordGenerator
import kotlinx.android.synthetic.main.activity_mycode.*
import net.glxn.qrgen.android.QRCode
import java.util.concurrent.TimeUnit


class Mycode : AppCompatActivity() {
    var USER:UserData= UserData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mycode)
        if (supportActionBar != null)
            supportActionBar?.hide()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        USER.UserData()
        val secret = "Leia"+USER.getuid()
        val config = TimeBasedOneTimePasswordConfig(codeDigits = 8,
            hmacAlgorithm = HmacAlgorithm.SHA1,
            timeStep = 1,
            timeStepUnit = TimeUnit.MINUTES)
        val timeBasedOneTimePasswordGenerator = TimeBasedOneTimePasswordGenerator(secret.toByteArray(), config)
        val myBitmap: Bitmap = QRCode.from(USER.getuid()+"==="+timeBasedOneTimePasswordGenerator.generate()).bitmap()
        val myImage: ImageView = findViewById(R.id.code) as ImageView
        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tv4.setText(" " + millisUntilFinished / 1000)
            }

            override fun onFinish() {
                tv4.setText("หมดเวลา !!")

                val builder = AlertDialog.Builder(this@Mycode)
                builder.setTitle("หมดเวลาแล้ว !")
                builder.setMessage(">>> กรูณากดโชว์ QR CODE อีกครั้ง")
                builder.setPositiveButton("ตกลง"){dialog, which ->
                    var i = Intent(this@Mycode, Student::class.java)
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(i)
                }

                val dialog: AlertDialog = builder.create()
                dialog.show()

            }
        }.start()
        myImage.setImageBitmap(myBitmap)
        Name.setText(USER.getname())
    }
}
