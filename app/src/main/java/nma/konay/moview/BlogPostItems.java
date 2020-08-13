package nma.konay.moview;

public class BlogPostItems
{

	public String title;
	public String id;
	public String thumbnailUrl;
	public String genre;
	public String genres;
	public String desc;

	public BlogPostItems(String title, String id, String thumbnailUrl, String genres, String desc)
	{
		this.title = title;
		this.id = id;
		this.thumbnailUrl = thumbnailUrl;
		
		this.genres = genres;
		this.desc = desc;
	}

	public BlogPostItems(String title, String id, String thumbnailUrl, String genre)
	{
		this.title = title;
		this.id = id;
		this.thumbnailUrl = thumbnailUrl;
		this.genre = genre;
	}

	public BlogPostItems(String title, String id, String thumbnailUrl)
	{
		this.title = title;
		this.id = id;
		this.thumbnailUrl = thumbnailUrl;
	}

	public void setGenres(String genres)
	{
		this.genres = genres;
	}

	public String getGenres()
	{
		return genres;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public String getDesc()
	{
		return desc;
	}

	
	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getTitle()
	{
		return title;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getId()
	{
		return id;
	}

	public void setThumbnailUrl(String thumbnailUrl)
	{
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getThumbnailUrl()
	{
		return thumbnailUrl;
	}

	public void setGenre(String genre)
	{
		this.genre = genre;
	}

	public String getGenre()
	{
		return genre;
	}}
