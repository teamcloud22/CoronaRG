package com.covid19.coronarg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.covid19.coronarg.R;
import com.covid19.coronarg.model.CovidFC;

import java.util.List;


public class CovidFCAdapter extends RecyclerView.Adapter<CovidFCAdapter.ViewHolder> {

    private Context mContext;
    private List<CovidFC> covidfcList;

    public CovidFCAdapter(Context mContext, List<CovidFC> covidfcList) {
        this.mContext = mContext;
        this.covidfcList = covidfcList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CovidFC covidfc = covidfcList.get(position);

        holder.textView.setText(covidfc.getText());
        holder.textView2.setText(covidfc.getText2());
        holder.textView3.setText(covidfc.getText3());
    }

    @Override
    public int getItemCount() {
        return covidfcList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView, textView2, textView3;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.worldTextViewId);
            textView2 = itemView.findViewById(R.id.natDefCntTextViewId); //누적확진자
            textView3 = itemView.findViewById(R.id.natDeathCntTextViewId); //사망률(수)
        }
    }
}