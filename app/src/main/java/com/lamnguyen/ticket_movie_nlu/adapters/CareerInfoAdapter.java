package com.lamnguyen.ticket_movie_nlu.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.bean.CareerInfo;

import java.util.ArrayList;
import java.util.List;

public class CareerInfoAdapter extends RecyclerView.Adapter<CareerInfoAdapter.CareerInfoViewHolder> {

    private List<CareerInfo> listCareerInfo;

    public void setData(List<CareerInfo> listCareerInfo){
        this.listCareerInfo = listCareerInfo;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CareerInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_career_info, parent, false);
        CareerInfoViewHolder viewHolder = new CareerInfoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CareerInfoViewHolder holder, int position) {
        CareerInfo careerInfo = listCareerInfo.get(position);
        if(careerInfo == null){
            return;
        }
        holder.careerTv.setText(careerInfo.getCareer());
        holder.nameTv.setText(careerInfo.getName());
    }

    @Override
    public int getItemCount() {
        if(listCareerInfo != null){
            return listCareerInfo.size();
        }
        return 0;
    }

    public class CareerInfoViewHolder extends RecyclerView.ViewHolder{

        private TextView careerTv;
        private TextView nameTv;

        public CareerInfoViewHolder(@NonNull View itemView) {
            super(itemView);

            careerTv = itemView.findViewById(R.id.career_tv);
            nameTv = itemView.findViewById(R.id.name_tv);
        }
    }
}
