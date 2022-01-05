package de.unitrier.st.uap.w21.triplac.visitor;

import de.unitrier.st.uap.w21.tram.Instruction;
import de.unitrier.st.uap.w21.triplac.code.*;
import de.unitrier.st.uap.w21.triplac.nodes.*;
import de.unitrier.st.uap.w21.triplac.parser.sym;
import java.util.*;

public class TramCodeVisitor extends DefaultVisitor implements IAddressCalculator {
    public final List<Instruction> output = new ArrayList<>();
    private final Stack<HashMap<String, AddressPair>> rho = new Stack<>();
    private final List<TramLabel> labels = new ArrayList<>();
    private int nl = 0;

    @Override
    public int calculate(IntegerAddressPair addressPair) {
        return (int) addressPair.getLoc();
    }

    @Override
    public int calculate(LabelAddressPair addressPair) {
        return output.indexOf(((TramLabel) addressPair.getLoc()).to)+1;
    }

    private TramLabel addLabeledInstruction(Instruction instruction){
        TramLabel l = new TramLabel(instruction);
        output.add(instruction);
        labels.add(l);
        return l;

    }
    private void addInstruction(Instruction instruction){
        output.add(instruction);
    }

    private void replaceLabel(){
        for (TramLabel l: labels) {
            if(l.from.getOpcode() == Instruction.INVOKE){
                l.from.setArg2(output.indexOf(l.to)+1);
            }else{
                l.from.setArg1(output.indexOf(l.to)+1);
            }
        }
    }

    private void clear(){
        rho.clear();
        labels.clear();
        nl = 0;
        rho.push(new HashMap<String, AddressPair>());
    }

    @Override
    protected void permute(Node node){
        for (Node n:node.getChildren()) {
            n.accept(this);
        }
    }

    @Override
    public void visit(Node node) {
        clear();
        FlattenVisitor f  = new FlattenVisitor();
        f.visit(node);
        node.accept(this);
        replaceLabel();
        output.add(new Instruction(Instruction.HALT));
    }

    @Override
    public void visit(AssignNode node) {
        AddressPair pair = rho.peek().get(node.getChildren().get(0).toString());
        node.getChildren().get(1).accept(this);
        int loc = pair.calculate(this);
        output.add(new Instruction(Instruction.STORE, loc, nl - pair.getNl()));
        output.add(new Instruction(Instruction.LOAD, loc, nl - pair.getNl()));
    }

    @Override
    public void visit(ConstNode node) {
        output.add(new Instruction(Instruction.CONST, (Integer) node.getAttribute()));
    }

    @Override
    public void visit(FuncNode node) {
        TramLabel label = addLabeledInstruction(new Instruction(Instruction.GOTO, -1));
        rho.peek().put(node.getChildren().get(0).toString(), new LabelAddressPair(label, nl));
        nl++;
        HashMap<String, AddressPair> hm = new HashMap<>(rho.peek());
        rho.push(hm);
        for (int j=0; j<node.getChildren().get(1).getChildren().size();j++) {
            rho.peek().put(node.getChildren().get(1).getChildren().get(j).toString(), new IntegerAddressPair(j, nl));
        }
        node.getChildren().get(2).accept(this);
        rho.pop();
        addInstruction(new Instruction(Instruction.RETURN));
        label.bind(output.get(output.size()-1));
        nl--;
    }

    @Override
    public void visit(IfNode node) {
        int i = 0;
        node.getChildren().get(0).accept(this);
        TramLabel l = addLabeledInstruction(new Instruction(Instruction.IFZERO));
        node.getChildren().get(1).accept(this);
        TramLabel m = addLabeledInstruction(new Instruction(Instruction.GOTO, -1));
        i = output.size()-1;
        node.getChildren().get(2).accept(this);
        l.bind(output.get(i));
        i = output.size()-1;
        addInstruction(new Instruction(Instruction.NOP));
        m.bind(output.get(i));
    }

    @Override
    public void visit(OpNode node) {
        node.getChildren().get(0).accept(this);
        node.getChildren().get(1).accept(this);
        switch ((int)node.getAttribute()){
            case sym.ADD: case sym.OR:
                addInstruction(new Instruction(Instruction.ADD));
                break;
            case sym.SUB:
                addInstruction(new Instruction(Instruction.SUB));
                break;
            case sym.DIV:
                addInstruction(new Instruction(Instruction.DIV));
                break;
            case sym.MUL: case sym.AND:
                addInstruction(new Instruction(Instruction.MUL));
                break;
            case sym.EQ:
                addInstruction(new Instruction(Instruction.EQ));
                break;
            case sym.GT:
                addInstruction(new Instruction(Instruction.GT));
                break;
            case sym.GTE:
                addInstruction(new Instruction(Instruction.GT));
                node.getChildren().get(0).accept(this);
                node.getChildren().get(1).accept(this);
                addInstruction(new Instruction(Instruction.EQ));
                addInstruction(new Instruction(Instruction.ADD));
                break;
            case sym.LT:
                addInstruction(new Instruction(Instruction.LT));
                break;
            case sym.LTE:
                addInstruction(new Instruction(Instruction.LT));
                node.getChildren().get(0).accept(this);
                node.getChildren().get(1).accept(this);
                addInstruction(new Instruction(Instruction.EQ));
                addInstruction(new Instruction(Instruction.ADD));
                break;
            case sym.NEQ:
                addInstruction(new Instruction(Instruction.NEQ));
                break;
            default:
                addInstruction(new Instruction(Instruction.NOP));
                break;
        }
    }

    @Override
    public void visit(ReadNode node) {
        AddressPair pair = rho.peek().get(node.getChildren().get(0).toString());
        int loc =  pair.calculate(this);
        output.add(new Instruction(Instruction.LOAD, loc, nl - pair.getNl()));
        permute(node);
    }

    @Override
    public void visit(SemiNode node) {
        for (ListIterator<Node> iter = node.getChildren().listIterator(); iter.hasNext(); ) {
            iter.next().accept(this);
            if(iter.hasNext()){
                output.add(new Instruction(Instruction.POP));
            }
        }
    }

    @Override
    public void visit(WhileNode node) {
        int i = Math.max(0,output.size()-1);
        node.getChildren().get(0).accept(this);
        TramLabel m = addLabeledInstruction(new Instruction(Instruction.IFZERO));
        //addInstruction(new Instruction(Instruction.POP));
        node.getChildren().get(1).accept(this);
        TramLabel l = addLabeledInstruction(new Instruction(Instruction.GOTO, -1));
        l.bind(output.get(i));
        addInstruction(new Instruction(Instruction.NOP));
        m.bind(output.get(output.size()-1));
    }

    @Override
    public void visit(CallNode node) {
        node.getChildren().get(1).accept(this);
        AddressPair pair = rho.peek().get(node.getChildren().get(0).toString());
        TramLabel label = addLabeledInstruction(new Instruction(Instruction.INVOKE, node.getChildren().get(1).getChildren().size(), -1, nl - pair.getNl()));
        label.bind(((TramLabel)pair.getLoc()).from);
    }

}
