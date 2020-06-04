package com.andy_dev.practicaltask.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.andy_dev.practicaltask.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> arrayList;
    private Typeface font = ResourcesCompat.getFont(getContext(), R.font.poppins_regular);

    public CustomSpinnerAdapter(@NonNull Context context, int resource, ArrayList<String> arrayList) {
        super(context, resource, arrayList);
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public View getView(int position, View recycle, @NonNull ViewGroup container) {
        TextView textView = (TextView) super.getView(position, recycle, container);
        int color = position == 0 ? ContextCompat.getColor(context, R.color.hint_color) : Color.BLACK;
        textView.setTextColor(color);
        textView.setTextSize(15);
        textView.setTypeface(font);

        textView.setText(arrayList.get(position));

        return textView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
        int color = position == 0 ? ContextCompat.getColor(context, R.color.hint_color) : Color.BLACK;
        textView.setTextColor(color);
        textView.setTypeface(font);
        textView.setTextSize(15);

        textView.setText(arrayList.get(position));
        return textView;
    }

    @Override
    public boolean isEnabled(int position) {
        // TODO Auto-generated method stub
        return position > 0;
    }
}
