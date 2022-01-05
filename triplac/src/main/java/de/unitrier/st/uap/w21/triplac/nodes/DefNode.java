
package de.unitrier.st.uap.w21.triplac.nodes;

import de.unitrier.st.uap.w21.triplac.visitor.IVisitor;

public class DefNode extends Node
{
    public DefNode(Node n)
    {
        super("DEF");
        addChild(n);
    }

    public DefNode(Node n, Node m)
    {
        super("DEF");
        addChild(n);
        addChild(m);
    }

    @Override
    public void accept(IVisitor v){
        v.visit(this);
    }
}
