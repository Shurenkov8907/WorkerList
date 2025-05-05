package com.example.workerlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class WorkerViewModel extends ViewModel {
    private MutableLiveData<List<Worker>> workers = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<Worker>> getWorkers() {
        return workers;
    }

    public void addWorker(Worker worker) {
        List<Worker> current = workers.getValue();
        current.add(worker);
        workers.setValue(current);
    }

    public void removeWorker(int position) {
        List<Worker> current = workers.getValue();
        current.remove(position);
        workers.setValue(current);
    }
}

