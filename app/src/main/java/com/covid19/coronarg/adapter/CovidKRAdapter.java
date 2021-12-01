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
import com.covid19.coronarg.model.CovidKR;

import java.util.List;


public class CovidKRAdapter extends RecyclerView.Adapter<CovidKRAdapter.ViewHolder> {

    private Context mContext;
    private List<CovidKR> covidList;

    public CovidKRAdapter(Context mContext, List<CovidKR> covidList) {
        this.mContext = mContext;
        this.covidList = covidList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CovidKR covidkr = covidList.get(position);
        holder.imageView.setImageResource(covidkr.getImage());
        holder.textView.setText(covidkr.getText());
        holder.textView2.setText(covidkr.getText2());
        holder.textView3.setText(covidkr.getText3());
        holder.textView4.setText(covidkr.getText4());
        holder.textView5.setText(covidkr.getText5());
        holder.textView6.setText(covidkr.getText6());
        holder.textView7.setText(covidkr.getText7());
    }

    @Override
    public int getItemCount() {
        return covidList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView, textView2, textView3, textView4, textView5, textView6, textView7;


        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.sidoImageId);
            textView = itemView.findViewById(R.id.sidoTextViewId);
            textView2 = itemView.findViewById(R.id.defCntTextViewId); //확진자
            textView3 = itemView.findViewById(R.id.incDecTextViewId); //전일대비
            textView4 = itemView.findViewById(R.id.deathCntTextViewId); //사망자
            textView5 = itemView.findViewById(R.id.isolIngCntTextViewId); //격리중
            textView6 = itemView.findViewById(R.id.overFlowCntTextViewId); //해외유입
            textView7 = itemView.findViewById(R.id.isolClearCntTextViewId); //격리해제
        }
    }
}