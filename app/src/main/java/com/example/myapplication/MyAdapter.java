package com.example.myapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    public model[] m;
    public static int view_layout;
    public MyAdapter(model[] listdata, int layout) {
        this.m = listdata;
        for(int i=0;i<this.m.length;i++){
            Log.e(i+"",this.m[i].getName());
        }
        MyAdapter.view_layout = layout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(MyAdapter.view_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.e("item",+this.m.length+"");
        model m_1 = this.m[position];
        Log.e("name",m_1.getName());
        //holder.imageView.setImageAlpha();
        holder.title.setText(m_1.getName());
        holder.slug.setText(m_1.getSlug());

        Picasso.get().load(m_1.img).into(holder.image, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(Exception e) {
                holder.image.setImageResource(R.drawable.ic_baseline_broken_image_24);
            }
        });

        if(MyAdapter.view_layout != R.layout.for_landscape){
            holder.dash.setText("("+((m_1.getSlug().split("-").length)-1)+")");
            String[] seperate_by_dash_elements = m_1.getSlug().split("-");
            holder.year.setText(seperate_by_dash_elements[seperate_by_dash_elements.length-1]);
        }
    }


    @Override
    public int getItemCount() {
        return this.m.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        //public ImageView imageView;
        public TextView title;
        public TextView slug;
        public TextView dash;
        public TextView year;
        public ImageView image;
        public ViewHolder(View itemView) {
            super(itemView);
            //this.imageView = (ImageView) itemView.findViewById(R.id.img);

            this.title = (TextView) itemView.findViewById(R.id.title);
            this.slug = (TextView) itemView.findViewById(R.id.slug);
            this.image = (ImageView) itemView.findViewById(R.id.img);
            if(MyAdapter.view_layout != R.layout.for_landscape){
                this.year = (TextView) itemView.findViewById(R.id.year);
                this.dash = (TextView) itemView.findViewById(R.id.dash);
            }
        }
    }

}
