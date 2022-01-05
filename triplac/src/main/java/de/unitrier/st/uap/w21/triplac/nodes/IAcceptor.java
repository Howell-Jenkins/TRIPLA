package de.unitrier.st.uap.w21.triplac.nodes;

import de.unitrier.st.uap.w21.triplac.visitor.IVisitor;

public interface IAcceptor {
    void accept(IVisitor v);
}
