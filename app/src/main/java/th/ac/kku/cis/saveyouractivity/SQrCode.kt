package th.ac.kku.cis.saveyouractivity

import android.R
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import net.glxn.qrgen.android.QRCode


class SQrCode : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_s_qr_code)
        /*val myBitmap: Bitmap = QRCode.from("www.example.org").bitmap()
        val myImage: ImageView = findViewById(R.id.imageView) as ImageView
        myImage.setImageBitmap(myBitmap)*/
    }
    fun createQR(content:String){

    }
}
