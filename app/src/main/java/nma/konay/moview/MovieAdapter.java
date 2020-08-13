package nma.konay.moview;

import android.view.*;
import java.util.*;
import android.content.*;
import android.widget.*;

import android.text.*;
import android.graphics.*;
import android.util.*;
import java.net.*;
import java.io.*;
import android.support.v7.widget.*;
import com.squareup.picasso.*;
import nma.konay.moview.MovieAdapter.MyViewHolder.*;
import com.google.android.gms.ads.*;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder>
{

	//private InterstitialAd mInterstitialAd;
	//private static final String AD_UNIT_ID="ca-app-pub-5721094094081844/5446520148";
	private List<BlogPostItems> postItem;
	private Context context;

	public MovieAdapter(List<BlogPostItems> postItem, Context context)
	{
		this.postItem = postItem;
		this.context = context;
	}

	
	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup p1, int viewType)
	{
	
		View view=LayoutInflater.from(p1.getContext())
			.inflate(R.layout.movie_items, p1, false);
		return new MyViewHolder(view);
		
	}

	@Override
	public void onBindViewHolder(MyViewHolder p1, int position)
	{
		final BlogPostItems item=postItem.get(position);
		 
		String url="";
		url=item.getThumbnailUrl();
					p1.movTitle.setText(item.getTitle());
					
					Picasso.get()
					.load(url)
					.placeholder(R.drawable.ic_launcher_background)
					.into(p1.movImg);
		p1.storePostItem(item);
	}

	@Override
	public int getItemCount()
	{
		return postItem.size();
	}



	public  class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
	{

		@Override
		public void onClick(View p1)
		{
			//setUpInterstitialAds();
			Intent intent=new Intent(p1.getContext(),MovieDetailActivity.class);
			//final BlogPostItems item=postItem.get(getAdapterPosition());
			if(item != null){
				intent.putExtra("title",item.getTitle());
				intent.putExtra("imgUrl",item.getThumbnailUrl());
				intent.putExtra("id",item.getId());
				intent.putExtra("genres",item.getGenres());
				intent.putExtra("desc",item.getDesc());
				p1.getContext().startActivity(intent);
			}
		}

		@Override
		public boolean onLongClick(View p1)
		{
			return false;
		}



		public TextView movTitle,movTitle2;
		public ImageView movImg,movImg2;
		public android.support.v7.widget.CardView cardView;

		public MyViewHolder(View v)
		{
			super(v);
		
			movTitle = v.findViewById(R.id.item_movie_title);
			movImg = v.findViewById(R.id.item_movie_img);
			cardView = v.findViewById(R.id.layout);
			v.setOnClickListener(this);
		}
		

		/*Charles hugo smart codes*/
		private BlogPostItems item = null;
		public void storePostItem(BlogPostItems item){
			this.item = item;
		}
	}
	
	
	/******************"""""
	private void setUpInterstitialAds()
	{
		mInterstitialAd = new InterstitialAd(context);
		mInterstitialAd.setAdUnitId(AD_UNIT_ID);
		AdRequest adRequest = new AdRequest.Builder().build();
		mInterstitialAd.loadAd(adRequest);

		mInterstitialAd.setAdListener(new AdListener(){
				@Override
				public void onAdClosed(){
					reloadAd();
				}

			});

		mInterstitialAd.setAdListener( new AdListener(){
				@Override
				public void onAdLoaded(){
					showInterstitialAd();
				}
			});
	}
	
	
	public void reloadAd()
	{
		if(!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()){
			AdRequest adRequest = new AdRequest.Builder().build();
			mInterstitialAd.loadAd(adRequest);
		}
	}

	public void showInterstitialAd(){
		if(mInterstitialAd != null && mInterstitialAd.isLoaded()){
			mInterstitialAd.show();

		}else reloadAd();

	}
	*******/
	
}
