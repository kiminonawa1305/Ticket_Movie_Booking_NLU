package com.lamnguyen.ticket_movie_nlu.view.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

public class WeekDashBoardFragment extends Fragment {

    private BarChart barChartViewDisplayWeekNumOfSaleTicket;
    private LineChart lineChartDisplayWeekRevenueReport;
    private TextView txtViewSumOfSaleTicket, txtViewSumOfRevenue;

    private String[] labels = {"T2", "T3", "T4", "T5", "T6", "T7", "CN"};

    public static WeekDashBoardFragment newInstance() {
        return new WeekDashBoardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_week_dash_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        barChartViewDisplayWeekNumOfSaleTicket = view.findViewById(R.id.bar_chart_view_display_week_sale_ticket);
        lineChartDisplayWeekRevenueReport = view.findViewById(R.id.any_chart_view_display_week_revenu_report);
        txtViewSumOfSaleTicket = view.findViewById(R.id.text_view_sum_sale_ticket_week);
        txtViewSumOfRevenue = view.findViewById(R.id.text_view_sum_revenu_week);

        createLineChart();
        createBarChart();
    }

    private void createBarChart() {
        // Dữ liệu cho biểu đồ cột
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 15));
        entries.add(new BarEntry(1f, 20));
        entries.add(new BarEntry(2f, 25));
        entries.add(new BarEntry(3f, 30));
        entries.add(new BarEntry(4f, 20));
        entries.add(new BarEntry(5f, 25));
        entries.add(new BarEntry(6f, 30));

        BarDataSet dataSet = new BarDataSet(entries, "Số vé bán được");
        dataSet.setColors(Color.WHITE);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setBarBorderColor(Color.WHITE);

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.4f);

        // Thiết lập biểu đồ
        barChartViewDisplayWeekNumOfSaleTicket.setData(data);
        barChartViewDisplayWeekNumOfSaleTicket.setFitBars(true);
        barChartViewDisplayWeekNumOfSaleTicket.setDescription(null);
        barChartViewDisplayWeekNumOfSaleTicket.animateXY(1000, 1000);
        // Cho phép cuộn và thu phóng
        barChartViewDisplayWeekNumOfSaleTicket.setDragEnabled(true);
        barChartViewDisplayWeekNumOfSaleTicket.setScaleEnabled(true);
        barChartViewDisplayWeekNumOfSaleTicket.setPinchZoom(true);

        barChartViewDisplayWeekNumOfSaleTicket.invalidate(); // Refresh chart

        // Cấu hình trục X
        XAxis xAxis = barChartViewDisplayWeekNumOfSaleTicket.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.length);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        // Cấu hình trục Y
        YAxis leftAxis = barChartViewDisplayWeekNumOfSaleTicket.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setTextColor(Color.WHITE);

        YAxis rightAxis = barChartViewDisplayWeekNumOfSaleTicket.getAxisRight();
        rightAxis.setDrawGridLines(false);

        // Vô hiệu hóa biểu đồ bên phải
        rightAxis.setEnabled(false);
    }

    private void createLineChart() {
        // Dữ liệu cho biểu đồ đường
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0f, 5));
        entries.add(new Entry(1f, 10));
        entries.add(new Entry(2f, 20));
        entries.add(new Entry(3f, 12));
        entries.add(new Entry(4f, 9));
        entries.add(new Entry(5f, 20));
        entries.add(new Entry(6f, 12));

        LineDataSet dataSet = new LineDataSet(entries, "Doanh thu");
        dataSet.setColor(Color.WHITE);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setLineWidth(2f);
        dataSet.setCircleColor(Color.WHITE); // Màu của điểm
        dataSet.setCircleRadius(4f);      // Kích thước của điểm
        dataSet.setFillAlpha(65);
        dataSet.setFillColor(Color.WHITE);
        dataSet.setDrawFilled(true);

        LineData data = new LineData(dataSet);
        lineChartDisplayWeekRevenueReport.setData(data);
        lineChartDisplayWeekRevenueReport.setDescription(null);
        lineChartDisplayWeekRevenueReport.animateXY(1000, 1000);
        // Cho phép cuộn và thu phóng
        lineChartDisplayWeekRevenueReport.setDragEnabled(true);
        lineChartDisplayWeekRevenueReport.setScaleEnabled(true);
        lineChartDisplayWeekRevenueReport.setPinchZoom(true);

        lineChartDisplayWeekRevenueReport.invalidate(); // Refresh chart

        // Cấu hình trục X
        XAxis xAxis = lineChartDisplayWeekRevenueReport.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.length);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        // Cấu hình trục Y
        YAxis leftAxis = lineChartDisplayWeekRevenueReport.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setTextColor(Color.WHITE);

        YAxis rightAxis = lineChartDisplayWeekRevenueReport.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setEnabled(false); // Vô hiệu hóa trục Y bên phải
    }
}