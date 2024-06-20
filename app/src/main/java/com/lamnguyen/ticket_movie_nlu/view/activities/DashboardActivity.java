package com.lamnguyen.ticket_movie_nlu.view.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.enums.Anchor;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.anychart.scales.LinearColor;
import com.lamnguyen.ticket_movie_nlu.R;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

//        AnyChartView anyChartView = findViewById(R.id.any_chart_view);

        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("Trend of Sales of the Most Popular Products of ACME Corp.");

        cartesian.yAxis(0).title("Number of Bottles Sold (thousands)");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        List<DataEntry> seriesData = new ArrayList<>();
        seriesData.add(new ValueDataEntry("1986", 3.6));
        seriesData.add(new ValueDataEntry("1987", 7.1));
        seriesData.add(new ValueDataEntry("1988", 8.5));
        seriesData.add(new ValueDataEntry("1989", 9.2));
        seriesData.add(new ValueDataEntry("1990", 10.1));
        seriesData.add(new ValueDataEntry("1991", 11.6));
        seriesData.add(new ValueDataEntry("1992", 16.4));
        seriesData.add(new ValueDataEntry("1993", 18.0));
        seriesData.add(new ValueDataEntry("1994", 13.2));
        seriesData.add(new ValueDataEntry("1995", 12.0));

        cartesian.line(seriesData).name("Brandy").tooltip()
                .position(Position.RIGHT_CENTER)
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

//        anyChartView.setChart(cartesian);
    }
}