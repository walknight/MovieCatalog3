package com.dtaoa.moviecatalog3.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dtaoa.moviecatalog3.R;
import com.dtaoa.moviecatalog3.ViewModel.DataModel;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {
    private ArrayList<DataModel> mData = new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;

    public void setData(ArrayList<DataModel> items){
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_items, viewGroup, false);
        return new DataViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataViewHolder holder, int position) {
        holder.bind(mData.get(position), holder);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(mData.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback{
        void onItemClicked(DataModel data);
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textYear, textRating, textSinopsis, textGenre;
        ImageView imgThumbnail;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.txt_title);
            textYear = itemView.findViewById(R.id.txt_year);
            textRating = itemView.findViewById(R.id.txt_rating);
            //textGenre = itemView.findViewById(R.id.txt_genre);
            imgThumbnail = itemView.findViewById(R.id.img_thumbnail);
            textSinopsis = itemView.findViewById(R.id.txt_sinopsis);
        }

        void bind(DataModel itemMovie,@NonNull DataViewHolder holder){
            String [] date = itemMovie.getYear().split("-");

            textTitle.setText(itemMovie.getTitle());
            textYear.setText(date[0]);
            textRating.setText(itemMovie.getRatings());
            textSinopsis.setText(itemMovie.getSinopsis());
            //textGenre.setText(itemMovie.getGenre());
            Glide.with(holder.itemView.getContext())
                    .load(itemMovie.getImageThumbnail())
                    .apply(new RequestOptions().override(100, 140))
                    .into(holder.imgThumbnail);
        }
    }
}
