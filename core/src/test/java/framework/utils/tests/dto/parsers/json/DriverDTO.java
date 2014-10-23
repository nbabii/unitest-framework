package framework.utils.tests.dto.parsers.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DriverDTO {

	private int id;
	private String name;
	private int age;
	private Car car;

	@JsonCreator
	public DriverDTO(@JsonProperty("id") int id,
			@JsonProperty("name") String name, @JsonProperty("age") int age,
			@JsonProperty("car") Car car) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.car = car;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

}
