package de.unitrier.st.uap.w21.triplac.code;

public interface IAddressCalculator {
    int calculate(IntegerAddressPair addressPair);
    int calculate(LabelAddressPair addressPair);
}
