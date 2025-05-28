package com.example.emsismartpresence;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ReclamationAdapter extends RecyclerView.Adapter<ReclamationAdapter.ViewHolder> {
    public interface OnEditClickListener {
        void onEdit(Reclamation reclamation);
    }

    private List<Reclamation> reclamations;
    private OnEditClickListener listener;

    public ReclamationAdapter(List<Reclamation> reclamations, OnEditClickListener listener) {
        this.reclamations = reclamations;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessage, tvEdit;
        public ViewHolder(View v) {
            super(v);
            tvMessage = v.findViewById(R.id.tvMessage);
            tvEdit = v.findViewById(R.id.tvEdit);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reclamation, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Reclamation r = reclamations.get(position);
        holder.tvMessage.setText(r.message);
        holder.tvEdit.setOnClickListener(v -> listener.onEdit(r));
    }

    @Override
    public int getItemCount() {
        return reclamations.size();
    }
}