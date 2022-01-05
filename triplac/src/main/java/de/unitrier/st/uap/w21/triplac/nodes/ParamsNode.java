
package de.unitrier.st.uap.w21.triplac.nodes;

import de.unitrier.st.uap.w21.triplac.visitor.IVisitor;

public class ParamsNode extends Node
{
    public ParamsNode(Node n)
    {
        super("PARAMS");
        addChild(n);
    }

    public ParamsNode(Node n, Node m)
    {
        super("PARAMS");
        addChild(n);
        addChild(m);
    }

    @Override
    public void accept(IVisitor v){
        v.visit(this);
    }
}
