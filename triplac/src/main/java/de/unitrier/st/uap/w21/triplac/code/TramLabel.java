package de.unitrier.st.uap.w21.triplac.code;

import de.unitrier.st.uap.w21.tram.Instruction;

public class TramLabel {
    public final Instruction from;
    public Instruction to;
    public TramLabel(Instruction i) {
        from = i;
    }
    public void bind(Instruction instruction){
        to = instruction;
    }
}
