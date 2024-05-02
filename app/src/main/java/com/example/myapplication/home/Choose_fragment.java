package com.example.myapplication.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class Choose_fragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    CardView KhatkhaCard;
    CardView WimCard;
    CardView NoneCard;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout._choose_fragment, container, false);
        KhatkhaCard=view.findViewById(R.id.KhatkhaCard);
        WimCard=view.findViewById(R.id.WimCard);
        NoneCard=view.findViewById(R.id.NoneCard);
        KhatkhaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClickedKhatkha();
            }
        });
        WimCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClickedWim();
            }
        });
        NoneCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClickedNone();
            }
        });
    return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }
    public interface OnFragmentInteractionListener {
        void onButtonClickedKhatkha();
        void onButtonClickedWim();
        void onButtonClickedNone();
    }
}
