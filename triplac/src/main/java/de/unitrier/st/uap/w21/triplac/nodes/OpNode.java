
package de.unitrier.st.uap.w21.triplac.nodes;
import de.unitrier.st.uap.w21.triplac.visitor.IVisitor;
import de.unitrier.st.uap.w21.triplac.parser.sym;
public class OpNode extends Node implements sym
{
    public OpNode(Node n, Node m, int s)
    {
        super("OP", s);
        addChild(n);
        addChild(m);
    }

    @Override
    public void accept(IVisitor v){
        v.visit(this);
    }

}
