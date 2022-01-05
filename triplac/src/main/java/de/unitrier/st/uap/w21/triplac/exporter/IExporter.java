package de.unitrier.st.uap.w21.triplac.exporter;
import de.unitrier.st.uap.w21.triplac.nodes.Node;

interface IExporter <T> {
    T export(Node n);
}
