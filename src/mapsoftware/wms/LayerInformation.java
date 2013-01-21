package mapsoftware.wms;

public class LayerInformation {
	private final String title, parameter;
	
	public LayerInformation(String title, String parameter) {
		super();
		this.title = title;
		this.parameter = parameter;
	}

	public String getTitle() {
		return title;
	}

	public String getParameter() {
		return parameter;
	}
	
	
}
