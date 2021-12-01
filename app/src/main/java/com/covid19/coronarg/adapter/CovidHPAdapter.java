package com.covid19.coronarg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.covid19.coronarg.R;
import com.covid19.coronarg.model.CovidHP;

import java.util.List;

public class CovidHPAdapter extends RecyclerView.Adapter<CovidHPAdapter.ViewHolder> {

    private Context mContext;
    private List<CovidHP> covidHPList;

    public CovidHPAdapter(Context mContext, List<CovidHP> covidHPList) {
        this.mContext = mContext;
        this.covidHPList = covidHPList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_item3, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CovidHP covidHP = covidHPList.get(position);

        holder.textView.setText(covidHP.getText());
        holder.textView2.setText(covidHP.getText2());
        holder.textView3.setText(covidHP.getText3());
        holder.textView4.setText(covidHP.getText4());
        holder.textView5.setText(covidHP.getText5());

    }

    @Override
    public int getItemCount() {
        return covidHPList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView, textView2, textView3, textView4, textView5;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.orgnameTextViewId);
            textView2 = itemView.findViewById(R.id.addressTextViewId);
            textView3 = itemView.findViewById(R.id.sttTextViewId);
            textView4 = itemView.findViewById(R.id.lunchTextViewId);
            textView5 = itemView.findViewById(R.id.telTextViewId);
        }
    }
}