package com.nupuit.vetmed.adapter;

/**
 * Created by USER on 18-May-17.
 */

import android.content.Context;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nupuit.vetmed.R;

import java.util.ArrayList;

public class AdapterSpinner extends ArrayAdapter<String> {
    Context context;
    ArrayList<String> numbers;
    LayoutInflater inflter;

    public AdapterSpinner(Context context,  ArrayList<String> numbers) {
        super(context,R.layout.dropdown1);
        this.context = context;
        this.numbers = numbers;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return numbers.size();
    }


    @Nullable
    @Override
    public String getItem(int position) {
        return numbers.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.dropdown, null);
        TextView names = (TextView) view.findViewById(R.id.textView);
        names.setText(numbers.get(i)+"");
        return view;
    }
}
