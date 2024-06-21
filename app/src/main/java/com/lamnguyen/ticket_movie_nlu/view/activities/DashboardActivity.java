package com.lamnguyen.ticket_movie_nlu.view.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.cartesian.series.Line;
import com.anychart.enums.Anchor;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.anychart.scales.LinearColor;
import com.lamnguyen.ticket_movie_nlu.R;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = "DashboardActivity";
    private Button btnDay, btnWeek, btnMonth;
    private ImageView imgViewEditCalendar;
    private AnyChartView anyChartViewDisplayNumOfSaleTicket, getAnyChartViewDisplayRevenueReport;
    private TextView txtViewDay, txtViewWeek, txtViewMonth, txtViewSumOfSaleTicket, txtViewSumOfRevenue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnDay = findViewById(R.id.button_day);
        btnWeek = findViewById(R.id.button_week);
        imgViewEditCalendar = findViewById(R.id.image_view_edit_calendar);
        anyChartViewDisplayNumOfSaleTicket = findViewById(R.id.any_chart_view_display_sale_ticket);
        getAnyChartViewDisplayRevenueReport = findViewById(R.id.any_chart_view_display_revenu_report);
        txtViewDay = findViewById(R.id.text_view_show_day);
        txtViewWeek = findViewById(R.id.text_view_show_week);
        txtViewMonth = findViewById(R.id.text_view_show_month);
        txtViewSumOfSaleTicket = findViewById(R.id.text_view_sum_sale_ticket);
        txtViewSumOfRevenue = findViewById(R.id.text_view_sum_revenu);

        createLineChart();
    }

    private void createLineChart() {
        Log.i(TAG, "createLineChart: Run");
        Cartesian cartesian = AnyChart.line();

        List<DataEntry> seriesData = new ArrayList<>();
        seriesData.add(new ValueDataEntry("Jan", 10000));
        seriesData.add(new ValueDataEntry("Feb", 12000));
        seriesData.add(new ValueDataEntry("Mar", 18000));
        seriesData.add(new ValueDataEntry("Apr", 11000));
        seriesData.add(new ValueDataEntry("May", 15000));
        seriesData.add(new ValueDataEntry("Jun", 13000));

        Line series = cartesian.line(seriesData);
        series.name("Sales");

        cartesian.title("Monthly Sales Data");

        getAnyChartViewDisplayRevenueReport.setChart(cartesian);
    }
}