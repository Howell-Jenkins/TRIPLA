
package de.unitrier.st.uap.w21.triplac.nodes;

import de.unitrier.st.uap.w21.triplac.visitor.IVisitor;

public class ThenNode extends Node
{
    public ThenNode(Node n)
    {
        super("THEN");
        addChild(n);
    }

    @Override
    public void accept(IVisitor v){
        v.visit(this);
    }
}
