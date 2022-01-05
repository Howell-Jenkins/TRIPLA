package de.unitrier.st.uap.w21.triplac.code;

public class LabelAddressPair extends AddressPair {
    public LabelAddressPair(TramLabel loc, int nl) {
        super(loc, nl);
    }

    @Override
    public int calculate(IAddressCalculator i) {
        return i.calculate(this);
    }
}
