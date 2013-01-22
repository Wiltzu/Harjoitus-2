package mapsoftware.wms;

public class LocationArea {
	
	private Double[] coords = new Double[4];
	private String locArea;
	private int counter;
	
	public LocationArea(Double minX, Double minY, Double maxX, Double maxY) {
		this.coords[0] = minX;
		this.coords[1] = minY;
		this.coords[2] = maxX;
		this.coords[3] = maxY;
		this.locArea = "";
		this.counter = 0;
		formatArea();
	}

	public Double getMinX() {
		return this.coords[0];
	}

	public Double getMinY() {
		return this.coords[1];
	}

	public Double getMaxX() {
		return this.coords[2];
	}

	public Double getMaxY() {
		return this.coords[3];
	}
	
	public String getArea(){
		return this.locArea;
	}
	
	public void formatArea() {
		if(counter == 0) {
			this.locArea = "";
			this.locArea += Double.toString(this.coords[counter]) + ",";
			counter++;
			formatArea();
		 }
		if(0 < this.counter && this.counter <3) {
			this.locArea += Double.toString(this.coords[counter]) + ",";
			counter++;
			formatArea();
		}
		if(counter == 3) {
			this.locArea += Double.toString(this.coords[counter]);
			counter = 0;
		}

	 
		
	}
}
