package com.lamnguyen.ticket_movie_nlu.view.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class DayDashBoardFragment extends Fragment {
    private BarChart barChartViewDisplayDayNumOfSaleTicket;
    private LineChart lineChartDisplayDayRevenueReport;
    private TextView txtViewSumOfSaleTicket, txtViewSumOfRevenue;

    private String[] labels = {"Sáng", "Trưa", "Chiều", "Tối", "Đêm"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_day_dash_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        barChartViewDisplayDayNumOfSaleTicket = view.findViewById(R.id.bar_chart_view_display_day_sale_ticket);
        lineChartDisplayDayRevenueReport = view.findViewById(R.id.any_chart_view_display_day_revenu_report);
        txtViewSumOfSaleTicket = view.findViewById(R.id.text_view_sum_sale_ticket_day);
        txtViewSumOfRevenue = view.findViewById(R.id.text_view_sum_revenu_day);

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

        BarDataSet dataSet = new BarDataSet(entries, "Số vé bán được");
        dataSet.setColors(Color.WHITE);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setBarBorderColor(Color.WHITE);

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.4f);

        // Thiết lập biểu đồ
        barChartViewDisplayDayNumOfSaleTicket.setData(data);
        barChartViewDisplayDayNumOfSaleTicket.setFitBars(true);
        barChartViewDisplayDayNumOfSaleTicket.setDescription(null);
        barChartViewDisplayDayNumOfSaleTicket.animateXY(1000, 1000);
        // Cho phép cuộn và thu phóng
        barChartViewDisplayDayNumOfSaleTicket.setDragEnabled(true);
        barChartViewDisplayDayNumOfSaleTicket.setScaleEnabled(true);
        barChartViewDisplayDayNumOfSaleTicket.setPinchZoom(true);

        barChartViewDisplayDayNumOfSaleTicket.invalidate(); // Refresh chart

        // Cấu hình trục X
        XAxis xAxis = barChartViewDisplayDayNumOfSaleTicket.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.length);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        // Cấu hình trục Y
        YAxis leftAxis = barChartViewDisplayDayNumOfSaleTicket.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setTextColor(Color.WHITE);

        YAxis rightAxis = barChartViewDisplayDayNumOfSaleTicket.getAxisRight();
        rightAxis.setDrawGridLines(false);

        // Vô hiệu hóa biểu đồ bên phải
        rightAxis.setEnabled(false);
    }

    private void createLineChart() {
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0f, 5));
        entries.add(new Entry(1f, 10));
        entries.add(new Entry(2f, 20));
        entries.add(new Entry(3f, 12));
        entries.add(new Entry(4f, 9));

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
        lineChartDisplayDayRevenueReport.setData(data);
        lineChartDisplayDayRevenueReport.setDescription(null);
        lineChartDisplayDayRevenueReport.animateXY(1000, 1000);
        // Cho phép cuộn và thu phóng
        lineChartDisplayDayRevenueReport.setDragEnabled(true);
        lineChartDisplayDayRevenueReport.setScaleEnabled(true);
        lineChartDisplayDayRevenueReport.setPinchZoom(true);
        lineChartDisplayDayRevenueReport.invalidate(); // Refresh chart

        // Cấu hình trục X
        XAxis xAxis = lineChartDisplayDayRevenueReport.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.length);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        // Cấu hình trục Y
        YAxis leftAxis = lineChartDisplayDayRevenueReport.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setTextColor(Color.WHITE);

        YAxis rightAxis = lineChartDisplayDayRevenueReport.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setEnabled(false); // Vô hiệu hóa trục Y bên phải
    }
}