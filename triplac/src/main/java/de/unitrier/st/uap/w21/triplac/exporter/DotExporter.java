package de.unitrier.st.uap.w21.triplac.exporter;

import de.unitrier.st.uap.w21.triplac.nodes.Node;
import de.unitrier.st.uap.w21.triplac.visitor.FlattenVisitor;
import de.unitrier.st.uap.w21.triplac.visitor.DotVisitor;
import de.unitrier.st.uap.w21.triplac.visitor.DotEasyReadVisitor;

public class DotExporter implements IExporter<String> {

    @Override
    public String export(Node n) {
        DotVisitor v = new DotVisitor();
        v.visit(n);
        return v.output.toString();
    }

    public String export(Node n, boolean easyRead) {
        if(!easyRead){
            return export(n);
        }
        FlattenVisitor f = new FlattenVisitor();
        DotVisitor d = new DotEasyReadVisitor();
        Node clone = n.deepCopy();
        f.visit(clone);
        d.visit(clone);
        return d.output.toString();
    }
}
