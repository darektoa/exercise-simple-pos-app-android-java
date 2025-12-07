package com.example.simplepos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplepos.model.Order;
import com.example.simplepos.utils.CurrencyUtils;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<Order> orderList;

    public HistoryAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.orderId.setText("Order #" + order.getId());
        holder.date.setText(order.getDate());
        holder.total.setText(CurrencyUtils.toRupiah(order.getTotalPrice()));
        holder.summary.setText(order.getItemsSummary());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView orderId, date, total, summary;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.text_order_id);
            date = itemView.findViewById(R.id.text_order_date);
            total = itemView.findViewById(R.id.text_order_total);
            summary = itemView.findViewById(R.id.text_order_summary);
        }
    }
}
