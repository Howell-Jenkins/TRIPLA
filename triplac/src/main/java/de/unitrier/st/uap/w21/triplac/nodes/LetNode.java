
package de.unitrier.st.uap.w21.triplac.nodes;

import de.unitrier.st.uap.w21.triplac.visitor.IVisitor;

public class LetNode extends Node
{
    public LetNode(Node n, Node m)
    {
        super("LET");
        addChild(n);
        addChild(m);
    }

    @Override
    public void accept(IVisitor v){
        v.visit(this);
    }
}
