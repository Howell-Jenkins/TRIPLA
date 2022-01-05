
package de.unitrier.st.uap.w21.triplac.nodes;

import de.unitrier.st.uap.w21.triplac.visitor.IVisitor;

public class CondNode extends Node
{
    public CondNode(Node n)
    {
        super("COND");
        addChild(n);
    }

    @Override
    public void accept(IVisitor v){
        v.visit(this);
    }
}
