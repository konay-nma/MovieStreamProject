package nma.konay.moview;
import java.util.*;

public class HomeItems
{
	
	public String genre;
	
	public List<BlogPostItems> data;

	public HomeItems(String genre, List<BlogPostItems> data)
	{
		this.genre = genre;
		this.data = data;
	}

	public HomeItems(List<BlogPostItems> data)
	{
		this.data = data;
	}

	public void setGenre(String genre)
	{
		this.genre = genre;
	}

	public String getGenre()
	{
		return genre;
	}

	

	public void setData(List<BlogPostItems> data)
	{
		this.data = data;
	}

	public List<BlogPostItems> getData()
	{
		return data;
	}}
