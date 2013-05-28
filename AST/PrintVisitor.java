public class PrintVisitor implements ArduinoLangVisitor {

	/**
	* Visiting each node of type Simplenode
	* @param Simplenode node
	* @param Object data - The value of the node 
	**/
    public Object visit(SimpleNode node, Object data)
    {
	throw new RuntimeException("Visit SimpleNode");
    }

	public Object visit(ASTProgram node, Object data) {
		System.out.print("");
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.println(""); // Print new line tut use ; here
		node.jjtGetChild(1).jjtAccept(this, data);
		
		return data;
	}

	public Object visit(ASTProgramContent node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		node.jjtGetChild(1).jjtAccept(this, data);

		return data;
	}

	public Object visit(ASTProgramTop node, Object data) {
		for(int i = 0; i < node.jjtGetNumChildren(); i++) {
			node.jjtGetChild(i).jjtAccept(this, data);
		}

		return data;
	}

	/**
	* ProgramLibInclude
	* @prints # IDENTIFIER < IDENTIFIER >
	*/
	public Object visit(ASTProgramLibInclude node, Object data) {
		System.out.print("#");
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(" <");
		node.jjtGetChild(1).jjtAccept(this, data);
		System.out.println(">");

		return data;
	}

	/**
	* ProgramSetup
	* @prints void Setup() { STATEMENT }
	*/
	public Object visit(ASTProgramSetup node, Object data) {
		System.out.print("void ");
		System.out.print(node.value);
		System.out.println(" { ");
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.println(" ");
		System.out.println("}");

		return data;
	}

	/**
	* ProgramLoop
	* @prints void Loop() { STATEMENT }
	*/
	public Object visit(ASTProgramLoop node, Object data) {
		System.out.print("void ");
		System.out.print(node.value);
		System.out.println(" { ");
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.println("");
		System.out.println("}");

		return data;
	}

	public Object visit(ASTStatements node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.println("");
		node.jjtGetChild(1).jjtAccept(this, data);

		return data;
	}

	/**
	* Is_stm
	* @prints if( BOOLEANEXPRESSION ) { STATEMENTS } (ElsIf_stm)* [Els_stm]
	**/
	public Object visit(ASTIf_stm node, Object data) {
		System.out.print("if (");
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.println(") {");
		node.jjtGetChild(1).jjtAccept(this, data);
		System.out.println("");
		System.out.println("} ");
		if(node.jjtGetNumChildren() > 2) {
			for(int i = 2; i <= node.jjtGetNumChildren(); i++) {
				if(i < (node.jjtGetNumChildren() -1)) {
					node.jjtGetChild(i).jjtAccept(this, data);
				} else {
					node.jjtGetChild(i).jjtAccept(this, data);
					break;
				}
			}
		}
		
		return data;
	}

	/**
	* Els_stm
	* @prints else { STATEMENT } - used for the If_stm
	* @hint This statement is optional
	**/
	public Object visit(ASTEls_stm node, Object data) {
		System.out.println(" else {");
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.println("");
		System.out.println("}");

		return data;
	}

	/**
	* ElseIf_stm
	* @prints else if( BOOLEANEXPRESSION ) { STATEMENTS } - used for the if_stm
	* @hint This statement is concatenated, which means that there can be 0+ of these expressions 
	**/
	public Object visit(ASTElsIf_stm node, Object data) {
		System.out.print("else if(");
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.println(") {");
		node.jjtGetChild(1).jjtAccept(this, data);
		System.out.println("");
		System.out.println("}");
	
		return data;
	}

	/**
	* WhileStatement
	* @prints while( BOOLEANEXPRESSION ) { STATEMENTS }
	**/
	public Object visit(ASTWhileStatement node, Object data) {
		System.out.print("while (");
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.println(") {");
		node.jjtGetChild(1).jjtAccept(this, data);
		System.out.println("");
		System.out.println("}");

		return data;
	}


	/**
	* Declaration
	* @prints TYPE IDENTIFER = EXPRESSION;
	**/
	public Object visit(ASTDeclaration node, Object data) {

		System.out.print(node.value + " ");
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(" = ");
		
		for(int i = 1; i < node.jjtGetNumChildren(); i++) {
			node.jjtGetChild(i).jjtAccept(this, data);
		}

		System.out.print(";");

		return data;
	}

	/**
	* Expression
	* @prints each expression, this is ised for several methods like Declaration
	**/
	public Object visit(ASTExpression node, Object data) {

		node.jjtGetChild(0).jjtAccept(this, data);

		for(int i = 1; i < node.jjtGetNumChildren(); i++) {
			node.jjtGetChild(i).jjtAccept(this, data);
		
		}

		return (data);
	}

	/**
	* FunctionCall
	* @prints FUNCTIONNAME((PARAMETERS,)*);
	* @hint PARAMETERS is concatenated so that there can be 0+ parameters
	**/
	public Object visit(ASTFunctionCall node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print("(");
		if(node.jjtGetNumChildren() > 0){
			for(int i = 1; i < node.jjtGetNumChildren(); i++) {
				if(i < (node.jjtGetNumChildren() - 1)) {
					node.jjtGetChild(i).jjtAccept(this, data);
					System.out.print(", ");
					
				} else {
					node.jjtGetChild(i).jjtAccept(this, data);
					break;
				}
			}
		} else {
			System.out.print(")");
			System.out.print(";");
		}
	
		System.out.print(")");
		System.out.print(";");

		return data;
	}

	public Object visit(ASTFunctionCallAsExpression node, Object data) {
		String nodeValueString = node.value.toString();
		System.out.print(node.value);

		return data;
	}

	/**
	* ClassInstantiation
	* @prints CLASSNAME VARIABLE((PARAMETERS,)*);
	* @hint PARAMETERS is concatenated so that there can be 0+ parameters
	**/
	public Object visit(ASTClassInstantiation node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(" ");
		node.jjtGetChild(1).jjtAccept(this, data);
		System.out.print("(");
		if(node.jjtGetNumChildren() > 0){
			for(int i = 2; i <= node.jjtGetNumChildren(); i++) {
				if(i < (node.jjtGetNumChildren() - 1)) {
					node.jjtGetChild(i).jjtAccept(this, data);
					System.out.print(", ");
					
				} else {
					node.jjtGetChild(i).jjtAccept(this, data);
					break;
				}
			}
		} else {
			System.out.print(")");
			System.out.println(";");
		}
	
		System.out.print(")");
		System.out.println(";");

		return data;
	}

	/**
	* Parameter
	* @prints NAME, - There can be 0+ parameters
	**/
	public Object visit(ASTParameter node, Object data) {
		int i = 0;

		for(i = 0; i <= node.jjtGetNumChildren(); i++) {
			if(i < (node.jjtGetNumChildren() -1)) {
				node.jjtGetChild(i).jjtAccept(this, data);
				System.out.print(",");
			} else {
				node.jjtGetChild(i).jjtAccept(this, data);
				break;
			}
		}
		return data;
	}

	/**
	* Add_op
	* @prints Prints out TERM EXPRESSION
	* @hint if ^ (the power operation), is used then the syntax chaned to fit the target language
	**/
	public Object visit(ASTAdd_op node, Object data) {
		String nodeValueString = node.value.toString();
		
		if("^".equals(nodeValueString)) {	
			System.out.print(" pow(");
			node.jjtGetChild(0).jjtAccept(this, data);
			System.out.print(", ");
			node.jjtGetChild(1).jjtAccept(this, data);
			System.out.print(")");
		} else {
			node.jjtGetChild(0).jjtAccept(this, data);
			System.out.print(" " + node.value + " ");
			node.jjtGetChild(1).jjtAccept(this, data);
		}
		return data;
	}

	/**
	* Divide_op
	* @prints /NUMBER (the number can not be 0)
	**/
	public Object visit(ASTDivide_op node, Object data) {
		String nodeValueString = node.value.toString();
		System.out.print(node.value);
		
		return data;
	}

	/**
	* Sqrt_op
 	* @prints out sqrt(POSITIVENUMBER)
 	* @hint squareroot can not be negative, and the syntax is chaned from SQRT to sqrt to fit the target language
	**/
	public Object visit(ASTSqrt_op node, Object data) {
		String nodeValueString = node.value.toString();
		System.out.print("sqrt");
		System.out.print(node.value);

		return data;
	}

	/**
	* Bool_op
	* @prints EXPRESION BOOLEANOPERATOR EXPRESSION
	* @hint All booleanoperators that is written with text e.g. "AND" is changed to the corresponding symbols e.g. "&&"
	**/
	public Object visit(ASTBool_op node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(" ");

		String nodeValueString = node.value.toString();
		if("AND".equals(nodeValueString)) { System.out.print("&& "); }
		else if("EQUALS".equals(nodeValueString)) {System.out.print("== ");}
		else if("NOT".equals(nodeValueString)) {System.out.print("!");}
		else if("NOTEQUALS".equals(nodeValueString)) {System.out.print("!= ");}
		else if("OR".equals(nodeValueString)) {System.out.print("|| ");}

		node.jjtGetChild(1).jjtAccept(this, data);

		return data;
	}

	public Object visit(ASTMult_op node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(" " + node.value + " ");
		int i = node.jjtGetNumChildren();
		if(i > 1) {
			node.jjtGetChild(1).jjtAccept(this, data);
		}

		return data;		
	}
	
    public Object visit(ASTIdentifier node, Object data) {
    	System.out.print(node.value);

    	return data;
    }

    public Object visit(ASTStringText node, Object data) {
    	System.out.print(node.value);

    	return data;
    }
    public Object visit(ASTBooleanNumber node, Object data) {
    	String number = node.value.toString();

    	// Checks if the value is (0) or (1)
		if("[0]".equals(number)) {
			System.out.print("0");
		} else if("[1]".equals(number)) {
			System.out.print("1");
		}

    	return data;
    }

    public Object visit(ASTNumber node, Object data) {
    	System.out.print(node.value);

    	return data;
    }
}