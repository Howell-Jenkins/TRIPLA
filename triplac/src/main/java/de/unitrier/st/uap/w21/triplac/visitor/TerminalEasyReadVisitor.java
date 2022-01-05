package de.unitrier.st.uap.w21.triplac.visitor;

import de.unitrier.st.uap.w21.triplac.nodes.*;
import de.unitrier.st.uap.w21.triplac.parser.sym;

public class TerminalEasyReadVisitor extends TerminalVisitor {

    @Override
    public void visit(ConstNode node) {
        permute(node,node.getAttribute().toString());
    }

    @Override
    public void visit(IDNode node) {
        permute(node,node.getAttribute().toString());
    }

    @Override
    public void visit(OpNode node) {
        String label = "";
        switch ((int)node.getAttribute()){
            case sym.ADD:
                label = "+";
                break;
            case sym.SUB:
                label = "-";
                break;
            case sym.DIV:
                label = "/";
                break;
            case sym.MUL:
                label = "*";
                break;
            case sym.EQ:
                label = "==";
                break;
            case sym.GT:
                label = ">";
                break;
            case sym.GTE:
                label = ">=";
                break;
            case sym.LT:
                label = "<";
                break;
            case sym.LTE:
                label = "<=";
                break;
            case sym.AND:
                label = "&&";
                break;
            case sym.OR:
                label = "||";
                break;
            case sym.NEQ:
                label = "!=";
                break;
            default:
                label = "";
                break;
        }
        permute(node,label);
    }

    @Override
    public void visit(ReadNode node) {
        node.getChildren().getFirst().accept(this);
    }
}
