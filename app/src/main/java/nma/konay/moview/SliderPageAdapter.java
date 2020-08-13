package nma.konay.moview;


import android.view.View;
import java.util.List;
import android.content.Context;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.view.*;
import android.support.annotation.*;
import com.squareup.picasso.*;
import android.support.design.widget.*;
import android.content.*;
import android.widget.*;
import android.os.*;
import java.util.*;
import android.widget.AdapterView.*;
import org.json.*;
import android.app.*;
import master.charleshugo.fbclient.*;


public class SliderPageAdapter extends PagerAdapter
{
	private Context mContext;
	private List<Slide> mList;
	
	ProgressDialog pd;

	public SliderPageAdapter(Context mContext, List<Slide> mList)
	{
		this.mContext= mContext;
		this.mList = mList;
		
		pd=new ProgressDialog(mContext);

		pd.setMessage("Generating Movie Link...");

		pd.setCancelable(false);
	}

	@NonNull
	@Override
	public Object instantiateItem(@NonNull ViewGroup container,final int position)
	{
		// TODO: Implement this method
	  	LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View slideLayout=inflater.inflate(R.layout.slide_item,null);

		ImageView slideImage=slideLayout.findViewById(R.id.slide_image);

		TextView slideTitle=slideLayout.findViewById(R.id.slide_title);
		
		FloatingActionButton fab=slideLayout.findViewById(R.id.floating_play_btn);
		
		
		final Slide	slideItem=mList.get(position);

		
		
		slideTitle.setText(slideItem.getTittle());
		

		Picasso.get()
			.load(slideItem.getImage())
			.placeholder(R.drawable.ic_launcher_background)
			.into(slideImage);
			
		fab.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					pd.show();
					final Intent intent=new Intent(mContext,MoviePlayerActivity.class);
					new FBClient().setOnResponseListener(new FBClient.OnResponseListener(){

							@Override
							public void onResponse(String p1, String p2)
							{
								
								if(p1==null & p2==null) {
									Toast.makeText(mContext,"Movie Not found!",Toast.LENGTH_LONG).show();
									pd.dismiss();
									return;
									
								}
								
								intent.putExtra("HdUrl",p1.replaceAll("amp;",""));
								mContext.startActivity(intent);
								pd.dismiss();
							}

						
					}).post(slideItem.getId());
					//Toast.makeText(mContext,"Playing movie",Toast.LENGTH_LONG).show();
					
					
				}

			
		});
		

//		slideImage.setImageResource(mList.get(position).getImage());
//
//		slideTitle.setText(mList.get(position).getTitle());
		
		container.addView(slideLayout);

		return slideLayout;
	}


	@Override
	public int getCount()
	{
		// TODO: Implement this method
		return mList.size();
	}

	@Override
	public boolean isViewFromObject(@NonNull View p1, @NonNull Object p2)
	{
		// TODO: Implement this method
		return p1==p2;
	}

	@Override
	public void destroyItem(@NonNull ViewGroup container, int position,@NonNull Object object)
	{
		// TODO: Implement this method
		container.removeView((View) object);
	}

	
	

}
