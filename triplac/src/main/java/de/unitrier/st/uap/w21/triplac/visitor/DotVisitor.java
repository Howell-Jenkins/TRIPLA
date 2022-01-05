package de.unitrier.st.uap.w21.triplac.visitor;

import de.unitrier.st.uap.w21.triplac.nodes.*;
import de.unitrier.st.uap.w21.triplac.parser.sym;

public class DotVisitor extends DefaultVisitor{
    private enum Shapes {
        DEFAULT("box"),
        OP("circle"),
        CONST("note");

        private final String text;
        Shapes(final String text) {
            this.text = text;
        }
        @Override
        public String toString() {
            return text;
        }
    }
    public final StringBuilder output = new StringBuilder();
    private final StringBuilder path = new StringBuilder() ;
    private final StringBuilder label = new StringBuilder();
    private int count;


    private void clear(){
        count = 0;
        label.setLength(0);
        path.setLength(0);
    }

    private void _permute(Node node, String opt){
        String nodeName = String.format("n%d",count++);
        label.append(String.format("%s %s;\n",nodeName, opt));
        if(node.getChildren().size()>0){
            for (Node child:node.getChildren()) {
                path.append(String.format("%s --",nodeName));
                child.accept(this);
            }
        }else{
            path.append(String.format("%s;\n",nodeName));
        }
    }
    protected void permute(Node node, String opt){
        if(count == 0) {
            output.setLength(0);
            output.append("graph syntaxGraph {\n");
            _permute(node, opt);
            output.append(label);
            output.append(path);
            output.append("}\n");
            clear();
        }else{
            _permute(node,opt);
        }
    }

    @Override
    protected void permute(Node node){
        permute(node,String.format("[label=\"%s\",shape=%s]",node.getType(),Shapes.DEFAULT));
    }

    @Override
    public void visit(ConstNode node) {
        permute(node,String.format("[label=\"%s\",shape=%s]",node.getAttribute(),Shapes.CONST));
    }

    @Override
    public void visit(IDNode node) {
        permute(node,String.format("[label=\"%s\",shape=%s]",node.getAttribute(),Shapes.OP));
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
            default:
                label = "";
                break;
        }
        permute(node,String.format("[label=\"%s\",shape=%s]",label,Shapes.OP));
    }

}
