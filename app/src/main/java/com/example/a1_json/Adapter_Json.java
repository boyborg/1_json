package com.example.a1_json;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_Json extends RecyclerView.Adapter<Adapter_Json.charViewHolder> {
    private ArrayList<Char_> datalist;

    public Adapter_Json(ArrayList<Char_> datalist){
        this.datalist=datalist;
    }

    @Override
    public charViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.char_list,parent,false);
        return new charViewHolder(view);
    }

    @Override
    public void onBindViewHolder(charViewHolder holder,int position){
        holder.txtName.setText(datalist.get(position).getName());
        holder.txtId.setText(datalist.get(position).getId());
        holder.txtUserName.setText(datalist.get(position).getUsername());
        holder.txtEmail.setText(datalist.get(position).getEmail());
        holder.txtAddress.setText(datalist.get(position).getAddress());


    }

    @Override
    public int getItemCount() {
        return (datalist != null) ? datalist.size() : 0;

    }

    public class charViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtId,txtUserName, txtEmail,txtAddress;

        public charViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.textName);
            txtId = (TextView) itemView.findViewById(R.id.textId);
            txtUserName = (TextView) itemView.findViewById(R.id.textUserName);
            txtEmail = (TextView) itemView.findViewById(R.id.textEmail);
            txtAddress = (TextView)itemView.findViewById(R.id.textAddress);
        }
    }

}





