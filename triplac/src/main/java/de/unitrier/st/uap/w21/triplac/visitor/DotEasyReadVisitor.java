package de.unitrier.st.uap.w21.triplac.visitor;

import de.unitrier.st.uap.w21.triplac.nodes.ReadNode;

public class DotEasyReadVisitor extends DotVisitor{

    @Override
    public void visit(ReadNode node) {
        node.getChildren().getFirst().accept(this);
    }
}
