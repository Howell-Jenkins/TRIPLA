
package de.unitrier.st.uap.w21.triplac.nodes;

import de.unitrier.st.uap.w21.triplac.visitor.IVisitor;

public class ReadNode extends Node
{
    public ReadNode(Node n)
    {
        super("READ");
        addChild(n);
    }

    @Override
    public void accept(IVisitor v){
        v.visit(this);
    }
}
