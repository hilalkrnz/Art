package com.example.artbook.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.artbook.model.Art;
import com.example.artbook.util.ArtItemListener;
import com.example.artbook.databinding.LayoutArtItemBinding;
import java.util.ArrayList;

public class ArtAdapter extends RecyclerView.Adapter<ArtAdapter.ArtViewHolder> {

    ArrayList<Art> artArrayList;
    private final ArtItemListener artItemListener;

    public ArtAdapter(ArrayList<Art> artArrayList, ArtItemListener listener) {
        this.artArrayList = artArrayList;
        this.artItemListener = listener;
    }

    @NonNull
    @Override
    public ArtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutArtItemBinding binding = LayoutArtItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ArtViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtViewHolder holder, int position) {
        holder.binding.artTitle.setText(artArrayList.get(position).getArtTitle());
        holder.binding.paintType.setText(artArrayList.get(position).getArtPaintType());
        holder.binding.artCaption.setText(artArrayList.get(position).getArtCaption());
        holder.binding.artImage.setImageBitmap(artArrayList.get(position).getImage());
        holder.binding.imageDelete.setOnClickListener(v -> {
            int positionItem = holder.getAdapterPosition();
            if (positionItem != RecyclerView.NO_POSITION) {
                Art art = artArrayList.get(positionItem);
                int artBookId = art.getId();
                artItemListener.onArtItemDeleteClicked(artBookId);
                artArrayList.remove(positionItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return artArrayList.size();
    }

    public static class ArtViewHolder extends RecyclerView.ViewHolder {
        private final LayoutArtItemBinding binding;

        public ArtViewHolder(LayoutArtItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
