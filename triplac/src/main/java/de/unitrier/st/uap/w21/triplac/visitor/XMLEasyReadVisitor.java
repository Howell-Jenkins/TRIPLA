package de.unitrier.st.uap.w21.triplac.visitor;

import de.unitrier.st.uap.w21.triplac.nodes.Node;

public class XMLEasyReadVisitor extends XMLVisitor{

    @Override
    protected String startTag(Node node) {
        String tag = indent(count) + "<" + node.getType();
        if (node.getAttribute() != null) {
            tag += " attr=\"" + node.getAttribute() + "\"";
        }
        if(node.getChildren().size() == 0){
            tag += " />\n";
        }else{
            tag += ">\n";
        }
        return tag;
    }
    @Override
    protected String endTag(Node node)
    {
        if(node.getChildren().size() == 0){
            return "";
        }else{
            return indent(count) + "</" + node.getType() + ">\n";
        }

    }

}
