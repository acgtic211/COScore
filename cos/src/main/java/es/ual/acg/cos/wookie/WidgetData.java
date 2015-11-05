package es.ual.acg.cos.wookie;

public class WidgetData
{
	private String url;
	private String identifier;
	private String title;
	private int height;
	private int width;
	
	public WidgetData()
	{
		
	}
	
	public WidgetData(String url, String identifier, String title, int height, int width)
	{
		this.url = url;
		this.identifier = identifier;
		this.title = title;
		this.height = height;
		this.width = width;		
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	

}
