package com.lamnguyen.ticket_movie_nlu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.dto.MovieDTO;
import com.lamnguyen.ticket_movie_nlu.dto.RoomDTO;

import java.util.List;

public class RoomSpinnerAdapter extends ArrayAdapter<RoomDTO> {

    public RoomSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<RoomDTO> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_spinner, parent, false);
        TextView roomItemTextView = convertView.findViewById(R.id.text_view_selected_item_spinner);
        RoomDTO roomDTO = this.getItem(position);

        if(roomDTO != null){
            roomItemTextView.setText(roomDTO.getName());
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner, parent, false);
        TextView roomItemTextView = convertView.findViewById(R.id.text_view_item_spinner);
        RoomDTO roomDTO = this.getItem(position);

        if(roomDTO != null){
            roomItemTextView.setText(roomDTO.getName());
        }

        return convertView;
    }
}
