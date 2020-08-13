package nma.konay.moview;

public class Slide
{
	private String tittle;
	private String image;
	private String id;

	public Slide(String tittle, String image, String id)
	{
		this.tittle = tittle;
		this.image = image;
		this.id = id;
	}



	public void setTittle(String tittle)
	{
		this.tittle = tittle;
	}

	public String getTittle()
	{
		return tittle;
	}

	public void setImage(String image)
	{
		this.image = image;
	}

	public String getImage()
	{
		return image;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getId()
	{
		return id;
	}}
