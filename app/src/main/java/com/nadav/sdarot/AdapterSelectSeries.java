package com.nadav.sdarot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class AdapterSelectSeries extends ArrayAdapter implements ListAdapter {
	public List<EpisodeClass> list;




    public AdapterSelectSeries(Context context, int resource, List<EpisodeClass> episodes) {
        super(context, resource);
		list = episodes;
    }



    public void add(EpisodeClass object) {

        super.add(object);
        list.add(object);
    }



    public static class ImgHolder
    {
        ImageView IMG;
        TextView NAME;

    }
    @Override
    public int getCount() {
        return this.list.size();
    }


    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        ImgHolder holder;
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.layout_select_series,parent,false);
             holder = new ImgHolder();
            holder.IMG =  (ImageView) row.findViewById(R.id.series_image);
            holder.NAME = (TextView) row.findViewById(R.id.series_titles);

            row.setTag(holder);


        }
        else
        {

            holder = (ImgHolder) row.getTag();

        }

        final EpisodeClass FR = list.get(position);
        Picasso.with(getContext()).load(list.get(position).imageUrl).resize(560,680).into(holder.IMG);

        holder.NAME.setText(FR.title);

        return row;

    }

}
