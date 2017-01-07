package com.it.nhozip.englisha_z.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.it.nhozip.englisha_z.Model.EL;
import com.it.nhozip.englisha_z.R;

import java.util.ArrayList;

/**
 * Created by huyen on 1/1/2017.
 */
public class AdapterEL extends RecyclerView.Adapter<AdapterEL.RecyclerViewHolder>{
    private ArrayList<EL> _arrExamplan;
    private Context _context;

    public AdapterEL(ArrayList<EL> examResults, Context context) {
        this._arrExamplan = examResults;
        this._context = context;

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_el, parent, false);

        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {

        holder.txtEL.setText(_arrExamplan.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


    }

    @Override
    public int getItemCount() {
        return _arrExamplan.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView txtEL;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtEL = (TextView) itemView.findViewById(R.id.txtEL);


        }
    }
}

