package com.example.workerlist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class AddWorkerDialog extends DialogFragment {

    private AddWorkerListener listener;

    public interface AddWorkerListener {
        void onWorkerAdded(Worker worker);
    }

    // Новый способ установки listener вместо setTargetFragment()
    public void setListener(AddWorkerListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_worker, null);

        EditText idInput = view.findViewById(R.id.idEditText);
        EditText nameInput = view.findViewById(R.id.nameEditText);
        EditText gradeInput = view.findViewById(R.id.gradeEditText);
        EditText rateInput = view.findViewById(R.id.rateEditText);

        return new AlertDialog.Builder(requireContext())
                .setView(view)
                .setTitle("Добавить работника")
                .setPositiveButton("Добавить", (dialog, which) -> {
                    String id = idInput.getText().toString().trim();
                    String name = nameInput.getText().toString().trim();
                    String grade = gradeInput.getText().toString().trim();
                    String rateText = rateInput.getText().toString().trim();

                    if (listener != null && !id.isEmpty() && !name.isEmpty() && !grade.isEmpty() && !rateText.isEmpty()) {
                        try {
                            double rate = Double.parseDouble(rateText);
                            Worker worker = new Worker(id, name, grade, rate);
                            listener.onWorkerAdded(worker);
                        } catch (NumberFormatException e) {
                            Toast.makeText(getContext(), "Некорректная ставка", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Отмена", null)
                .create();
    }
}
