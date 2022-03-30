package com.example.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class filteradapter extends RecyclerView.Adapter<filteradapter.cardViewHolder>{
    private ArrayList<filterdata> mlist;
    private Context context;

    public filteradapter(ArrayList<filterdata> mlist, Context context) {
        this.mlist = mlist;
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filterList(ArrayList<filterdata> filteredlist) {
        mlist = filteredlist;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public cardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        return new cardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cardViewHolder holder, int position) {
        filterdata currentitem = mlist.get(position);
        holder.textView.setText( currentitem.getText());
        holder.textView2.setText( currentitem.getText2());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class cardViewHolder extends RecyclerView.ViewHolder {

        private TextView textView,textView2;

        public cardViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.t);
            textView2 = itemView.findViewById(R.id.t2);
        }
    }

}
