
package de.unitrier.st.uap.w21.triplac.nodes;

import de.unitrier.st.uap.w21.triplac.visitor.IVisitor;

public class ConstNode extends Node
{
    public ConstNode(Integer v)
    {
        super("CONST", v);
    }

    @Override
    public void accept(IVisitor v){
        v.visit(this);
    }
}
