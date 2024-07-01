package com.lamnguyen.ticket_movie_nlu.view.fragments.admin;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.TimeoutError;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.adapters.PriceManageAdapter;
import com.lamnguyen.ticket_movie_nlu.api.PriceManageApi;
import com.lamnguyen.ticket_movie_nlu.dto.PriceManageDTO;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;
import com.lamnguyen.ticket_movie_nlu.utils.DialogLoading;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PriceManagerFragment extends Fragment {
    private RecyclerView rcvPriceManage;
    private PriceManageAdapter priceManageAdapter;
    private Dialog dialogLoading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_price_manager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.init(view);

        fetchPriceData();
    }

    private void init(View view) {
        rcvPriceManage = view.findViewById(R.id.recycler_view_display_price_manage);
        priceManageAdapter = new PriceManageAdapter(getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        rcvPriceManage.setLayoutManager(gridLayoutManager);
        rcvPriceManage.setAdapter(priceManageAdapter);
        dialogLoading = DialogLoading.newInstance(getContext());
    }

    private void fetchPriceData() {
        DialogLoading.showDialogLoading(dialogLoading, getString(R.string.loading));
        PriceManageApi.getPriceManageList(getContext(), new CallAPI.CallAPIListener<>() {

            @Override
            public void completed(List<PriceManageDTO> priceManageDTOList) {
                dialogLoading.dismiss();
                priceManageAdapter.setData(priceManageDTOList);
            }

            @Override
            public void error(Object error) {
                dialogLoading.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError)
                    Toast.makeText(getContext(), getString(R.string.error_server), Toast.LENGTH_SHORT).show();
            }
        });
    }
}