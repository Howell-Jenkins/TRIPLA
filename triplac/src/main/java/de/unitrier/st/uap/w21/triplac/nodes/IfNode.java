
package de.unitrier.st.uap.w21.triplac.nodes;

import de.unitrier.st.uap.w21.triplac.visitor.IVisitor;

public class IfNode extends Node
{
    public IfNode(Node n, Node m, Node o)
    {
        super("IF");
        addChild(n);
        addChild(m);
        addChild(o);
    }

    @Override
    public void accept(IVisitor v){
        v.visit(this);
    }
}
