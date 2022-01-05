package de.unitrier.st.uap.w21.triplac.code;

public class IntegerAddressPair extends AddressPair  {
    public IntegerAddressPair(Integer loc, int nl) {
        super(loc, nl);
    }

    @Override
    public int calculate(IAddressCalculator i) {
        return i.calculate(this);
    }
}
