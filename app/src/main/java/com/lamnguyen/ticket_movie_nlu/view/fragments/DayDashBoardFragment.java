package com.lamnguyen.ticket_movie_nlu.view.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.lamnguyen.ticket_movie_nlu.response.DashboardResponse;

import java.util.ArrayList;
import java.util.List;

public class DayDashBoardFragment extends Fragment {
    private BarChart barChartViewDisplayDayNumOfSaleTicket;
    private LineChart lineChartDisplayDayRevenueReport;
    private TextView txtViewSumOfSaleTicket, txtViewSumOfRevenue;
    private DashboardResponse data;
    private String[] dayLabels = {"Sáng", "Trưa", "Chiều", "Tối", "Đêm"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getParentFragmentManager().setFragmentResultListener(getClass().getSimpleName(), this, (requestKey, result) -> {
            data = (DashboardResponse) result.getSerializable("data");
            createLineChart();
            createBarChart();
            showTotal();
        });

        return inflater.inflate(R.layout.fragment_day_dash_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        barChartViewDisplayDayNumOfSaleTicket = view.findViewById(R.id.bar_chart_view_display_day_sale_ticket);
        lineChartDisplayDayRevenueReport = view.findViewById(R.id.any_chart_view_display_day_revenu_report);
        txtViewSumOfSaleTicket = view.findViewById(R.id.text_view_sum_sale_ticket_day);
        txtViewSumOfRevenue = view.findViewById(R.id.text_view_sum_revenu_day);

        if (getArguments() != null) {
            data = (DashboardResponse) getArguments().getSerializable("data") == null ? new DashboardResponse() : (DashboardResponse) getArguments().getSerializable("data");
        }
    }

    private void showTotal() {
        txtViewSumOfSaleTicket.setText(String.valueOf(data.getTotalNumOfTickets()));
        txtViewSumOfRevenue.setText(String.valueOf(data.getTotalRevenue()));
    }

    private void createBarChart() {
        double morning = data.getRevenue().get("MORNING") == null ? 0 : data.getNumOfTickets().get("MORNING");
        double noon = data.getRevenue().get("NOON") == null ? 0 : data.getNumOfTickets().get("NOON");
        double afternoon = data.getRevenue().get("AFTERNOON") == null ? 0 : data.getNumOfTickets().get("AFTERNOON");
        double evening = data.getRevenue().get("EVENING") == null ? 0 : data.getNumOfTickets().get("EVENING");
        double night = data.getRevenue().get("NIGHT") == null ? 0 : data.getNumOfTickets().get("NIGHT");
        // Dữ liệu cho biểu đồ cột
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, (float) morning));
        entries.add(new BarEntry(1f, (float) noon));
        entries.add(new BarEntry(2f, (float) afternoon));
        entries.add(new BarEntry(3f, (float) evening));
        entries.add(new BarEntry(4f, (float) night));

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
        xAxis.setLabelCount(dayLabels.length);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dayLabels));

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
        double morning = data.getRevenue().get("MORNING") == null ? 0 : data.getRevenue().get("MORNING");
        double noon = data.getRevenue().get("NOON") == null ? 0 : data.getRevenue().get("NOON");
        double afternoon = data.getRevenue().get("AFTERNOON") == null ? 0 : data.getRevenue().get("AFTERNOON");
        double evening = data.getRevenue().get("EVENING") == null ? 0 : data.getRevenue().get("EVENING");
        double night = data.getRevenue().get("NIGHT") == null ? 0 : data.getRevenue().get("NIGHT");

        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0f, (float) morning));
        entries.add(new Entry(1f, (float) noon));
        entries.add(new Entry(2f, (float) afternoon));
        entries.add(new Entry(3f, (float) evening));
        entries.add(new Entry(4f, (float) night));

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
        xAxis.setLabelCount(dayLabels.length);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dayLabels));

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