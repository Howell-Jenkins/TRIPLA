package de.unitrier.st.uap.w21.triplac.visitor;

import de.unitrier.st.uap.w21.triplac.nodes.*;

public interface IVisitor {
    void visit (Node node);
    void visit(ArgsNode node);
    void visit(AssignNode node);
    void visit(BodyNode node);
    void visit(CallNode node);
    void visit(CondNode node);
    void visit(ConstNode node);
    void visit(DefNode node);
    void visit(ElseNode node);
    void visit(ExprNode node);
    void visit(FuncNode node);
    void visit(IDNode node);
    void visit(IfNode node);
    void visit(LetNode node);
    void visit(OpNode node);
    void visit(ParamsNode node);
    void visit(ParNode node);
    void visit(ReadNode node);
    void visit(SemiNode node);
    void visit(ThenNode node);
    void visit(WhileNode node);

}
