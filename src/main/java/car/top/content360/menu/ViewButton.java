package car.top.content360.menu;

/**
 * @author ZP
 *
 */
public class ViewButton extends Button {

	public ViewButton(){}
	
	public ViewButton(String url){
		super();
		this.url = url;
	}
	
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
