package car.top.content360.menu;

public class ClickButton extends Button {

	public ClickButton(){}
	
	public ClickButton(String key){
		super();
		this.key = key;
	}
	
	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
