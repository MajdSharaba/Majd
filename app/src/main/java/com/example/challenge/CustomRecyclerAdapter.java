package com.example.challenge;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<ModelUtils> modelUtils;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    public CustomRecyclerAdapter(Context context, List modelUtils) {
        this.context = context;
        this.modelUtils = modelUtils;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(modelUtils.get(position));

        ModelUtils pu = modelUtils.get(position);

        holder.pName.setText(pu.getStart());
        holder.pJobProfile.setText(pu.getEnd());
        holder.price.setText(pu.getPrice());
        if(MainActivity.check(pu.getId())){
            holder.isFavourite.setVisibility(View.VISIBLE);

        }
        else{
            holder.isFavourite.setVisibility(View.INVISIBLE);

        }

        Glide.with(context).load(pu.getImage()).into(holder.pImage);

    }

    @Override
    public int getItemCount() {
        return modelUtils.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView pName;
        public TextView pJobProfile;
        public  ImageView isFavourite;
        public ImageView pImage;
        public TextView price;


        public ViewHolder(View itemView) {
            super(itemView);

            pName = (TextView) itemView.findViewById(R.id.pNametxt);
            pJobProfile = (TextView) itemView.findViewById(R.id.pJobProfiletxt);
            price = itemView.findViewById(R.id.price);
            pImage = itemView.findViewById(R.id.userImg);
            isFavourite = itemView.findViewById(R.id.isFavourit);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ModelUtils cpu = (ModelUtils) view.getTag();
                    Intent intent = new Intent(context,DetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("start",cpu.getStart());
                    bundle.putString("end",cpu.getEnd());
                    bundle.putString("image",cpu.getImage());
                    bundle.putString("id",cpu.getId());
                    bundle.putBoolean("isFavourit",cpu.isFavourite());
                    bundle.putString("price",cpu.getPrice());
                    intent.putExtras(bundle);
                    context.startActivity(intent);


                }
            });

        }
    }


}
