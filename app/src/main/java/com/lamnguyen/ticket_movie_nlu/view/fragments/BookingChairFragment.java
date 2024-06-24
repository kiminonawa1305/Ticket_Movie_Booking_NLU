package com.lamnguyen.ticket_movie_nlu.view.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.adapters.ChairAdapter;
import com.lamnguyen.ticket_movie_nlu.dto.ChairDTO;
import com.lamnguyen.ticket_movie_nlu.enums.ChairStatus;
import com.lamnguyen.ticket_movie_nlu.response.ChairResponse;
import com.lamnguyen.ticket_movie_nlu.service.chair.ChairService;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;
import com.lamnguyen.ticket_movie_nlu.utils.DialogLoading;
import com.lamnguyen.ticket_movie_nlu.utils.SharedPreferencesUtils;
import com.lamnguyen.ticket_movie_nlu.view.activities.BookingChairActivity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BookingChairFragment extends Fragment {
    private static final String TAG = BookingChairFragment.class.getSimpleName();
    private Dialog dialog;
    private int showtimeiId;
    private ChairService chairService;
    private String uuid;
    private GridView gridView;
    private RecyclerView recyclerView;
    private List<ChairDTO> dtos;
    private ChairAdapter chairAdapter;
    private List<ChairDTO> listChairSelect;
    private boolean connect = false;
    private Integer userId;
    private boolean back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getParentFragmentManager().setFragmentResultListener(BookingChairActivity.class.getSimpleName(), this, (key, bundle) -> {
            showtimeiId = bundle.getInt("showtimeId");
            back = bundle.getBoolean("back");
        });
        chairService = ChairService.getInstance();
        userId = SharedPreferencesUtils.getUserID(this.getContext());
        return inflater.inflate(R.layout.fragment_seat_item_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view_seats);
        dialog = new Dialog(this.getContext());
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(false);
        listChairSelect = new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();

        DialogLoading.showDialogLoading(dialog, getString(R.string.loading));
        chairService.loadChair(showtimeiId, this.getContext(), new CallAPI.CallAPIListener<ChairResponse>() {
            @Override
            public void completed(ChairResponse chairResponse) {
                dialog.dismiss();
                uuid = chairResponse.getUuid();
                if (!connect)
                    openConnect(uuid);

                Bundle bundle = new Bundle();
                bundle.putSerializable("price", chairResponse.getPrice());
                getParentFragmentManager().setFragmentResult(BookingChairFragment.class.getSimpleName(), bundle);


                dtos = chairResponse.getChairs();
                setChair(dtos);
            }

            @Override
            public void error(Object error) {
                dialog.dismiss();
            }
        });
    }

    private void setChair(List<ChairDTO> dtos) {
        listChairSelect.clear();
        dtos.forEach(chair -> {
            if (chair.getUserId() != null)
                if (chair.getStatus().equals(ChairStatus.SELECTED))
                    if (!chair.getUserId().equals(userId))
                        chair.setStatus(ChairStatus.OTHER_SELECTED);
                    else {
                        listChairSelect.add(chair);
                        sendChairSelected("add", chair);
                    }
        });


        int col = getCol(dtos);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(BookingChairFragment.this.getContext(), col);
        chairAdapter = new ChairAdapter(BookingChairFragment.this.getContext(), dtos, uuid);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(chairAdapter);
    }

    private void openConnect(String uuid) {
        chairService.openConnectDatabase(uuid, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!connect) {
                    connect = true;
                    return;
                }

                updateStatusHandle(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onStop() {
        super.onStop();

        listChairSelect.forEach(chair -> chairService.rollBack(uuid, chair.getId(), ChairStatus.AVAILABLE, BookingChairFragment.this.getContext()));
    }

    private int getCol(List<ChairDTO> dtos) {
        dtos.sort(Comparator.comparingInt(ChairDTO::getId));
        String colStr = dtos.get(dtos.size() - 1).getName().substring(1);
        return Integer.parseInt(colStr);
    }

    private void updateStatusHandle(DataSnapshot snapshot) {
        ChairDTO chairDTO = new Gson().fromJson(snapshot.getValue().toString(), ChairDTO.class);

        dtos.stream().filter(chair -> chair.getId().equals(chairDTO.getId()))
                .forEach(chair -> {
                    chair.setUserId(chairDTO.getUserId());

                    if (!chairDTO.getUserId().equals(userId) && chairDTO.getStatus().equals(ChairStatus.SELECTED)) {
                        chair.setStatus(ChairStatus.OTHER_SELECTED);
                    } else chair.setStatus(chairDTO.getStatus());
                });


        chairAdapter.notifyDataSetChanged();
        if (!chairDTO.getUserId().equals(userId))
            return;


        String key;
        if (listChairSelect.contains(chairDTO)) {
            listChairSelect.remove(chairDTO);
            key = "remove";
        } else {
            listChairSelect.add(chairDTO);
            key = "add";
        }
        sendChairSelected(key, chairDTO);
    }

    private void sendChairSelected(String key, ChairDTO chairDTO) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(key, chairDTO);
        if (back) return;
        getParentFragmentManager().setFragmentResult(BookingChairFragment.class.getSimpleName(), bundle);
    }
}
