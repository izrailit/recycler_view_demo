package com.futurice.recyclerviewdemo;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by Vera Izrailit on 11/03/15.
 */
public class ImageCardAdapter extends RecyclerView.Adapter<ImageCardAdapter.ViewHolder> {

    List<Uri> images;
    private Context mContext;

    public ImageCardAdapter(Context context, List<Uri> images) {
        this.mContext = context;
        this.images = images;
    }

    @Override
    public ImageCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ImageCardAdapter.ViewHolder holder, final int position) {
        Uri imageUri = images.get(position);
        int width = ((position % 4) + 1) * 100;
        if (imageUri == null)
            return;
        try {
            File imageFile = new File(imageUri.getPath());
            Picasso.with(mContext)
                    .load(imageFile)
                    .resize(width, width)
                    .centerCrop()
                    .into(holder.imageView);
            holder.itemView.setTag(imageUri);
        } catch (Exception e) {
            Log.d("Adapter", e.getMessage());
        }
        holder.textView.setText("Image at position " + position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    images = ImageProvider.getImages(mContext, 30);
                    notifyDataSetChanged();
                } else {
                    int currentPosition = images.indexOf((Uri) holder.itemView.getTag());
                    images.remove(currentPosition);
                    notifyItemRemoved(currentPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imageView;
        public final TextView textView;


        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image);
            textView = (TextView) view.findViewById(R.id.text);

        }

    }

}
