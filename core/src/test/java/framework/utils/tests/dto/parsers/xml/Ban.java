package framework.utils.tests.dto.parsers.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BAN")
// another mapping
public class Ban {

	@XStreamAlias("UPDATED_AT")
	private String dateOfUpdate;

	@XStreamAlias("TROUBLEMAKER")
	private Person person;

	public String getDateOfUpdate() {
		return dateOfUpdate;
	}

	public Person getPerson() {
		return person;
	}

}