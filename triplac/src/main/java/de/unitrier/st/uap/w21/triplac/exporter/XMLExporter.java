package de.unitrier.st.uap.w21.triplac.exporter;

import de.unitrier.st.uap.w21.triplac.nodes.Node;
import de.unitrier.st.uap.w21.triplac.visitor.FlattenVisitor;
import de.unitrier.st.uap.w21.triplac.visitor.XMLVisitor;
import de.unitrier.st.uap.w21.triplac.visitor.XMLEasyReadVisitor;

public class XMLExporter implements  IExporter<String>{

    @Override
    public String export(Node n) {
        XMLVisitor v = new XMLVisitor();
        v.visit(n);
        return v.output.toString();
    }

    public String export(Node n, boolean easyRead) {
        if(!easyRead){
            return export(n);
        }
        FlattenVisitor f = new FlattenVisitor();
        XMLVisitor d = new XMLEasyReadVisitor();
        Node clone = n.deepCopy();
        f.visit(clone);
        d.visit(clone);
        return d.output.toString();
    }
}
