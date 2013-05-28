import java.util.*;
import java.util.regex.*;

public class TypeCheckVisitor implements ArduinoLangVisitor {
	public String _errorMessage = "";

	public Object visit(SimpleNode node, Object data) {
		throw new RuntimeException("Visit SimpleNode");
	}

	public Object visit(ASTProgram node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		node.jjtGetChild(1).jjtAccept(this, data);

		return DataType.Program;
	}

	public Object visit(ASTProgramContent node, Object data) {

		node.jjtGetChild(0).jjtAccept(this, data);
		node.jjtGetChild(1).jjtAccept(this, data);

		return DataType.TypeNotImportant;
	}

	public Object visit(ASTProgramTop node, Object data) {

		return DataType.TypeNotImportant;
	}

	public Object visit(ASTProgramLibInclude node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}


	public Object visit(ASTProgramSetup node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

	public Object visit(ASTProgramLoop node, Object data) {

		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

	public Object visit(ASTStatements node, Object data) {
		PrintVisitor pv = new PrintVisitor();

		// If the returned DataType is TypeUnknown, then print the type error 
		if((DataType)node.jjtGetChild(0).jjtAccept(this, data) == DataType.TypeUnknown) {
			System.out.print("Type error: ");
			node.jjtGetChild(0).jjtAccept(pv, null);
			System.out.print(" (" + _errorMessage + ")");
			System.out.println();
		}

		return (node.jjtGetChild(1).jjtAccept(this, data));
	}

	public Object visit(ASTIf_stm node, Object data) {
		return DataType.TypeNotImportant;
	}

	public Object visit(ASTEls_stm node, Object data) {
		return DataType.TypeNotImportant;
	}

	public Object visit(ASTElsIf_stm node, Object data) {
		return DataType.TypeNotImportant;
	}

	public Object visit(ASTWhileStatement node, Object data) {
		return DataType.TypeNotImportant;
	}

	public Object visit(ASTFunctionCallAsExpression node, Object data) {
		return DataType.TypeNotImportant;
	}

	/**
	* Declaration
	* @hint Checks for type errors between child on and child two, and sends the error to the property errorMessage
	**/
	public Object visit(ASTDeclaration node, Object data) {
		PrintVisitor pv = new PrintVisitor();

		if(((DataType)node.jjtGetChild(0).jjtAccept(this, data) == DataType.TypeInteger) && ((DataType)node.jjtGetChild(1).jjtAccept(this, data) == DataType.TypeFloat)) {

			_errorMessage = "Trying to add Float to Integer, this result in loss of precision.";

			return DataType.TypeUnknown;

		} else if(((DataType)node.jjtGetChild(0).jjtAccept(this, data) != DataType.TypeBoolean) && ((DataType)node.jjtGetChild(1).jjtAccept(this, data) == DataType.TypeBoolean)) {

			_errorMessage = "Expected the variable to be of type boolean.";

			return DataType.TypeUnknown;
		}

		return DataType.Declaration;
	}

	public Object visit(ASTExpression node, Object data) {
		return(node.jjtGetChild(0).jjtAccept(this, data));
	}

	public Object visit(ASTFunctionCall node, Object data) {
		return DataType.TypeNotImportant;
	}

	public Object visit(ASTClassInstantiation node, Object data) {
		return DataType.TypeNotImportant;
	}

	public Object visit(ASTParameter node, Object data) {
		return DataType.TypeNotImportant;
	}

	public Object visit(ASTDivide_op node, Object data) {

		return DataType.TypeNotImportant;
	}

	public Object visit(ASTSqrt_op node, Object data) {
		return DataType.TypeNotImportant;
	}

	public Object visit(ASTAdd_op node, Object data) {
		PrintVisitor pv = new PrintVisitor();

	 	if(((DataType)node.jjtGetChild(0).jjtAccept(this, data) == DataType.TypeFloat) || ((DataType)node.jjtGetChild(1).jjtAccept(this, data) == DataType.TypeFloat)) {

	 		return DataType.TypeFloat;

	 	} else if (((DataType)node.jjtGetChild(0).jjtAccept(this, data) == DataType.TypeInteger) && ((DataType)node.jjtGetChild(1).jjtAccept(this, data) == DataType.TypeInteger)) {

	 		return DataType.TypeInteger;
		
		} else if(((DataType)node.jjtGetChild(0).jjtAccept(this, data) == DataType.TypeFloat) && ((DataType)node.jjtGetChild(1).jjtAccept(this, data) == DataType.TypeFloat)) {

			return DataType.TypeFloat;

		} else if(((DataType)node.jjtGetChild(0).jjtAccept(this, data) == DataType.TypeString) && ((DataType)node.jjtGetChild(1).jjtAccept(this, data) == DataType.TypeString)) {

			return DataType.TypeString;

		} 

		return DataType.Declaration;

		}

	 public Object visit(ASTBool_op node, Object data) {

		return DataType.TypeNotImportant;

	}

	 public Object visit(ASTMult_op node, Object data) {
	 	if(((DataType)node.jjtGetChild(0).jjtAccept(this, data) == DataType.TypeFloat) || ((DataType)node.jjtGetChild(1).jjtAccept(this, data) == DataType.TypeFloat)) {

	 		return DataType.TypeFloat;

	 	} else if (((DataType)node.jjtGetChild(0).jjtAccept(this, data) == DataType.TypeInteger) && ((DataType)node.jjtGetChild(1).jjtAccept(this, data) == DataType.TypeInteger)) {

	 		return DataType.TypeInteger;
		
		} else if(((DataType)node.jjtGetChild(0).jjtAccept(this, data) == DataType.TypeFloat) && ((DataType)node.jjtGetChild(1).jjtAccept(this, data) == DataType.TypeFloat)) {

			return DataType.TypeFloat;

		} else if(((DataType)node.jjtGetChild(0).jjtAccept(this, data) == DataType.TypeString) && ((DataType)node.jjtGetChild(1).jjtAccept(this, data) == DataType.TypeString)) {

			return DataType.TypeString;

		} 

		return DataType.Declaration;
	}

	// Gets the type of the identifier and returns et as a DataType
    public Object visit(ASTIdentifier node, Object data) {
    	Hashtable ST = (Hashtable) data;
    	STC hashTableEntry;

    	hashTableEntry = (STC)ST.get(node.value);

    	if(hashTableEntry.type == "int") {
    		return DataType.TypeInteger;

    	} else if(hashTableEntry.type == "float") {
    		return DataType.TypeFloat;
    
    	} else if(hashTableEntry.type == "string") {
    		return DataType.TypeString;
    
    	} else if(hashTableEntry.type == "boolean") {
    		return DataType.TypeBoolean;
    
    	} else {
    		return DataType.TypeUnknown;
    	}
    }

    public Object visit(ASTStringText node, Object data) {
    	return DataType.TypeString;
    }

    /**
    * BooleanNumber
    * @hint This is implemented to get the Token and analyse it to see if it is a boolean number (0) or (1) and then returns the correct value
    **/
    public Object visit(ASTBooleanNumber node, Object data) {
    	String number = node.value.toString();

    	// Checks if the value is (0) or (1)
		if("[0]".equals(number) || "[1]".equals(number)) {
			
			return DataType.TypeBoolean;
		} else {

			// Print error
			System.out.println("Type error: A boolean number has to be 1 or 0");
			
			return DataType.TypeNotImportant; // Maybe TypeUnknown here?
		}	 
    }

    /**
    * Number - Check if the number can be converted to Integer, if it can it returns DataType of integer, else it returns a DataType float
    * @hint a "try" statement is used here, because if the integer can not be converted i will throw and exception and crash the problem (IF this exception is not catched and handled)
    **/
    public Object visit(ASTNumber node, Object data) {
    	
    	String number = node.value.toString();
    	try {
    		Integer.parseInt(number);
    		return DataType.TypeInteger;
    	} catch(NumberFormatException nfe) {
    		return DataType.TypeFloat;
    	}
    }
}