package com.lamnguyen.ticket_movie_nlu.view.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.TimeoutError;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.zxing.WriterException;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.response.TicketDetailResponse;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;
import com.lamnguyen.ticket_movie_nlu.utils.DateTimeFormat;
import com.lamnguyen.ticket_movie_nlu.utils.DialogLoading;

import org.json.JSONException;

import java.time.LocalDateTime;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class TicketDetailActivity extends AppCompatActivity {
    private TextView tvTicketId;
    private ImageView imgvQr;
    private QRGEncoder qrgEncoder;
    private TextView tvTheaterName;
    private TextView tvMovieName;
    private TextView tvDateAndTime;
    private TextView tvScreenName;
    private TextView tvRow;
    private TextView tvSeat;
    private TextView tvDuration;
    private ImageView imgvPoster;
    private ImageView btnBack;
    private Dialog dialog;
    private LinearLayout llAvail;
    private static final String TAG = "TicketDetailActivity";

    private String url = "/ticket/api/detail/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        boolean avail = getIntent().getBooleanExtra(getString(R.string.avail_ticket), true);

        btnBack = findViewById(R.id.back_to_tickets);
        llAvail = findViewById(R.id.linear_layout_avail);
        if (!avail)
            llAvail.setVisibility(LinearLayout.GONE);

        tvTicketId = findViewById(R.id.textview_ticket_id);
        imgvQr = findViewById(R.id.imageview_QR);
        tvTheaterName = findViewById(R.id.textview_theater_name);
        tvMovieName = findViewById(R.id.textview_movie_name_td);
        tvDateAndTime = findViewById(R.id.textview_date_and_time);
        tvScreenName = findViewById(R.id.textview_screen);
        tvSeat = findViewById(R.id.textview_seat);
        tvRow = findViewById(R.id.textview_row);
        tvDuration = findViewById(R.id.textview_duration);
        imgvPoster = findViewById(R.id.imageview_movie_td);

        dialog = DialogLoading.newInstance(this);

        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(getString(R.string.avail_ticket), avail);
            MainActivity.saveFragmentId(intent, MainActivity.FRAGMENT_TICKET);
            startActivity(intent);
        });

        getTicketDetail();
    }

    private void getTicketDetail() {
        String ticketId = getIntent().getStringExtra("ticketId");
        DialogLoading.showDialogLoading(dialog, getString(R.string.loading));
        CallAPI.callJsonObjectRequest(this, CallAPI.URL_WEB_SERVICE + url + ticketId, "", Request.Method.GET, response -> {
            try {
                TicketDetailResponse ticketDetail = new Gson().fromJson(response.getString("data"), TicketDetailResponse.class);

                String row = ticketDetail.getNameChair().substring(0, 1);
                String seat = ticketDetail.getNameChair().substring(1);
                String room = ticketDetail.getNameRoom().split(" ")[1];
                LocalDateTime dateTime = LocalDateTime.parse(ticketDetail.getStartShowtime());

                tvTicketId.setText(ticketDetail.getId());
                tvTheaterName.setText(ticketDetail.getNameCinema());
                tvMovieName.setText(ticketDetail.getNameMovie());
                tvDateAndTime.setText(DateTimeFormat.getDateTime(dateTime));
                tvScreenName.setText(room);
                tvSeat.setText(seat);
                tvRow.setText(row);
                tvDuration.setText(ticketDetail.getDuration());

                // Load poster
                Glide.with(this).load(ticketDetail.getPoster()).into(imgvPoster);

                createQR(ticketDetail.getId());
            } catch (JSONException e) {
                Log.e(TAG, "getTicketDetail: ", e);
            }
            dialog.dismiss();
        }, error -> {
            dialog.dismiss();
            createQR("xxxxxxxx");
            if (error instanceof TimeoutError || error instanceof NoConnectionError)
                Toast.makeText(this, "Lỗi server!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();

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
            qrgEncoder = new QRGEncoder(tvTicketId.getText().toString(), null, QRGContents.Type.TEXT, dimen);

            /*
            Bitmap đại diện cho một ma trận các điểm ảnh (pixels) được sắp xếp 2D.
            Mỗi điểm ảnh trong Bitmap có thể được mô tả bằng một số nguyên, trong đó mỗi bit của số nguyên đó biểu diễn một màu sắc cụ thể.
             */
            Bitmap bitmap = qrgEncoder.encodeAsBitmap();
            imgvQr.setImageBitmap(bitmap);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }
}