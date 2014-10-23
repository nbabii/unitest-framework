package framework.utils.tests.dto.parsers.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("TROUBLEMAKER")
public class Person {

	@XStreamAlias("NAME1")
	private String firstName;

	@XStreamAlias("NAME2")
	private String lastName;

	@XStreamAlias("AGE")
	// String will be auto converted to int value
	private int age;

	@XStreamAlias("NUMBER")
	private String documentNumber;

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getAge() {
		return age;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

}
