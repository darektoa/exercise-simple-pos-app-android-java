package com.example.simplepos.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplepos.HistoryAdapter;
import com.example.simplepos.R;
import com.example.simplepos.viewmodel.ProductViewModel;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private TextView emptyText;
    private ProductViewModel productViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_history);
        emptyText = view.findViewById(R.id.text_history_empty);
        view.findViewById(R.id.btn_generate_report).setOnClickListener(v -> generateReport());
        
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.getAllOrders().observe(getViewLifecycleOwner(), orders -> {
            if (orders == null || orders.isEmpty()) {
                emptyText.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                emptyText.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                adapter = new HistoryAdapter(orders);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    private void generateReport() {
        new com.example.simplepos.background.legacy.ReportGenerationTask(getContext()).execute();
    }
}
