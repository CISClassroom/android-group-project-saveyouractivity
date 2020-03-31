package th.ac.kku.cis.saveyouractivity

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
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
        USER.UserData()
        val secret = "Leia"+USER.getuid()
        val config = TimeBasedOneTimePasswordConfig(codeDigits = 8,
            hmacAlgorithm = HmacAlgorithm.SHA1,
            timeStep = 2,
            timeStepUnit = TimeUnit.MINUTES)
        val timeBasedOneTimePasswordGenerator = TimeBasedOneTimePasswordGenerator(secret.toByteArray(), config)
        val myBitmap: Bitmap = QRCode.from(USER.getuid()+"==="+timeBasedOneTimePasswordGenerator.generate()).bitmap()
        val myImage: ImageView = findViewById(R.id.code) as ImageView
        myImage.setImageBitmap(myBitmap)
        Name.setText(USER.getname())
    }
}
