package framework.utils.tests.dto.httpclient;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import framework.utils.dto.IFrameworkDTO;

@SuppressWarnings("serial")
@XStreamAlias("ElevationResponse")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Elevation implements IFrameworkDTO {

	@XStreamImplicit(itemFieldName = "result")
	private List<Result> results;
	
	@XStreamAlias("STATUS")
	private String status;

	@JsonCreator
	public Elevation(@JsonProperty("results") List<Result> results, @JsonProperty("status") String status) {
		super();
		this.results = results;
		this.status = status;
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
