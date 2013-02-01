package mapsoftware.wms;

/**
 * <p>Container for the location information</p>
 * 
 * @author Johannes Miettinen
 * @author Aleksi Haapsaari
 * @author Ville Ahti
 * 
 */
public class LocationInformation {
	
	private Double[] Coords = new Double[4];
	private String LocArea;
	private int Counter;
	private Double Angle;
	
	/**
	 * <p>Constructor for the class. Parameters form two coordinates: (minX, minY) and (maxX, maxY), which
	 *  are formatted in to a single string. </p>
	 * 
	 * @param minX Double representing minimum X coordinate
	 * @param minY Double representing minimum Y coordinate
	 * @param maxX Double representing maximum X coordinate
	 * @param maxY Double representing maximum Y coordinate
	 * 
	 */
	public LocationInformation(Double minX, Double minY, Double maxX, Double maxY) {
		this.Coords[0] = minX;
		this.Coords[1] = minY;
		this.Coords[2] = maxX;
		this.Coords[3] = maxY;
		this.LocArea = "";
		this.Counter = 0;
		this.Angle = (maxX - minX)/(maxY - minY);
		formatArea();
	}

	/**
	 * <p>Getter for the minX coordinate. </p>
	 * @return Double value representing minX coordinate.
	 */
	public Double getMinX() {
		return this.Coords[0];
	}

	/**
	 * <p>Getter for the minY coordinate. </p>
	 * @return Double value representing minY coordinate.
	 */
	public Double getMinY() {
		return this.Coords[1];
	}

	/**
	 * <p>Getter for the maxX coordinate. </p>
	 * @return Double value representing maxX coordinate.
	 */
	public Double getMaxX() {
		return this.Coords[2];
	}

	/**
	 * <p>Getter for the maxY coordinate. </p>
	 * @return Double value representing maxY coordinate.
	 */
	public Double getMaxY() {
		return this.Coords[3];
	}
	
	/**
	 * <p>Getter for the area coordinates. </p>
	 * @return String containing coordinates minX, minY, maxX and maxY.
	 */
	public String getArea(){
		return this.LocArea;
	}
	
	
	/**
	 * <p>Changes the coordinates according to which parameter is given. </p>
	 * @param direction String defining in what direction the coordinates should be changed.
	 */
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
	}//move
	
	
	
	/**
	 * <p>Places the coordinates in to a String in a correct format. </p>
	 * 
	 */
	private void formatArea() {
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
