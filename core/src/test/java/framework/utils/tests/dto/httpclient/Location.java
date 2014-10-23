package framework.utils.tests.dto.httpclient;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("location")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

	@XStreamAlias("lat")
	private double lat;
	
	@XStreamAlias("lng")
	private double lng;

	@JsonCreator
	public Location(@JsonProperty("lat") double lat, @JsonProperty("lng") double lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

}
