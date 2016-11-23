package com.nadav.sdarot;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterTop10 extends ArrayAdapter implements ListAdapter {
    public List list= new ArrayList();




    public AdapterTop10(Context context, int resource/*, List<EpisodeClass> episodes*/) {
        super(context, resource);
        // TODO Auto-generated constructor stub
    }
    public void add(EpisodeClass object) {
        // TODO Auto-generated method stub
        list.add(object);
        super.add(object);
    }

    public void remove(int object) {
        // TODO Auto-generated method stub
        list.remove(object);
        super.add(object);
    }

    public static class ImgHolder
    {
        ImageView IMG;
        TextView NAME,PLOT,RANK;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return this.list.size();
    }



    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return this.list.get(position);
    }





    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View row;
        row = convertView;
        ImgHolder holder = null;


        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row_adapter_top10,parent,false);
            holder = new ImgHolder();
            holder.IMG =  (ImageView) row.findViewById(R.id.series_image);
            holder.NAME = (TextView) row.findViewById(R.id.series_titles);
            holder.PLOT = (TextView) row.findViewById(R.id.textView15);
            holder.RANK = (TextView) row.findViewById(R.id.textView16);
            row.setTag(holder);
        }
        else
        {
            holder = (ImgHolder) row.getTag();
        }

        EpisodeClass FR = (EpisodeClass) getItem(position);
        final String a=FR.imageUrl;
        if(FR.imageUrl.equals("none")){
            holder.IMG.setImageResource(R.drawable.no_image_icon);
        }else{
            Picasso.with(getContext()).load(FR.imageUrl).resize(560,680).into(holder.IMG);
        }

        holder.NAME.setText(FR.getName_of_series());
        holder.PLOT.setText(FR.plot);
        holder.RANK.setText(FR.RANK+"");


        return row;
    }

}
