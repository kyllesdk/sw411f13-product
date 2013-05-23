import java.util.*;

public class STC extends Object {
	String type;
	String name;
	Token value;
	String stringValue;

	public STC(String iType, Token iValue) {
		type = iType;
		value = iValue;
	}

	public STC(String iType, String iValue) {
		type = iType;
		stringValue = iValue;
	}

	public STC(String iType, String iName,  Token iValue) {
		type = iType;
		name = iName;
		value = iValue;
	}

	public STC(String iType, String iName, String iStringValue) {
		type = iType;
		name = iName;
		stringValue = iStringValue;
	}
}