
package de.unitrier.st.uap.w21.triplac.nodes;

import de.unitrier.st.uap.w21.triplac.visitor.IVisitor;

public class ArgsNode extends Node
{
    public ArgsNode(Node n)
    {
        super("ARGS");
        addChild(n);
    }

    public ArgsNode(Node n, Node m)
    {
        super("ARGS");
        addChild(n);
        addChild(m);
    }

    @Override
    public void accept(IVisitor v){
        v.visit(this);
    }
}
