package com.covid19.coronarg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.covid19.coronarg.R;
import com.covid19.coronarg.model.CovidVC2;

import java.util.List;

public class CovidVC2Adapter extends RecyclerView.Adapter<CovidVC2Adapter.ViewHolder> {

    private Context mContext;
    private List<CovidVC2> covidVC2List;

    public CovidVC2Adapter(Context mContext, List<CovidVC2> covidVC2List) {
        this.mContext = mContext;
        this.covidVC2List = covidVC2List;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_item2_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CovidVC2 vc2 = covidVC2List.get(position);

        holder.imageView.setImageResource(vc2.getImage());
        holder.textView.setText(vc2.getText());
        holder.textView2.setText(vc2.getText2());
        holder.textView3.setText(vc2.getText3());
        holder.textView4.setText(vc2.getText4());
        holder.textView5.setText(vc2.getText5());
    }

    @Override
    public int getItemCount() {
        return covidVC2List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView, textView2, textView3, textView4, textView5;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.sidoImageId);
            textView = itemView.findViewById(R.id.sidoTextViewId);
            textView2 = itemView.findViewById(R.id.accumulate1TextViewId);
            textView3 = itemView.findViewById(R.id.new1TextViewId);
            textView4 = itemView.findViewById(R.id.accumulate2TextViewId);
            textView5 = itemView.findViewById(R.id.new2TextViewId);
        }
    }
}