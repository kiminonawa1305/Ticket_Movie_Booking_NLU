package com.lamnguyen.ticket_movie_nlu.adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.view.fragments.placeholder.PlaceholderContent.PlaceholderItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class SeatAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mSeatList;

    public SeatAdapter(Context context, List<String> seatList) {
        mContext = context;
        mSeatList = seatList;
    }

    @Override
    public int getCount() {
        return mSeatList.size();
    }

    @Override
    public Object getItem(int position) {
        return mSeatList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View seatView = convertView;
        if (seatView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            seatView = inflater.inflate(R.layout.fragment_seat_item, null);
        }

        TextView seatTextView = seatView.findViewById(R.id.seat);
        seatTextView.setText(mSeatList.get(position));

        return seatView;
    }
}