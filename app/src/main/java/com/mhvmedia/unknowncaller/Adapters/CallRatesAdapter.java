package com.mhvmedia.unknowncaller.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mhvmedia.unknowncaller.Model.ModelCallRates;
import com.mhvmedia.unknowncaller.R;

import java.util.List;
/** Created by AwsmCreators * */
public class CallRatesAdapter extends RecyclerView.Adapter<CallRatesAdapter.MyViewHolder> {
    private final List<ModelCallRates> mylist;
    private final Context context;
    private final OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick (ModelCallRates model);
    }

    public CallRatesAdapter(List<ModelCallRates> mylist, Context context, OnItemClickListener listener) {
        this.mylist = mylist;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_callrates,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ModelCallRates model = mylist.get(position);
        holder.icon.setImageDrawable(context.getResources().getDrawable(model.getCountryicon()));
        holder.blind(mylist.get(position),listener);

    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView icon;
        TextView name;
        TextView cost;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.icon);
            name = itemView.findViewById(R.id.name);
            cost = itemView.findViewById(R.id.cost);
        }

        public void blind (final ModelCallRates model, final OnItemClickListener listener){
            //Model model = mylist.get(position);
            name.setText(model.getCountryname());
            cost.setText(model.getCost());
            //Picasso.get().load(model.getImage()).into(holder.gift);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(model);
                }
            });
        }
    }
}
