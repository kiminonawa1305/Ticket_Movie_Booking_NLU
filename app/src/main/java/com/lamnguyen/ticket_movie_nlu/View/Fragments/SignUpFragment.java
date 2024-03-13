package com.lamnguyen.ticket_movie_nlu.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.lamnguyen.ticket_movie_nlu.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {
    private TextView tvChangeFragment;
    private FragmentManager fragmentManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        tvChangeFragment = view.findViewById(R.id.text_view_change_fragment_sign_in);
        fragmentManager = getFragmentManager();
        event();
        return view;
    }


    private void event() {
        tvChangeFragment.setOnClickListener(v -> {
            changeFragmentSignIn();
        });
    }

    private void changeFragmentSignIn() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);

        // Replace whatever is in the fragment_container view with this fragment
        fragmentTransaction.replace(R.id.fragment_sign, SignInFragment.class, null);
        fragmentTransaction.commit();
    }
}