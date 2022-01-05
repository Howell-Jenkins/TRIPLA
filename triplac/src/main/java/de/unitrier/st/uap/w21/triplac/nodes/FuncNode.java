
package de.unitrier.st.uap.w21.triplac.nodes;

import de.unitrier.st.uap.w21.triplac.visitor.IVisitor;

public class FuncNode extends Node
{
    public FuncNode(Node n, Node m, Node o)
    {
        super("FUNC");
        addChild(n);
        addChild(m);
        addChild(o);
    }

    @Override
    public void accept(IVisitor v){
        v.visit(this);
    }
}
