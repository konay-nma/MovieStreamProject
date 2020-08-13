package nma.konay.moview;
import java.util.*;
import android.content.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyviewHolder>
{

	private List<HomeItems> homeItems;
	private Context context;
	private MovieAdapter movieAdpater;
	private RecyclerView.RecycledViewPool recycledViewPool;

	public HomeAdapter(List<HomeItems> homeItems, Context context)
	{
		this.homeItems = homeItems;
		this.context = context;
		
		recycledViewPool=new RecyclerView.RecycledViewPool();
	}
	
	@Override
	public HomeAdapter.MyviewHolder onCreateViewHolder(ViewGroup p1, int p2)
	{
		View view=LayoutInflater.from(p1.getContext()).inflate(R.layout.movie_item,p1,false);
		return new MyviewHolder(view);
	}

	@Override
	public void onBindViewHolder(HomeAdapter.MyviewHolder p1, int p2)
	{
		// TODO: Implement this method
		p1.movieItemTv.setText(homeItems.get(p2).getGenre());
		movieAdpater=new MovieAdapter(homeItems.get(p2).getData(),context);
		p1.homeRcView.setAdapter(movieAdpater);
		p1.homeRcView.setRecycledViewPool(recycledViewPool);
	
		
		
	}

	@Override
	public int getItemCount()
	{
		// TODO: Implement this method
		return homeItems.size();
	}
	
	public class MyviewHolder extends RecyclerView.ViewHolder{
		
		private TextView movieItemTv;
		private RecyclerView homeRcView;
		public MyviewHolder(View v){
			super(v);
			movieItemTv=v.findViewById(R.id.movie_itemTextView);
			homeRcView=v.findViewById(R.id.movie_itemRcview);
			homeRcView.setHasFixedSize(true);
			homeRcView.setNestedScrollingEnabled(false);
			homeRcView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
			
		}
	}
	
}
