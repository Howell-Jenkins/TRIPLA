
package de.unitrier.st.uap.w21.triplac.nodes;

import de.unitrier.st.uap.w21.triplac.visitor.IVisitor;

public class CallNode extends Node
{
    public CallNode(Node n, Node m)
    {
        super("CALL");
        addChild(n);
        addChild(m);
    }

    @Override
    public void accept(IVisitor v){
        v.visit(this);
    }
}
