package com.court.app.ui.videos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.court.app.R;
import com.court.app.data.model.Video;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    public interface OnVideoClickListener {
        void onVideoClick(Video video);
    }

    private List<Video> videos = new ArrayList<>();
    private final OnVideoClickListener listener;

    public VideoAdapter(OnVideoClickListener listener) {
        this.listener = listener;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video video = videos.get(position);

        holder.tvTitulo.setText(video.getTitulo());
        holder.tvCategoria.setText(video.getCategoria().toUpperCase());

        // Formatear duración
        int min = video.getDuracionSeg() / 60;
        int seg = video.getDuracionSeg() % 60;
        holder.tvDuracion.setText(String.format("%d:%02d", min, seg));

        holder.card.setOnClickListener(v -> listener.onVideoClick(video));
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView card;
        TextView tvTitulo, tvCategoria, tvDuracion;

        VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            card        = itemView.findViewById(R.id.card_video);
            tvTitulo    = itemView.findViewById(R.id.tv_titulo_video);
            tvCategoria = itemView.findViewById(R.id.tv_categoria_video);
            tvDuracion  = itemView.findViewById(R.id.tv_duracion_video);
        }
    }
}
