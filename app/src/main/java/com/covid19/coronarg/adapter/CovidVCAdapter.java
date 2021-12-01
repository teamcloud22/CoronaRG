package com.covid19.coronarg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.covid19.coronarg.R;
import com.covid19.coronarg.model.CovidVC;

import java.util.List;

public class CovidVCAdapter extends RecyclerView.Adapter<CovidVCAdapter.ViewHolder> {

    private Context mContext;
    private List<CovidVC> covidVCList;

    public CovidVCAdapter(Context mContext, List<CovidVC> covidVCList) {
        this.mContext = mContext;
        this.covidVCList = covidVCList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_item2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CovidVC vc = covidVCList.get(position);
        holder.textView.setText(vc.getText());
        holder.textView2.setText(vc.getText2());
        holder.textView3.setText(vc.getText3());
        holder.textView4.setText(vc.getText4());
    }

    @Override
    public int getItemCount() {
        return covidVCList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView, textView2, textView3, textView4;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.vaccineTextViewId);
            textView2 = itemView.findViewById(R.id.percentTextViewId);
            textView3 = itemView.findViewById(R.id.totalTextViewId);
            textView4 = itemView.findViewById(R.id.newTextViewId);
        }
    }
}