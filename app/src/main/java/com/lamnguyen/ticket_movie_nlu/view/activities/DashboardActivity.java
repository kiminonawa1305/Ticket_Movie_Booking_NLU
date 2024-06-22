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
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.lamnguyen.ticket_movie_nlu.R;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = "DashboardActivity";
    private Button btnDay, btnWeek, btnMonth;
    private ImageView imgViewEditCalendar;
    private AnyChartView anyChartViewDisplayNumOfSaleTicket, anyChartViewDisplayRevenueReport;
    private BarChart barChartViewDisplayNumOfSaleTicket;
    private TextView txtViewDay, txtViewWeek, txtViewMonth, txtViewSumOfSaleTicket, txtViewSumOfRevenue;
    private Spinner spnSelectCinema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnDay = findViewById(R.id.button_day);
        btnWeek = findViewById(R.id.button_week);
        imgViewEditCalendar = findViewById(R.id.image_view_edit_calendar);
        barChartViewDisplayNumOfSaleTicket = (BarChart) findViewById(R.id.bar_chart_view_display_sale_ticket);
        anyChartViewDisplayRevenueReport = findViewById(R.id.any_chart_view_display_revenu_report);
        txtViewDay = findViewById(R.id.text_view_show_day);
        txtViewWeek = findViewById(R.id.text_view_show_week);
        txtViewMonth = findViewById(R.id.text_view_show_month);
        txtViewSumOfSaleTicket = findViewById(R.id.text_view_sum_sale_ticket);
        txtViewSumOfRevenue = findViewById(R.id.text_view_sum_revenu);
        spnSelectCinema = findViewById(R.id.spinner_name_cinema);

        createBarChart();
        addItemToSpinner();
        createLineChart();


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
        rightAxis.setEnabled(true);
    }

    private void addItemToSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_items_name_cinema, R.layout.items_spinner_name_cinema);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSelectCinema.setAdapter(adapter);
    }

    private void createLineChart() {
        Cartesian cartesian = AnyChart.line();
        cartesian.animation(true);

        List<DataEntry> seriesData = new ArrayList<>();
        seriesData.add(new ValueDataEntry("Sáng", 10000));
        seriesData.add(new ValueDataEntry("Trưa", 12000));
        seriesData.add(new ValueDataEntry("Chiều", 18000));
        seriesData.add(new ValueDataEntry("Tối", 11000));

        Line series = cartesian.line(seriesData);
        series.name("Doanh thu");
        series.color("#ffffff");
        setBackgroundGradient(cartesian);

        anyChartViewDisplayRevenueReport.setChart(cartesian);
    }
    public void setBackgroundGradient(Cartesian cartesian){
        cartesian.background().fill(new GradientKey("#4C84BC", 0, 1), 135, false, 0.5);
        // Set X-axis labels color
        cartesian.xAxis(0).labels().fontColor("#ffffff");

        // Set Y-axis labels color
        cartesian.yAxis(0).labels().fontColor("#ffffff");

    }
}