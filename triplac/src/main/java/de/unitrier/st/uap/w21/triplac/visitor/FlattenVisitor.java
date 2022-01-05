package de.unitrier.st.uap.w21.triplac.visitor;

import de.unitrier.st.uap.w21.triplac.nodes.*;

import java.util.ListIterator;

public class FlattenVisitor extends DefaultVisitor{

    private void flatten(Node node){
        boolean flattened = true;
        while(flattened){
            flattened = false;
            for (final ListIterator<Node> i = node.getChildren().listIterator(); i.hasNext();) {
                final Node n = i.next();
                if (node.getType().equals(n.getType())) {
                    i.remove();
                    for (Node m:n.getChildren()) {
                        i.add(m);
                    }
                    flattened = true;
                }
            }
        }
    }

    @Override
    public void visit(ArgsNode node) {
        flatten(node);
        permute(node);
    }

    @Override
    public void visit(DefNode node) {
        flatten(node);
        permute(node);
    }

    @Override
    public void visit(ParamsNode node) {
        flatten(node);
        permute(node);
    }

    @Override
    public void visit(SemiNode node) {
        flatten(node);
        permute(node);
    }

}
