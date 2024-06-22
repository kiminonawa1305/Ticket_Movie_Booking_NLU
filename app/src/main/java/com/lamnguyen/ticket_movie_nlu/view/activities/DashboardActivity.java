package com.lamnguyen.ticket_movie_nlu.view.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.graphics.vector.GradientKey;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.lamnguyen.ticket_movie_nlu.R;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = "DashboardActivity";
    private Button btnDay, btnWeek, btnMonth;
    private ImageView imgViewEditCalendar;
    private BarChart barChartViewDisplayNumOfSaleTicket;
    private LineChart lineChartDisplayRevenueReport;
    private TextView txtViewDay, txtViewWeek, txtViewMonth, txtViewSumOfSaleTicket, txtViewSumOfRevenue;
    private Spinner spnSelectCinema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnDay = findViewById(R.id.button_day);
        btnWeek = findViewById(R.id.button_week);
        btnMonth = findViewById(R.id.button_month);
        imgViewEditCalendar = findViewById(R.id.image_view_edit_calendar);
//        barChartViewDisplayNumOfSaleTicket = (BarChart) findViewById(R.id.bar_chart_view_display_sale_ticket);
//        lineChartDisplayRevenueReport = (LineChart) findViewById(R.id.any_chart_view_display_revenu_report);
//        txtViewDay = findViewById(R.id.text_view_show_day);
//        txtViewWeek = findViewById(R.id.text_view_show_week);
//        txtViewMonth = findViewById(R.id.text_view_show_month);
//        txtViewSumOfSaleTicket = findViewById(R.id.text_view_sum_sale_ticket);
//        txtViewSumOfRevenue = findViewById(R.id.text_view_sum_revenu);
//        spnSelectCinema = findViewById(R.id.spinner_name_cinema);


        addItemToSpinner();
//        createLineChart();
//        createBarChart();

        imgViewEditCalendar.setOnClickListener(v -> {
        });
    }

    private void createBarChart() {
        // Dữ liệu cho biểu đồ cột
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 15));
        entries.add(new BarEntry(1f, 20));
        entries.add(new BarEntry(2f, 25));
        entries.add(new BarEntry(3f, 30));

        BarDataSet dataSet = new BarDataSet(entries, "Số vé bán được");
        dataSet.setColors(Color.WHITE);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setBarBorderColor(Color.WHITE);

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.4f);

        // Thiết lập biểu đồ
        barChartViewDisplayNumOfSaleTicket.setData(data);
        barChartViewDisplayNumOfSaleTicket.setFitBars(true);
        barChartViewDisplayNumOfSaleTicket.setDescription(null);
        barChartViewDisplayNumOfSaleTicket.invalidate(); // Refresh chart

        // Cấu hình trục X
        String[] labels = {"Jan", "Feb", "Mar", "Apr"};
        XAxis xAxis = barChartViewDisplayNumOfSaleTicket.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.length);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        // Cấu hình trục Y
        YAxis leftAxis = barChartViewDisplayNumOfSaleTicket.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setTextColor(Color.WHITE);

        YAxis rightAxis = barChartViewDisplayNumOfSaleTicket.getAxisRight();
        rightAxis.setDrawGridLines(false);

        // Vô hiệu hóa biểu đồ bên phải
        rightAxis.setEnabled(false);
    }

    private void addItemToSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_items_name_cinema, R.layout.items_spinner_name_cinema);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSelectCinema.setAdapter(adapter);
    }

    private void createLineChart() {
        // Dữ liệu cho biểu đồ đường
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0f, 5));
        entries.add(new Entry(1f, 10));
        entries.add(new Entry(2f, 20));
        entries.add(new Entry(3f, 12));
        entries.add(new Entry(4f, 9));

        String[] labels = {"Jan", "Feb", "Mar", "Apr"};
        LineDataSet dataSet = new LineDataSet(entries, "Values");
        dataSet.setColor(Color.WHITE);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setLineWidth(2f);
        dataSet.setCircleColor(Color.WHITE); // Màu của điểm
        dataSet.setCircleRadius(4f);      // Kích thước của điểm
        dataSet.setFillAlpha(65);
        dataSet.setFillColor(Color.WHITE);
        dataSet.setDrawFilled(true);

        LineData data = new LineData(dataSet);
        lineChartDisplayRevenueReport.setData(data);
        lineChartDisplayRevenueReport.setDescription(null);
        lineChartDisplayRevenueReport.invalidate(); // Refresh chart

        // Cấu hình trục X
        XAxis xAxis = lineChartDisplayRevenueReport.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.length);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        // Cấu hình trục Y
        YAxis leftAxis = lineChartDisplayRevenueReport.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setTextColor(Color.WHITE);

        YAxis rightAxis = lineChartDisplayRevenueReport.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setEnabled(false); // Vô hiệu hóa trục Y bên phải
    }
}