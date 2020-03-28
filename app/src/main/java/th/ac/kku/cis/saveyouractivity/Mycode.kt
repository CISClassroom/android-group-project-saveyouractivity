package th.ac.kku.cis.saveyouractivity

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_mycode.*
import net.glxn.qrgen.android.QRCode


class Mycode : AppCompatActivity() {
    var USER:UserData= UserData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mycode)
        USER.UserData()
        val myBitmap: Bitmap = QRCode.from(USER.getuid()).bitmap()
        val myImage: ImageView = findViewById(R.id.code) as ImageView
        myImage.setImageBitmap(myBitmap)
        Name.setText(USER.getname())
    }
}
