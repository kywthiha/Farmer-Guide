package com.farm.ngo.farm.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.farm.ngo.farm.Holder.AdminViewHolder;
import com.farm.ngo.farm.Model.Admin;
import com.farm.ngo.farm.R;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminViewHolder> {
    private Context context;
    private List<Admin> admins;

    public AdminAdapter(Context context, List<Admin> admins) {
        this.context = context;
        this.admins = admins;
    }

    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_item, parent, false);
        return new AdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewHolder holder, int position) {
        Admin admin=admins.get(position);
        holder.onAdminBind(admin,position);
    }

    @Override
    public int getItemCount() {
        return admins.size();
    }
}
