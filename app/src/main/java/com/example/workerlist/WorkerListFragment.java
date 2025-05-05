package com.example.workerlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class WorkerListFragment extends Fragment implements AddWorkerDialog.AddWorkerListener {

    private WorkerViewModel viewModel;
    private WorkerAdapter adapter;
    private TextView emptyView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(WorkerViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_worker_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        emptyView = view.findViewById(R.id.emptyView);
        FloatingActionButton fab = view.findViewById(R.id.fab);

        adapter = new WorkerAdapter(viewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        viewModel.getWorkers().observe(getViewLifecycleOwner(), workers -> {
            adapter.setWorkers(workers);
            emptyView.setVisibility(workers.isEmpty() ? View.VISIBLE : View.GONE);
        });

        fab.setOnClickListener(v -> showAddDialog());

        return view;
    }

    private void showAddDialog() {
        AddWorkerDialog dialog = new AddWorkerDialog();
        dialog.setListener(this); // Новый способ установки listener
        dialog.show(getParentFragmentManager(), "AddWorkerDialog");
    }

    @Override
    public void onWorkerAdded(Worker worker) {
        viewModel.addWorker(worker);
    }

    // Статический внутренний адаптер (избегает утечек памяти)
    private static class WorkerAdapter extends RecyclerView.Adapter<WorkerAdapter.ViewHolder> {
        private final List<Worker> workers = new ArrayList<>();
        private final WorkerViewModel viewModel;

        WorkerAdapter(WorkerViewModel viewModel) {
            this.viewModel = viewModel;
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView infoView;
            Button deleteButton;

            ViewHolder(View itemView) {
                super(itemView);
                infoView = itemView.findViewById(R.id.workerTextView);
                deleteButton = itemView.findViewById(R.id.deleteButton);
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_worker, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Worker worker = workers.get(position);
            holder.infoView.setText(worker.getIdNumber() + " | " +
                    worker.getFullName() + " | " +
                    worker.getGrade() + " | " +
                    worker.getHourlyRate() + "₽/ч");

            holder.deleteButton.setOnClickListener(v ->
                    viewModel.removeWorker(holder.getAdapterPosition()));
        }

        @Override
        public int getItemCount() {
            return workers.size();
        }

        void setWorkers(List<Worker> newWorkers) {
            workers.clear();
            workers.addAll(newWorkers);
            notifyDataSetChanged();
        }
    }
}
