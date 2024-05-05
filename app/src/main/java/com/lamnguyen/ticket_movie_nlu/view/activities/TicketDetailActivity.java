package com.lamnguyen.ticket_movie_nlu.view.activities;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.pdf417.encoder.BarcodeMatrix;
import com.lamnguyen.ticket_movie_nlu.R;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class TicketDetailActivity extends AppCompatActivity {
    private TextView tv_ticket_id;
    private ImageView imgv_qr;
    private QRGEncoder qrgEncoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);
        tv_ticket_id = findViewById(R.id.textview_ticket_id);
        imgv_qr = findViewById(R.id.imageview_QR);


        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE) ;
        Display display = manager.getDefaultDisplay();

        Point point = new Point();
        display.getSize(point) ;
        int width = point.x;
        int height = point.y;
        int dimen = width < height ? width : height;
        dimen = dimen * 3/4;

        try{
            qrgEncoder = new QRGEncoder(tv_ticket_id.getText().toString(), null, QRGContents.Type.TEXT, dimen);
            Bitmap bitmap = qrgEncoder.encodeAsBitmap();
            imgv_qr.setImageBitmap(bitmap);
        }catch (WriterException e){
            throw new RuntimeException(e);
        }
    }
}