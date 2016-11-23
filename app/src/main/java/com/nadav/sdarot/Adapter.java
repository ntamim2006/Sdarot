package com.nadav.sdarot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends ArrayAdapter implements ListAdapter {
	public List list= new ArrayList();




	public Adapter(Context context, int resource/*, List<EpisodeClass> episodes*/) {
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
	ImageView IMG, img_pause;
	TextView NAME,Numofseason,Numofepisode,textSeason,textEpisode,nextSeason,pause;
	ProgressBar pBar;
	ProgressBar pBarBig;

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
	row = inflater.inflate(R.layout.row_layout,parent,false);
	 holder = new ImgHolder();
		holder.IMG =  (ImageView) row.findViewById(R.id.series_image);
		holder.NAME = (TextView) row.findViewById(R.id.series_titles);
		holder.Numofseason = (TextView) row.findViewById(R.id.textseason);
		holder.Numofepisode = (TextView) row.findViewById(R.id.textEpisode);
		holder.textSeason = (TextView) row.findViewById(R.id.textView3);
		holder.textEpisode = (TextView) row.findViewById(R.id.textView5);
		holder.nextSeason = (TextView) row.findViewById(R.id.textView2);
		holder.pBar = (ProgressBar) row.findViewById(R.id.progressbar) ;
		holder.pBarBig = (ProgressBar) row.findViewById(R.id.progressBarBig) ;
		holder.pause = (TextView) row.findViewById(R.id.textView22);
		holder.img_pause = (ImageView) row.findViewById(R.id.imageView9);

		row.setTag(holder);
	}
	else
	{
		holder = (ImgHolder) row.getTag();
	}

	EpisodeClass FR = (EpisodeClass) getItem(position);

	if(FR.imageUrl == null || FR.imageUrl.equals("none")||FR.imageUrl.equals("")){
		holder.IMG.setImageResource(R.drawable.nophotoavalibale);
		FR.setDate("No data for next episode");

	}else{
		Picasso.with(getContext()).load(FR.imageUrl).resize(560,680).into(holder.IMG);
	}

	holder.NAME.setText(FR.getName_of_series());
	holder.Numofseason.setText(FR.getNum_of_season());
	holder.Numofepisode.setText(FR.getNum_of_episode());

	/* if the date field is empty the progressbar is needed */
	if(FR.Date.equals(""))
	{
		holder.nextSeason.setText(FR.Date);
		holder.pBar.setVisibility(ProgressBar.VISIBLE);
		/* cancel the progress bar if the series are not from omdb */
		if(FR.imageUrl == null || FR.imageUrl.equals("none")){
			holder.pBar.setVisibility(ProgressBar.INVISIBLE);
		}
	}
	else
	{
		/* if the date changed the progressbar stopped */
		holder.nextSeason.setText(FR.Date);
		holder.pBar.setVisibility(ProgressBar.INVISIBLE);
	}


	try {
		if(!FR.pausetime.equals(""))
		{
			holder.img_pause.setVisibility(View.VISIBLE);
			holder.pause.setVisibility(View.VISIBLE);
			holder.pause.setText(FR.pausetime);
		}
		else
		{
			holder.pause.setText(FR.pausetime);
			holder.img_pause.setVisibility(View.INVISIBLE);
			holder.pause.setVisibility(View.INVISIBLE);
		}

	} catch (Exception e) {

	}



	if (FR.FlagIfSwipe)
	{
		holder.nextSeason.setVisibility(View.INVISIBLE);
		holder.Numofepisode.setVisibility(View.INVISIBLE);
		holder.Numofseason.setVisibility(View.INVISIBLE);
		holder.textEpisode.setVisibility(View.INVISIBLE);
		holder.textSeason.setVisibility(View.INVISIBLE);
		holder.pBarBig.setVisibility(View.VISIBLE);
		holder.img_pause.setVisibility(View.INVISIBLE);
		holder.pause.setVisibility(View.INVISIBLE);
	}
	else
	{
		holder.nextSeason.setVisibility(View.VISIBLE);
		holder.Numofepisode.setVisibility(View.VISIBLE);
		holder.Numofseason.setVisibility(View.VISIBLE);
		holder.textEpisode.setVisibility(View.VISIBLE);
		holder.textSeason.setVisibility(View.VISIBLE);
		holder.pBarBig.setVisibility(View.INVISIBLE);

	}

	return row;
}

}
