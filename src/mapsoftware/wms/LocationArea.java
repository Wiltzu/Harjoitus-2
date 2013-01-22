package mapsoftware.wms;

public class LocationArea {
	
	private Double[] Coords = new Double[4];
	private String LocArea;
	private int Counter;
	private Double Angle;
	
	public LocationArea(Double minX, Double minY, Double maxX, Double maxY) {
		this.Coords[0] = minX;
		this.Coords[1] = minY;
		this.Coords[2] = maxX;
		this.Coords[3] = maxY;
		this.LocArea = "";
		this.Counter = 0;
		this.Angle = (maxX - minX)/(maxY - minY);
		formatArea();
	}

	public Double getMinX() {
		return this.Coords[0];
	}

	public Double getMinY() {
		return this.Coords[1];
	}

	public Double getMaxX() {
		return this.Coords[2];
	}

	public Double getMaxY() {
		return this.Coords[3];
	}
	
	public String getArea(){
		return this.LocArea;
	}
	
	
	public void move(String direction) {
		if(direction.equals("L")) {
			this.Coords[0] -= 0.01;
			this.Coords[2] -= 0.01;
			formatArea();
		}
		if(direction.equals("R")) {
			this.Coords[0] += 0.01;
			this.Coords[2] += 0.01;
			formatArea();
		}
		if(direction.equals("U")) {
			this.Coords[1] += 0.01;
			this.Coords[3] += 0.01;
			formatArea();
		}
		if(direction.equals("D")) {
			this.Coords[1] -= 0.01;
			this.Coords[3] -= 0.01;
			formatArea();
		}
		if(direction.equals("I")) {
			this.Coords[0] += 0.003 * this.Angle;
			this.Coords[1] += 0.003;
			this.Coords[2] -= 0.003 * this.Angle;
			this.Coords[3] -= 0.003;
			formatArea();
		}
		if(direction.equals("O")) {
			this.Coords[0] -= 0.003 * this.Angle;
			this.Coords[1] -= 0.003;
			this.Coords[2] += 0.003 * this.Angle;
			this.Coords[3] += 0.003;
			formatArea();
		}
	}
	
	
	
	public void formatArea() {
		if(Counter == 0) {
			this.LocArea = "";
			this.LocArea += Double.toString(this.Coords[Counter]) + ",";
			Counter++;
			formatArea();
		 }
		if(0 < this.Counter && this.Counter <3) {
			this.LocArea += Double.toString(this.Coords[Counter]) + ",";
			Counter++;
			formatArea();
		}
		if(Counter == 3) {
			this.LocArea += Double.toString(this.Coords[Counter]);
			Counter = 0;
		}
		
	}//formatArea
}
