package com.mhvmedia.unknowncaller.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.billingclient.api.SkuDetails;
import com.mhvmedia.unknowncaller.R;
import com.mhvmedia.unknowncaller.Variables.Variables;

import java.util.List;

public class SkuAdapter extends RecyclerView.Adapter<SkuAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(SkuDetails item);
    }

    private final List<SkuDetails> items;
    private final OnItemClickListener listener;

    public SkuAdapter(List<SkuDetails> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_credits, parent, false);
        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position), listener);
    }

    @Override public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, price;
        private Button buy;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            buy = itemView.findViewById(R.id.buy);
        }

        public void bind(final SkuDetails item, final OnItemClickListener listener) {

            if (item.getSku().equals(Variables.SMALL_PACK_ID)){
                name.setText(Variables.SMALL_PACK_CREDITS+" Credits");
            }else if (item.getSku().equals(Variables.MEDIUM_PACK_ID)){
                name.setText(Variables.MEDIUM_PACK_CREDITS+" Credits");
            }else if (item.getSku().equals(Variables.BIG_PACK_ID)){
                name.setText(Variables.BIG_PACK_CREDITS+" Credits");
            }
            price.setText("Price "+item.getPrice());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    //listener.onItemClick(item);
                }
            });
            buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}