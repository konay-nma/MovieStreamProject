package nma.konay.moview;


import android.os.Bundle;


import java.util.List;

import java.util.ArrayList;
import android.util.Log;
import java.util.Timer;
import java.util.TimerTask;
import android.view.Menu;
import android.view.MenuItem;

import android.os.AsyncTask;
import org.json.*;
import android.text.*;
import android.widget.*;
import android.content.*;
import android.view.*;
import android.support.v7.app.*;
import android.support.v4.view.*;
import android.support.design.widget.*;
import android.support.v7.widget.RecyclerView;
import android.support.v4.widget.*;
import android.support.v7.widget.LinearLayoutManager;
import java.util.*;
import com.google.android.gms.ads.*;

public class MainActivity extends AppCompatActivity
{

	private ViewPager slidePager;

	private List<Slide> slideList;

	
	
	private List<HomeItems> homeItems;

	
	HomeAdapter homeAdapter;

	private TabLayout indicator;

	private String link="https://latteada.blogspot.com/feeds/posts/default?alt=json&start-index=1&max-results=500";

	//https://aideandroidlessons.blogspot.com/feeds/posts/default?alt=json&start-index=1&max-results=10

	private RecyclerView rcView;

	private SwipeRefreshLayout mSwipeRefreshLayout;
	
	
	private ProgressBar progressBar;
	
	Timer timer;
	
	private AdView myAds;
	

//	
//	@Override
//	protected void attachBaseContext(Context base) {
//		super.attachBaseContext(base);
//		MultiDex.install(this);
//	}
//	

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
			
       android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
		
		progressBar=findViewById(R.id.spin_kit);
		
		
		
		rcView = findViewById(R.id.Rv_movie);
	
		rcView.setHasFixedSize(true);

		rcView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
	

		slidePager = findViewById(R.id.slide_pager);

		indicator = findViewById(R.id.indicator);

		setUpAds();
		
		
		
		/*mSwipeRefreshLayout=findViewById(R.id.swipe_refresh_layout);

		 mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

		 @Override
		 public void onRefresh()
		 {
		 refresh();
		 }


		 });

		 mSwipeRefreshLayout.setColorSchemeResources(
		 R.color.refresh_progress_1,
		 R.color.refresh_progress_2,
		 R.color.refresh_progress_3);*/
		 
		slideList=new ArrayList<>();

		//timer=new Timer();
		

		indicator.setupWithViewPager(slidePager,true);


		refresh();

	}

	
	class sliderTimer extends TimerTask
	{

		@Override
		public void run()
		{
			
			MainActivity.this.runOnUiThread(new Runnable(){

					@Override
					public void run()
					{
						
						if (slidePager.getCurrentItem() < slideList.size() - 1)
						{
							slidePager.setCurrentItem(slidePager.getCurrentItem() + 1);
						}
						else
						{
							slidePager.setCurrentItem(0);
						}
					}


				});
		}


	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
	{
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
	{
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
		{
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

	//refresh method
	public void refresh()
	{

		new DownloadTask().execute(link);

	}	

	public class DownloadTask extends AsyncTask <String,Void,String>
	{
		

		@Override
		protected void onPreExecute()
		{
			progressBar.setVisibility(View.VISIBLE);
			super.onPreExecute();

		}


		@Override
		protected String doInBackground(String[] p1)
		{
			String result=JSONDownloader.download(p1[0]);
			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{
			super.onPostExecute(result);
			
			progressBar.setVisibility(View.GONE);
			processJson(result);
		

		}

		

	}
	
	public void processJson(String result)
	{
		
		List<BlogPostItems> myList2 = new ArrayList<>();
		homeItems=new ArrayList<>();
		
		
		
		try
		{
			JSONObject jo=new JSONObject(result);

			JSONArray ja=jo.getJSONObject("feed").getJSONArray("entry");
			
			Toast.makeText(getApplicationContext(), "JO length " + ja.length(), Toast.LENGTH_SHORT).show();
			
			for (int i=0; i < ja.length(); i++)
			{

				JSONObject jo2=ja.getJSONObject(i);
				
					
					String content=jo2.getJSONObject("content").getString("$t");
					
					JSONObject jo3=new JSONObject(Html.fromHtml(content).toString());
			
					slideList.add(new Slide(jo3.getString("title"),jo3.getString("imgUrl"),jo3.getString("id")));
					
				for(int i1=6 ; i1 < slideList.size(); i1++){

					Random rd = new Random();
					
					int rdIndex=rd.nextInt(slideList.size());
					
					slideList.remove(rdIndex);
					
					
				}
				
					
					myList2.add(new BlogPostItems(jo3.getString("title"),jo3.getString("id"),jo3.getString("imgUrl"),jo3.getString("genres"),jo3.getString("desc")));
					
			}
			
			
			
			
			/*CharlesHugo's genius Codes*/
			
			homeItems.add(new HomeItems("Recently Added",myList2));
			
			
			String[] genres = {"Scifi & Action","Action & Adventure","Drama, Comedy & Crime","Documentary & Family","Animation","Indian","Adult(18+)","Horror"};
			for (int j=0; j < genres.length; j++) {
				List<BlogPostItems> myList = new ArrayList<>();
				
				for (int i=0; i < ja.length(); i++)
				{
					
					JSONObject jo2=ja.getJSONObject(i);

					String content=jo2.getJSONObject("content").getString("$t");

					JSONObject	jo3=new JSONObject(Html.fromHtml(content).toString());
					
					
					if (genres[j].equals(jo3.getString("genre"))){
						myList.add(new BlogPostItems(jo3.getString("title"),jo3.getString("id"),jo3.getString("imgUrl"),jo3.getString("genres"),jo3.getString("desc")));
					}
						
					
				}
				
				
				
				homeItems.add(new HomeItems(genres[j], myList));
			}
			/*CharlesHugo's genius Codes*/
			
			homeAdapter=new HomeAdapter(homeItems,this);
			rcView.setAdapter(homeAdapter);
			
		
			SliderPageAdapter slideAdpter=new SliderPageAdapter(this, slideList);

			slidePager.setAdapter(slideAdpter);
			
			//Log.e("AAA", "" + postItems.size());

		}
		catch (JSONException e)
		{
			Log.e("JSON", e.toString());
		}
		
		
	}

	
	
	@Override
	protected void onDestroy()
	{
		myAds.destroy();
		timer.cancel();
		super.onDestroy();
	}

	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		if(myAds != null)
		{
			myAds.pause();
		}
		timer.cancel();
		super.onPause();
	}

	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		myAds.resume();
		timer=new Timer();
		//if(slideList.size() > 0) {
		timer.scheduleAtFixedRate(new MainActivity.sliderTimer(), 6000, 8000);
		//}
		super.onResume();
	}
	
	
	private void setUpAds()
	{
		myAds=findViewById(R.id.adView);
		//myAds=new AdView(this);
		AdRequest adRequest=new AdRequest.Builder().build();
		myAds.loadAd(adRequest);
		
	}
	
	}

