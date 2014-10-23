package framework.utils.tests.dto.database;

import javax.persistence.Column;
import javax.persistence.Entity;

import framework.dto.IFrameworkDTO;

@SuppressWarnings("serial")
@Entity
public class DatabaseTestData implements IFrameworkDTO {

	@Column(name = "id")
	int id;
	@Column(name = "name")
	String name;
	@Column(name = "value")
	String value;

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
