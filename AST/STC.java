import java.util.*;

public class STC extends Object {
	String type;
	String name;
	Token value;

	public STC(String iType, String iName,  Token iValue) {
		type = iType;
		name = iName;
		value = iValue;
	}
}