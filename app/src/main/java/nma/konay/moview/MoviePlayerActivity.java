package nma.konay.moview;

import android.support.v7.app.*;
import android.os.*;
import android.widget.*;
import android.net.*;
import android.media.*;
import android.view.Window;
import android.widget.GridLayout.*;
import android.view.*;
import android.app.*;
import android.support.v4.media.session.*;



public class MoviePlayerActivity extends AppCompatActivity
{
	
	private VideoView playerView;

    private static String VIDEO_URL = "https://www.radiantmediaplayer.com/media/bbb-360p.mp4";
    private static long SEEK_POSITION = 0;
	
	private ProgressBar progressBar;
//	ProgressDialog pd;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setFullScreen();
		setContentView(R.layout.activity_player);
		
		progressBar=findViewById(R.id.spin_kit2);
		playerView=findViewById(R.id.playerView);
		
		VIDEO_URL=getIntent().getExtras().getString("HdUrl");
		progressBar.setVisibility(View.VISIBLE);
		
//		pd= new ProgressDialog(this);
//		pd.setMessage("Loading...");
//		pd.show();
		
		
		
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		iniPlayer();
	}
	

	
	private void setFullScreen(){
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
	private void iniPlayer(){
				
		
		playerView.setMediaController( new MediaController(this));
		playerView.setVideoURI(Uri.parse(VIDEO_URL));
		
		playerView.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){

				@Override
				public void onPrepared(MediaPlayer p1)
				{
					progressBar.setVisibility(View.GONE);
					playerView.start();
				}
			
		});
		
	}
}
