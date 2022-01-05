package de.unitrier.st.uap.w21.triplac.nodes;

import de.unitrier.st.uap.w21.triplac.visitor.IVisitor;

public class WhileNode extends Node
{
    public WhileNode(Node n, Node m)
    {
        super("WHILE");
        addChild(n);
        addChild(m);
    }

    @Override
    public void accept(IVisitor v){
        v.visit(this);
    }
}
