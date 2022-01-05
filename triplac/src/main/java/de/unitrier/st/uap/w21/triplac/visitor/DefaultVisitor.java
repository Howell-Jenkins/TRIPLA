package de.unitrier.st.uap.w21.triplac.visitor;

import de.unitrier.st.uap.w21.triplac.nodes.*;

abstract public class DefaultVisitor implements IVisitor {

    protected void permute(Node node){
        for (Node n:node.getChildren()) {
            n.accept(this);
        }
    }

    @Override
    public void visit(Node node) {
        node.accept(this);
    }

    @Override
    public void visit(ArgsNode node) {
        permute(node);
    }

    @Override
    public void visit(AssignNode node) {
        permute(node);
    }

    @Override
    public void visit(BodyNode node) {
        permute(node);
    }

    @Override
    public void visit(CallNode node) {
        permute(node);
    }

    @Override
    public void visit(CondNode node) {
        permute(node);
    }

    @Override
    public void visit(ConstNode node) {
        permute(node);
    }

    @Override
    public void visit(DefNode node) {
        permute(node);
    }

    @Override
    public void visit(ElseNode node) {
        permute(node);
    }

    @Override
    public void visit(ExprNode node) {
        permute(node);
    }

    @Override
    public void visit(FuncNode node) {
        permute(node);
    }

    @Override
    public void visit(IDNode node) {
        permute(node);
    }

    @Override
    public void visit(IfNode node) {
        permute(node);
    }

    @Override
    public void visit(LetNode node) {
        permute(node);
    }

    @Override
    public void visit(OpNode node) {
        permute(node);
    }

    @Override
    public void visit(ParamsNode node) {
        permute(node);}

    @Override
    public void visit(ParNode node) {
        permute(node);}

    @Override
    public void visit(ReadNode node) {
        permute(node);}

    @Override
    public void visit(SemiNode node) {
        permute(node);
    }

    @Override
    public void visit(ThenNode node) {
        permute(node);}

    @Override
    public void visit(WhileNode node) {
        permute(node);
    }
}
