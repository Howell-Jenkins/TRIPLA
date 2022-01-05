package de.unitrier.st.uap.w21.triplac.exporter;

import de.unitrier.st.uap.w21.triplac.nodes.Node;
import de.unitrier.st.uap.w21.triplac.visitor.FlattenVisitor;
import de.unitrier.st.uap.w21.triplac.visitor.TerminalVisitor;
import de.unitrier.st.uap.w21.triplac.visitor.TerminalEasyReadVisitor;

public class TerminalExporter implements  IExporter<String>{

    @Override
    public String export(Node n) {
        TerminalVisitor v = new TerminalVisitor();
        v.visit(n);
        return v.output.toString();
    }

    public String export(Node n, boolean easyRead) {
        if(!easyRead){
            return export(n);
        }
        FlattenVisitor f = new FlattenVisitor();
        TerminalVisitor d = new TerminalEasyReadVisitor();
        Node clone = n.deepCopy();
        f.visit(clone);
        d.visit(clone);
        return d.output.toString();
    }
}
