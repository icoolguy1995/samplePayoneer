package com.example.samplepayoneer.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.samplepayoneer.Models.ModelListOfPaymentOptions;
import com.example.samplepayoneer.R;

import java.util.ArrayList;

public class RecyclerAdaptor extends RecyclerView.Adapter<RecyclerAdaptor.ViewHolder> {

    private ArrayList<ModelListOfPaymentOptions> paymentOptions;
    private Context context;


    public RecyclerAdaptor (ArrayList<ModelListOfPaymentOptions> paymentOptions, Context context){
        this.paymentOptions = paymentOptions;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdaptor.ViewHolder holder, int position) {
        ModelListOfPaymentOptions payOps = paymentOptions.get(position);
        holder.tvName.setText(payOps.getFullName());
        Glide.with(context).load(payOps.getModelCardInfo().getLogo()).into(holder.img);


    }

    @Override
    public int getItemCount() {
        return paymentOptions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.text);
            img =   itemView.findViewById(R.id.icon);
        }
    }
}
