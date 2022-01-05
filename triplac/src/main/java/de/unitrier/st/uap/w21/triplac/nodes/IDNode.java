package de.unitrier.st.uap.w21.triplac.nodes;

import de.unitrier.st.uap.w21.triplac.visitor.IVisitor;

public class IDNode extends Node
{
    public IDNode(String v)
    {
        super("ID", v);
    }

    @Override
    public void accept(IVisitor v){
        v.visit(this);
    }
}
