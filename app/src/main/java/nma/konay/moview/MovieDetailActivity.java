package nma.konay.moview;

import android.support.v7.app.AppCompatActivity;
import android.os.*;
import android.widget.ImageView;
import android.support.design.widget.FloatingActionButton;
import android.graphics.*;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.view.animation.*;
import com.squareup.picasso.*;
import android.view.View;
import android.widget.*;
import org.json.*;
import android.content.*;
import android.app.*;
import master.charleshugo.fbclient.*;
import android.view.*;
import com.google.android.gms.ads.*;
import java.io.*;
import android.support.v4.content.*;
import android.*;
import android.content.pm.*;
import android.support.v4.app.*;
import android.net.*;





public class MovieDetailActivity extends AppCompatActivity
{

	private static final int PERMISSION_REQUEST_CODE = 101;
	private ImageView movieThumbnailImg,movieDetailCover;

	private TextView movieDetailTitle,movieDetailDesc,genre;

	private FloatingActionButton play_fab;
	
	private AdView myAds;
	
	private InterstitialAd mInterstitialAd;
	
	private String movieTitle;
	
	private static final String AD_UNIT_ID="ca-app-pub-5721094094081844/5446520148";
	
	Toolbar toolbar;
	
	private String SdLink,HdLink;
	
	private ProgressDialog pd;
	
	private Button detailMovieWatch,detailMovieDowonload;
	
	private String id;
	
	private RadioGroup radioGroup;
	
	private RadioButton radioBtnSd,radioBtnHd;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_movie);
		 toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
		toolbar.setTitleTextColor(Color.WHITE);
		pd=new ProgressDialog(this);
		pd.setMessage("Generating Movie Link...");
		pd.setCancelable(false);
		
		
		iniViews();

		setUpAds();
		
		setUpInterstitialAds();
		
		//requestStoragePermission();

	}

//	private void requestStoragePermission() {
//		
//					String state = Environment.getExternalStorageState();
//					if(Environment.MEDIA_MOUNTED.equals(state)){
//
//						if(Build.VERSION.SDK_INT > 23){
//
//							if(checkStoragePermissiom()){
//
//								String path = android.os.Environment.getExternalStorageDirectory()
//									+ File.separator
//									+ "MoView Downloaded" + File.separator.toString();
//									
//								if(!new File(path).exists()) { new File(path).mkdir(); }
//
//
//
//							} else requestPermission();
//
//						}else {
//							String path = Environment.getExternalStorageState()
//								+ File.separator
//								+ "MoView Downloaded" + File.separator;
//							if(!new File(path).exists()) { new File(path).mkdir(); }
//
//						}
//
//					}
//				}


				

	void iniViews(){

		play_fab=findViewById(R.id.floating_play_btn);
		
		//play_fab=new FloatingActionButton(getApplicationContext());
		
		movieThumbnailImg=findViewById(R.id.detail_movie_img);
		
		movieDetailCover=findViewById(R.id.detail_movie_cover);
		
		detailMovieWatch = findViewById(R.id.detail_movie_watch);
		
		detailMovieDowonload = findViewById(R.id.detail_movie_download);
		
		movieDetailDesc=findViewById(R.id.detail_movie_desc);

		genre=findViewById(R.id.genre_tv);
		
		movieTitle=getIntent().getExtras().getString("title");

		String imgUrl=getIntent().getExtras().getString("imgUrl");
		
		String genres=getIntent().getExtras().getString("genres");
		
		String desc = getIntent().getExtras().getString("desc");
		
		id=getIntent().getExtras().getString("id");
		
		
		
		Picasso.get()
			.load(imgUrl)
			.placeholder(R.drawable.ic_launcher_background)
			.into(movieThumbnailImg);

		Picasso.get()
				.load(imgUrl)
				.placeholder(R.drawable.ic_launcher_background)
				.into(movieDetailCover);
		//setup anim
		movieDetailCover.setAnimation(AnimationUtils.loadAnimation(this,R.anim.cover_anim));

		play_fab.setAnimation(AnimationUtils.loadAnimation(this,R.anim.cover_anim));

		movieDetailTitle=findViewById(R.id.detail_movie_title);

		movieDetailTitle.setText(movieTitle);

		getSupportActionBar().setTitle(movieTitle);

		
		
		movieDetailDesc.setText(desc);
		
		genre.setText(genres);
		
		
		play_fab.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
				
					pd.show();
					new FBClient().setOnResponseListener(new FBClient.OnResponseListener(){

							@Override
							public void onResponse(String p1, String p2)
							{
								
								Intent intent=new Intent(getApplicationContext(),MoviePlayerActivity.class);
								if(p1==null & p2==null) 
								{
									Toast.makeText(getApplicationContext(),"Movie Not found!",Toast.LENGTH_LONG).show();
									pd.dismiss();
									return;
								}
								intent.putExtra("HdUrl",p1.replaceAll("amp;",""));
								startActivity(intent);
								pd.dismiss();
							}

						
					}).post(id);
					
					
				}
			
		});
		
	
	}
	
	
	public void onWatch(final View v){
		
		
		pd.show();
		
		
		new FBClient().setOnResponseListener(new FBClient.OnResponseListener(){

				@Override
				public void onResponse(String p1, String p2)
				{
					/***************/
					
					if(p1==null & p2==null) {
						Toast.makeText(getApplicationContext(),"Movie Not found!",Toast.LENGTH_LONG).show();
						pd.dismiss();
						return;
						
					}
						
					if(p1!=null){
						HdLink=p1.replaceAll("amp;","");
					}
					
					if(p2!=null){
						SdLink=p2.replaceAll("amp;","");
					}
					
					/*********************************/
					AlertDialog.Builder builder=new AlertDialog.Builder(MovieDetailActivity.this);
					ViewGroup viewGruup=findViewById(android.R.id.content);
					View dialogView=LayoutInflater.from(v.getContext()).inflate(R.layout.custom_dialog,viewGruup,false);
					builder.setView(dialogView);
					final AlertDialog alertDialog=builder.create();
					alertDialog.setCancelable(false);
					alertDialog.show();
					
					TextView cancel=alertDialog.findViewById(R.id.cancel_watch);

					cancel.setOnClickListener(new View.OnClickListener(){

							@Override
							public void onClick(View p1)
							{
								alertDialog.dismiss();
							}


						});


					TextView download =alertDialog.findViewById(R.id.download);
					
					download.setOnClickListener(new View.OnClickListener(){

							@Override
							public void onClick(View p1)
							{
								radioGroup=alertDialog.findViewById(R.id.radio_group);
								int selectedId=radioGroup.getCheckedRadioButtonId();
								radioBtnSd=alertDialog.findViewById(selectedId);
								
								String state = Environment.getExternalStorageState();
								if(Environment.MEDIA_MOUNTED.equals(state)){

									if(Build.VERSION.SDK_INT > 23){

										if(checkStoragePermissiom()){

											String path = android.os.Environment.getExternalStorageDirectory()
												+ File.separator
												+ "MoView Downloaded" + File.separator.toString();

											if(!new File(path).exists()) { new File(path).mkdir(); }

											if(radioBtnSd.getText().equals("SD")){
												downloadUrl(SdLink);
											}else downloadUrl(HdLink);
											

										} else requestPermission();
										

									}else {
										String path = Environment.getExternalStorageState()
											+ File.separator
											+ "MoView Downloaded" + File.separator;
										if(!new File(path).exists()) { new File(path).mkdir(); 
										
											if(radioBtnSd.getText().equals("SD")){
												downloadUrl(SdLink);
											}else downloadUrl(HdLink);
										}

									}

								}
								
							}

							
						
					});
					
					
					TextView watch=alertDialog.findViewById(R.id.watch);

					watch.setOnClickListener(new View.OnClickListener(){

							@Override
							public void onClick(View p1)
							{
								// TODO: Implement this method

								Toast.makeText(MovieDetailActivity.this,"Start Playing!",Toast.LENGTH_LONG).show();
								Intent intent=new Intent(MovieDetailActivity.this,MoviePlayerActivity.class);
								radioGroup=alertDialog.findViewById(R.id.radio_group);
								int selectedId=radioGroup.getCheckedRadioButtonId();
								radioBtnSd=alertDialog.findViewById(selectedId);

								if(radioBtnSd.getText().equals("SD")){

									intent.putExtra("HdUrl",SdLink);


								}else{

									intent.putExtra("HdUrl",HdLink);


								}
								startActivity(intent);
							}

						});
					
					/*********************************/
					
					showInterstitialAd();
					
					pd.dismiss();
					
					
			
				}

		}).post(id);
		
		

		
		Toast.makeText(getApplicationContext(),"Hi",Toast.LENGTH_LONG).show();
		
	}
	
	public void onDownload(View v){
		
		Toast.makeText(MovieDetailActivity.this,"Comming Soon..!",Toast.LENGTH_LONG).show();
	}
	
	private void setUpAds()
	{
		myAds=findViewById(R.id.adView2);
		//myAds=new AdView(this);
		AdRequest adRequest=new AdRequest.Builder().build();
		myAds.loadAd(adRequest);

	}
	
	private void setUpInterstitialAds()
	{
		mInterstitialAd = new InterstitialAd(this);
		mInterstitialAd.setAdUnitId(AD_UNIT_ID);
		AdRequest adRequest = new AdRequest.Builder().build();
		mInterstitialAd.loadAd(adRequest);

		mInterstitialAd.setAdListener(new AdListener(){
				@Override
				public void onAdClosed(){
					reloadAd();
				}

			});

//		mInterstitialAd.setAdListener( new AdListener(){
//				@Override
//				public void onAdLoaded(){
//					showInterstitialAd();
//				}
//			});
	}


	public void reloadAd()
	{
		if(!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()){
			AdRequest adRequest = new AdRequest.Builder().build();
			mInterstitialAd.loadAd(adRequest);
		}
	}

	private void showInterstitialAd(){
		if(mInterstitialAd != null && mInterstitialAd.isLoaded()){
			mInterstitialAd.show();

		}else reloadAd();

	}
	
	
	private void requestPermission()
	{
		if(ActivityCompat.shouldShowRequestPermissionRationale(MovieDetailActivity.this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
			AlertDialog ad= new AlertDialog.Builder(MovieDetailActivity.this).create();
			ad.setMessage("You need to allow storage permission to download this file");
			ad.setButton(AlertDialog.BUTTON_POSITIVE, "Allow", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface p1, int p2)
					{

						requestWriteExternalStoragePermission();

					}

				});

			ad.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel" , new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface p1, int p2)
					{

						Toast.makeText(getApplicationContext(),"You can't download this file without permission",Toast.LENGTH_LONG).show();
					}

				});

			ad.show();
		} else requestWriteExternalStoragePermission();
	}
	
	
	private boolean checkStoragePermissiom()
	{
		int result = ContextCompat.checkSelfPermission(MovieDetailActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
		if(result == PackageManager.PERMISSION_GRANTED)
		{ 
			return true;

		}else return false;

	}
	
	private void requestWriteExternalStoragePermission()
	{
		ActivityCompat.requestPermissions(MovieDetailActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);

	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
	{
		switch (requestCode) {
			case PERMISSION_REQUEST_CODE: {
					if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
						// permission granted
							Toast.makeText(this,"Press Again Download Button",Toast.LENGTH_LONG).show();
						
					} else {
						Toast.makeText(getApplicationContext(), "You cannot use this app without read external storage access", 0).show();
					}}
				return;	
		}				
	}
	
	private void downloadUrl(String sdHdUrl)
	{
		DownloadManager.Request request = new DownloadManager.Request(Uri.parse(sdHdUrl));
		request.setTitle(movieTitle);

		String path = android.os.Environment.getExternalStorageDirectory() 
			+ File.separator
			+ "MoView Downloaded" + File.separator;
		String filePath = "file://"+ path +"/" + movieTitle + ".mp4" ;
		request.setDestinationUri(Uri.parse(filePath));
		request.allowScanningByMediaScanner();
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		DownloadManager dm=(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
		dm.enqueue(request);
		Toast.makeText(MovieDetailActivity.this, "Download started.", 1).show();

	}
	
} 
