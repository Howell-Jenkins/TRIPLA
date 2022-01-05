package de.unitrier.st.uap.w21.triplac.visitor;

import de.unitrier.st.uap.w21.triplac.nodes.*;
import de.unitrier.st.uap.w21.triplac.parser.sym;

import java.util.ListIterator;
import java.util.Stack;

public class TerminalVisitor extends DefaultVisitor{
    public final StringBuilder output = new StringBuilder();
    Stack<Boolean> leafPrefix = new Stack<>();

    private void appendPrefix(){
        for (ListIterator<Boolean> iter = leafPrefix.listIterator(); iter.hasNext(); ) {
            if(iter.next()) {
                if(iter.hasNext()){
                    output.append("│   ");
                }else{
                    output.append("├── ");
                }
            }else{
                if(iter.hasNext()){
                    output.append("    ");
                }else{
                    output.append("└── ");
                }
            }

        }
    }

    protected void permute(Node node, String opt){
        if(leafPrefix.empty()){
            output.setLength(0);
        }
        appendPrefix();
        output.append(opt);
        output.append('\n');
        for (ListIterator<Node> iter = node.getChildren().listIterator(); iter.hasNext(); ) {
            Node element = iter.next();
            if(iter.hasNext()){
                leafPrefix.push(true);
            }else{
                leafPrefix.push(false);
            }
            element.accept(this);
            leafPrefix.pop();
        }
    }

    @Override
    protected void permute(Node node){
        permute(node,node.getType());
    }

    @Override
    public void visit(ConstNode node) {
        permute(node,"CONST:" + node.getAttribute().toString());
    }

    @Override
    public void visit(IDNode node) {
        permute(node,"ID:" + node.getAttribute().toString());
    }

    @Override
    public void visit(OpNode node) {
        String label = "";
        switch ((int)node.getAttribute()) {
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
        permute(node,"OP:" + label);
    }

}
