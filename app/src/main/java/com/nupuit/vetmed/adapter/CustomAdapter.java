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

public class CustomAdapter extends ArrayAdapter<Integer> {
    Context context;
    Integer[] numbers;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, Integer[] numbers) {
        super(applicationContext,R.layout.dropdown1);
        this.context = applicationContext;
        this.numbers = numbers;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return numbers.length;
    }


    @Nullable
    @Override
    public Integer getItem(int position) {
        return numbers[position];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.dropdown, null);
        TextView names = (TextView) view.findViewById(R.id.textView);
        names.setText(numbers[i]+"");
        return view;
    }
}
