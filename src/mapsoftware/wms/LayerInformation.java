package mapsoftware.wms;

public class LayerInformation {
	private final String name, title;
	
	public LayerInformation(String name, String title) {
		super();
		this.name = name;
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public String toString() {
		return "LayerInformation [name=" + name + ", title=" + title + "]";
	}
	
}
