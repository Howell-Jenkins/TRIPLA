
package de.unitrier.st.uap.w21.triplac.nodes;

import de.unitrier.st.uap.w21.triplac.visitor.IVisitor;

public class ParNode extends Node
{
    public ParNode(Node n)
    {
        super("PAR");
        addChild(n);
    }

    @Override
    public void accept(IVisitor v){
        v.visit(this);
    }
}
