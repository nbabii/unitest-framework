package framework.utils.tests.dto.parsers.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import framework.utils.dto.IFrameworkDTO;

@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Drive implements IFrameworkDTO{

	List<DriverDTO> drivers;

	@JsonCreator
	public Drive(List<DriverDTO> drivers) {
		super();
		this.drivers = drivers;
	}

	public List<DriverDTO> getDrivers() {
		return drivers;
	}

	public void setDrivers(List<DriverDTO> drivers) {
		this.drivers = drivers;
	}

}
