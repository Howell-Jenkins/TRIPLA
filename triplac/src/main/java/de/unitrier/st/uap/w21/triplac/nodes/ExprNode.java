
package de.unitrier.st.uap.w21.triplac.nodes;

import de.unitrier.st.uap.w21.triplac.visitor.IVisitor;

public class ExprNode extends Node
{
    public ExprNode(Node n)
    {
        super("EXPR");
        addChild(n);
    }

    @Override
    public void accept(IVisitor v){
        v.visit(this);
    }
}
