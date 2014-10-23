package framework.utils.tests.dto.httpclient;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("result")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

	@XStreamAlias("elevation")
	private double elevation;
	
	@XStreamAlias("location")
	private Location location;
	
	@XStreamAlias("resolution")
	private double resolution;

	@JsonCreator
	public Result(@JsonProperty("elevation") double elevation,
			@JsonProperty("location") Location location,
			@JsonProperty("resolution") double resolution) {
		super();
		this.elevation = elevation;
		this.location = location;
		this.resolution = resolution;
	}

	public double getElevation() {
		return elevation;
	}

	public void setElevation(double elevation) {
		this.elevation = elevation;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public double getResolution() {
		return resolution;
	}

	public void setResolution(double resolution) {
		this.resolution = resolution;
	}

}
