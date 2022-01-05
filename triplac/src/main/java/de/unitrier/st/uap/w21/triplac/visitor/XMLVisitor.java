package de.unitrier.st.uap.w21.triplac.visitor;

import de.unitrier.st.uap.w21.triplac.nodes.Node;

public class XMLVisitor extends DefaultVisitor{
    public final StringBuilder output = new StringBuilder();
    int count;

    protected String startTag(Node node) {
        String tag =  indent(count) + "<" + node.getType();
        if (node.getAttribute() != null) {
            tag += " attr=\"" + node.getAttribute() + "\"";
        }
        tag += ">\n";
        return tag;
    }
    protected String endTag(Node node)
    {
        return  indent(count) + "</" + node.getType() + ">\n";
    }

    String indent(int count){
        return new String(new char[count]).replace("\0", "  ");
    }

    @Override
    protected void permute(Node node){
        if(count == 0){
            output.setLength(0);
            output.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n");
        }
        output.append(startTag(node));
        count++;
        for (Node n:node.getChildren()) {
            n.accept(this);
        }
        count--;
        output.append(endTag(node));
    }
}
