package com.relinns.viegram.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.relinns.viegram.Activity.Another_user;
import com.relinns.viegram.Activity.Profile;
import com.relinns.viegram.Modal.LikeComment;
import com.relinns.viegram.R;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class NameAdapter extends RecyclerView.Adapter<NameAdapter.ViewHolder> {
    private Context context;
    private List<LikeComment> list;
    private SharedPreferences preferences;

    public NameAdapter(Context activity, List<LikeComment> list_data) {
        this.context = activity;
        this.list = list_data;
        preferences = context.getSharedPreferences("Viegram", Context.MODE_PRIVATE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.design_results_name, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        LikeComment item = list.get(position);

        DisplayImageOptions options = new DisplayImageOptions.Builder().
                cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();

        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage(item.getProfileImage() , holder.display_image , options);

        holder.name_text.setText(item.getDisplayName());


       // holder.name_text.setText(list.get(position).getDisplayName());
       /* Glide.with(context).load(list.get(position).getProfileImage())
                .bitmapTransform(new CropCircleTransformation(context))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.display_image);*/
        holder.name_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_profile(position);
            }
        });
        holder.display_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_profile(position);
            }
        });
    }

    private void open_profile(int position) {
        if (list.get(position).getId().equals(preferences.getString("user_id", ""))) {
            Intent intent = new Intent(context, Profile.class);
            context.startActivity(intent);
        } else {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("another_user", list.get(position).getId());
            editor.commit();
            Intent intent = new Intent(context, Another_user.class);
            context.startActivity(intent);
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView display_image;
        TextView name_text;

        public ViewHolder(View itemView) {
            super(itemView);
            name_text = (TextView) itemView.findViewById(R.id.name_text);
            display_image = (ImageView) itemView.findViewById(R.id.display_image);

        }
    }
}
