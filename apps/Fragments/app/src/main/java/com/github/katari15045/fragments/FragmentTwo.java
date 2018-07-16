package com.github.katari15045.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Saketh Katari on 22-02-2018.
 */

public class FragmentTwo extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d("SAK : ", "frag_2 -> onCreateView()");
        return inflater.inflate(R.layout.fragment_two, container, false);
    }
}
