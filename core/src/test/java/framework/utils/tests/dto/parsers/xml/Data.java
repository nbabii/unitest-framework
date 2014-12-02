package framework.utils.tests.dto.parsers.xml;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import framework.utils.dto.IFrameworkDTO;

@SuppressWarnings("serial")
@XStreamAlias("DATA")
// maps DATA element in XML to this class
public class Data implements IFrameworkDTO{

	@XStreamImplicit(itemFieldName = "BAN")
	private List<Ban> bans = new ArrayList<Ban>();

	public List<Ban> getBans() {
		return bans;
	}
}