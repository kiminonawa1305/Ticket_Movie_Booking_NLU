package com.lamnguyen.ticket_movie_nlu.view.activities;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.pdf417.encoder.BarcodeMatrix;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.response.TicketDetailResponse;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;
import com.lamnguyen.ticket_movie_nlu.utils.DateTimeFormat;
import com.lamnguyen.ticket_movie_nlu.utils.DialogLoading;

import org.json.JSONException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class TicketDetailActivity extends AppCompatActivity {
    private TextView tv_ticket_id;
    private ImageView imgv_qr;
    private QRGEncoder qrgEncoder;

    private TextView tv_theater_name;
    private TextView tv_movie_name;
    private TextView tv_date_and_time;
    private TextView tv_screen_name;
    private TextView tv_row;
    private TextView tv_seat;
    private TextView tv_duration;
    private ImageView imgv_poster;
    private Dialog dialog;
    private static final String TAG = "TicketDetailActivity";

    private String url = "/ticket-detail/api/009a4ccd-89a2-4bd1-a0a4-f2b5dd148961";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);


        tv_ticket_id = findViewById(R.id.textview_ticket_id);
        imgv_qr = findViewById(R.id.imageview_QR);
        tv_theater_name = findViewById(R.id.textview_theater_name);
        tv_movie_name = findViewById(R.id.textview_movie_name_td);
        tv_date_and_time = findViewById(R.id.textview_date_and_time);
        tv_screen_name = findViewById(R.id.textview_screen);
        tv_seat = findViewById(R.id.textview_seat);
        tv_row = findViewById(R.id.textview_row);
        tv_duration = findViewById(R.id.textview_duration);
        imgv_poster = findViewById(R.id.imageview_movie_td);

        dialog = DialogLoading.newInstance(this);

        getTicketDetail();
    }

    private void getTicketDetail() {
        Log.i(TAG, "getTicketDetail: " + CallAPI.URL_WEB_SERVICE + url);
        DialogLoading.showDialogLoading(dialog, getString(R.string.loading));
        CallAPI.callJsonObjectRequest(this, CallAPI.URL_WEB_SERVICE + url, "", Request.Method.GET, response -> {
            try {
                TicketDetailResponse ticketDetail = new Gson().fromJson(response.getString("data"), TicketDetailResponse.class);

                String row = ticketDetail.getNameChair().substring(0, 1);
                String seat = ticketDetail.getNameChair().substring(1);
                String room = ticketDetail.getNameRoom().split(" ")[1];
                LocalDateTime dateTime = LocalDateTime.parse(ticketDetail.getStartShowtime());

                tv_ticket_id.setText(ticketDetail.getId());
                tv_theater_name.setText(ticketDetail.getNameCinema());
                tv_movie_name.setText(ticketDetail.getNameMovie());
                tv_date_and_time.setText(DateTimeFormat.getDateTime(dateTime));
                tv_screen_name.setText(room);
                tv_seat.setText(seat);
                tv_row.setText(row);
                tv_duration.setText(ticketDetail.getDuration());

                // Load poster
                Glide.with(this).load(ticketDetail.getPoster()).into(imgv_poster);

                createQR(ticketDetail.getId());
            } catch (JSONException e) {
                Log.e(TAG, "getTicketDetail: ", e);
            }
            dialog.dismiss();
        }, error -> {
            dialog.dismiss();
            Log.e(TAG, "getTicketDetail: ", error);
            createQR("xxxxxxxx");
        });


    }

    private void createQR(String ticket_id) {
        // Tạo QR từ chuỗi text ticket id
        // Lấy WindowManager và Display:
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();

        // Lấy kích thước màn hình:
        Point point = new Point();
        display.getSize(point); //lấy kích thước màn hình và lưu nó vào đối tượng Point.
        int width = point.x;
        int height = point.y;
        // Tính toán kích thước cho mã QR:
        int dimen = width < height ? width : height;
        dimen = dimen * 3 / 4;

        // Tạo mã QR:
        try {
            qrgEncoder = new QRGEncoder(tv_ticket_id.getText().toString(), null, QRGContents.Type.TEXT, dimen);

            /*
            Bitmap đại diện cho một ma trận các điểm ảnh (pixels) được sắp xếp 2D.
            Mỗi điểm ảnh trong Bitmap có thể được mô tả bằng một số nguyên, trong đó mỗi bit của số nguyên đó biểu diễn một màu sắc cụ thể.
             */
            Bitmap bitmap = qrgEncoder.encodeAsBitmap();
            imgv_qr.setImageBitmap(bitmap);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }
}