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

	public Object visit(ASTProgramLibInclude node, Object data) {
		System.out.print("#");
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(" <");
		node.jjtGetChild(1).jjtAccept(this, data);
		System.out.println(">");

		return data;
	}

	/*public Object visit(ASTProgramStart node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.println("");
		node.jjtGetChild(1).jjtAccept(this, data);

		return data;
	}*/

	public Object visit(ASTProgramSetup node, Object data) {
		System.out.print("void ");
		System.out.print(node.value);
		System.out.println(" { ");
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.println(" ");
		System.out.println("}");

		return data;
	}

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

	public Object visit(ASTEls_stm node, Object data) {
		System.out.println(" else {");
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.println("");
		System.out.println("}");

		return data;
	}

	public Object visit(ASTElsIf_stm node, Object data) {
		System.out.print("else if(");
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.println(") {");
		node.jjtGetChild(1).jjtAccept(this, data);
		System.out.println("");
		System.out.println("}");
	
		return data;
	}

	public Object visit(ASTWhileStatement node, Object data) {
		System.out.print("while (");
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.println(") {");
		node.jjtGetChild(1).jjtAccept(this, data);
		System.out.println("");
		System.out.println("}");

		return data;
	}


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
	public Object visit(ASTExpression node, Object data) {

		node.jjtGetChild(0).jjtAccept(this, data);

		for(int i = 1; i < node.jjtGetNumChildren(); i++) {
			node.jjtGetChild(i).jjtAccept(this, data);
		
		}

		return (data);
	}

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

	public Object visit(ASTAdd_op node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		System.out.print(" " + node.value + " ");
		node.jjtGetChild(1).jjtAccept(this, data);

		return data;
	}

	/*public Object visit(ASTSringAdd_op node, Object data) {
		node.jjtGetChild(0).jjtAccept(this, data);
		nonde.jjtGetChild(1).jjtAccept(this, data);

		return data;
	}*/

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
		} else {
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

    public Object visit(ASTNumber node, Object data) {
    	System.out.print(node.value);

    	return data;
    }
}