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
import com.lamnguyen.ticket_movie_nlu.response.DashboardResponse;

import java.util.ArrayList;
import java.util.List;

public class MonthDashboardFragment extends Fragment {


    public static MonthDashboardFragment newInstance() {
        return new MonthDashboardFragment();
    }
    private BarChart barChartViewDisplayMonthNumOfSaleTicket;
    private LineChart lineChartDisplayMonthRevenueReport;
    private TextView txtViewSumOfSaleTicket, txtViewSumOfRevenue;
    private DashboardResponse data;
    private String[] labels = {"Tuần 1", "Tuần 2", "Tuần 3", "Tuần 4", "Tuần 5"};

    public static MonthDashboardFragment newInstance(DashboardResponse data) {
        MonthDashboardFragment fragment = new MonthDashboardFragment();
        Bundle args = new Bundle();
        args.putSerializable("data", data);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_month_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        barChartViewDisplayMonthNumOfSaleTicket = view.findViewById(R.id.bar_chart_view_display_month_sale_ticket);
        lineChartDisplayMonthRevenueReport = view.findViewById(R.id.any_chart_view_display_month_revenu_report);
        txtViewSumOfSaleTicket = view.findViewById(R.id.text_view_sum_sale_ticket_month);
        txtViewSumOfRevenue = view.findViewById(R.id.text_view_sum_revenu_month);

        if (getArguments() != null) {
            data = (DashboardResponse) getArguments().getSerializable("data");
        }
        createLineChart();
        createBarChart();
        showTotal();
    }
    private void showTotal() {
        txtViewSumOfSaleTicket.setText(String.valueOf(data.getTotalNumOfTickets()));
        txtViewSumOfRevenue.setText(String.valueOf(data.getTotalRevenue()));
    }
    private void createBarChart() {
        // Dữ liệu cho biểu đồ cột
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, data.getNumOfTickets().get("FIRST_WEEK")));
        entries.add(new BarEntry(1f, data.getNumOfTickets().get("SECOND_WEEK")));
        entries.add(new BarEntry(2f, data.getNumOfTickets().get("THIRD_WEEK")));
        entries.add(new BarEntry(3f, data.getNumOfTickets().get("FOURTH_WEEK")));
        entries.add(new BarEntry(4f, data.getNumOfTickets().get("FIFTH_WEEK")));

        BarDataSet dataSet = new BarDataSet(entries, "Số vé bán được");
        dataSet.setColors(Color.WHITE);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setBarBorderColor(Color.WHITE);

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.4f);

        // Thiết lập biểu đồ
        barChartViewDisplayMonthNumOfSaleTicket.setData(data);
        barChartViewDisplayMonthNumOfSaleTicket.setFitBars(true);
        barChartViewDisplayMonthNumOfSaleTicket.setDescription(null);
        barChartViewDisplayMonthNumOfSaleTicket.animateXY(1000, 1000);
        // Cho phép cuộn và thu phóng
        barChartViewDisplayMonthNumOfSaleTicket.setDragEnabled(true);
        barChartViewDisplayMonthNumOfSaleTicket.setScaleEnabled(true);
        barChartViewDisplayMonthNumOfSaleTicket.setPinchZoom(true);

        barChartViewDisplayMonthNumOfSaleTicket.invalidate(); // Refresh chart

        // Cấu hình trục X
        XAxis xAxis = barChartViewDisplayMonthNumOfSaleTicket.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.length);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        // Cấu hình trục Y
        YAxis leftAxis = barChartViewDisplayMonthNumOfSaleTicket.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setTextColor(Color.WHITE);

        YAxis rightAxis = barChartViewDisplayMonthNumOfSaleTicket.getAxisRight();
        rightAxis.setDrawGridLines(false);

        // Vô hiệu hóa biểu đồ bên phải
        rightAxis.setEnabled(false);
    }

    private void createLineChart() {
        // Dữ liệu cho biểu đồ đường
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0f, data.getNumOfTickets().get("FIRST_WEEK")));
        entries.add(new Entry(1f, data.getNumOfTickets().get("SECOND_WEEK")));
        entries.add(new Entry(2f, data.getNumOfTickets().get("THIRD_WEEK")));
        entries.add(new Entry(3f, data.getNumOfTickets().get("FOURTH_WEEK")));
        entries.add(new Entry(4f, data.getNumOfTickets().get("FIFTH_WEEK")));

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
        lineChartDisplayMonthRevenueReport.setData(data);
        lineChartDisplayMonthRevenueReport.setDescription(null);
        lineChartDisplayMonthRevenueReport.animateXY(1000, 1000);
        // Cho phép cuộn và thu phóng
        lineChartDisplayMonthRevenueReport.setDragEnabled(true);
        lineChartDisplayMonthRevenueReport.setScaleEnabled(true);
        lineChartDisplayMonthRevenueReport.setPinchZoom(true);

        lineChartDisplayMonthRevenueReport.invalidate(); // Refresh chart

        // Cấu hình trục X
        XAxis xAxis = lineChartDisplayMonthRevenueReport.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.length);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        // Cấu hình trục Y
        YAxis leftAxis = lineChartDisplayMonthRevenueReport.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setTextColor(Color.WHITE);

        YAxis rightAxis = lineChartDisplayMonthRevenueReport.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setEnabled(false); // Vô hiệu hóa trục Y bên phải
    }
}