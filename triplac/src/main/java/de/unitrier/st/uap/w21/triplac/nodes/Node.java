package de.unitrier.st.uap.w21.triplac.nodes;

import de.unitrier.st.uap.w21.triplac.visitor.IVisitor;
import org.apache.commons.lang.SerializationUtils;

import java.io.Serializable;
import java.util.LinkedList;

public abstract class Node implements IAcceptor, Serializable
{
    private String type;
    private Object attribute;
    private LinkedList<Node> children;

    public Node(String type)
    {
        this.type = type;
        this.attribute = null;
        this.children = new LinkedList<>();
    }

    public Node(String type, Object attribute)
    {
        this.type = type;
        this.attribute = attribute;
        this.children = new LinkedList<>();
    }

    public String getType()
    {
        return this.type;
    }

    public Object getAttribute()
    {
        return this.attribute;
    }

    public LinkedList<Node> getChildren()
    {
        return this.children;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setAttribute(Object attribute)
    {
        this.attribute = attribute;
    }

    public void setChildren(LinkedList<Node> children)
    {
        this.children = children;
    }

    public void addChild(Node child)
    {
        this.children.add(child);
    }

    public void addChildren(LinkedList<Node> children)
    {
        this.children.addAll(children);
    }

    private String startTag()
    {
        String tag = "<" + type;

        if (attribute != null)
        {
            tag += " attr=\"" + attribute + "\"";
        }

        tag += ">";

        return tag;
    }

    private String endTag()
    {
        return "</" + type + ">";
    }

    @Override
    public String toString()
    {
        StringBuilder str = new StringBuilder(startTag());
        for (Node node : children)
        {
            str.append(node.toString());
        }
        str.append(endTag());
        return str.toString();
    }

    @Override
    public void accept(IVisitor v){
        v.visit(this);
    }

    public Node deepCopy() {
        return (Node) SerializationUtils.clone(this);
    }
}
