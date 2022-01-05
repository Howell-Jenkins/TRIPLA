package de.unitrier.st.uap.w21.tram;
import org.apache.logging.log4j.*;

import java.util.List;
import java.util.Stack;

public class AbstractMachine {
    private static int count = 0;
    private static final Logger logger = LogManager.getLogger("AbstractMachine "+ count++);

    private Stack<Integer> stack;
    private int programCounter;
    private int parameterPointer;
    private int framePointer;


    public AbstractMachine() {
        stack = new Stack<Integer>();
        programCounter = 0;
        parameterPointer = 0;
        framePointer = 0;
    }

    private void execute(Instruction i){
        switch (i.getOpcode()) {
            case Instruction.CONST:
                doConst(i.getArg1());
                break;
            case Instruction.LOAD:
                doLoad(i.getArg1(), i.getArg2());
                break;
            case Instruction.STORE:
                doStore(i.getArg1(), i.getArg2());
                break;
            case Instruction.ADD:
                doAdd();
                break;
            case Instruction.SUB:
                doSub();
                break;
            case Instruction.MUL:
                doMult();
                break;
            case Instruction.DIV:
                doDiv();
                break;
            case Instruction.LT:
                doLt();
                break;
            case Instruction.GT:
                doGt();
                break;
            case Instruction.EQ:
                doEq();
                break;
            case Instruction.NEQ:
                doNeq();
                break;
            case Instruction.IFZERO:
                doIfzero(i.getArg1());
                break;
            case Instruction.GOTO:
                doGoTo(i.getArg1());
                break;
            case Instruction.HALT:
                doHalt();
                break;
            case Instruction.NOP:
                doNop();
                break;
            case Instruction.INVOKE:
                doInvoke(i.getArg1(), i.getArg2(), i.getArg3());
                break;
            case Instruction.RETURN:
                doReturn();
                break;
            case Instruction.POP:
                doPop();
                break;
            default:
                doError();
                logger.error("Instruction not found");
                break;
        }
        if(logger.isDebugEnabled()){
            debugStack(i);
        }
    }

    public int execute(List<Instruction> instructionList){
        while (programCounter >= 0) {
            Instruction i = instructionList.get(programCounter);
            execute(i);
        }
        return stack.peek();
    }

    public int execute(Instruction[] instructions) {
        while (programCounter >= 0) {
            Instruction i = instructions[programCounter];
            execute(i);
        }
        return stack.peek();
    }

    private int getTop(){
        return stack.size()-1;
    }

    public int getResult(){
        return stack.peek();
    }

    private void doConst(int k) {
        stack.push(k);
        programCounter++;
    }

    private void doAdd() {
        int element = stack.pop();
        stack.set(stack.size() - 1, stack.peek() + element);
        programCounter++;
    }

    private void doSub() {
        int element = stack.pop();
        stack.set(stack.size() - 1, stack.peek() - element);
        programCounter++;
    }

    private void doMult() {
        int element = stack.pop();
        stack.set(stack.size() - 1, stack.peek() * element);
        programCounter++;
    }

    private void doDiv() {
        int element = stack.pop();
        stack.set(stack.size() - 1, stack.peek() / element);
        programCounter++;
    }

    private void doLt() {
        int element = stack.pop();
        stack.set(stack.size() - 1, stack.peek() < element ? 1 : 0);
        programCounter++;
    }

    private void doGt() {
        int element = stack.pop();
        stack.set(stack.size() - 1, stack.peek() > element ? 1 : 0);
        programCounter++;
    }

    private void doEq() {
        int element = stack.pop();
        stack.set(stack.size() - 1, stack.peek() == element ? 1 : 0);
        programCounter++;
    }

    private void doNeq() {
        int element = stack.pop();
        stack.set(stack.size() - 1, stack.peek() != element ? 1 : 0);
        programCounter++;
    }

    private void doGoTo(int p) {
        programCounter = p;
    }

    private void doIfzero(int p) {
        programCounter = stack.pop() == 0 ? p : programCounter + 1;
    }

    private void doHalt() {
        programCounter = -1;
    }

    private void doNop() {
        programCounter++;
    }

    private void doInvoke(int n, int p, int d) {
        int top = stack.size();
        stack.push(programCounter + 1);
        stack.push(parameterPointer);
        stack.push(framePointer);
        stack.push(spp(d, parameterPointer, framePointer));
        stack.push(sfp(d, parameterPointer, framePointer));
        parameterPointer = top - n;
        framePointer = top;
        programCounter = p;

    }

    private int spp(int d, int pp, int fp) {
        return d == 0 ? pp : spp(d - 1, stack.get(fp + 3), stack.get(fp + 4));
    }

    private int sfp(int d, int pp, int fp) {
        return d == 0 ? fp : spp(d - 1, stack.get(fp + 3), stack.get(fp + 4));
    }

    private void doReturn() {
        int element = stack.pop();
        int top = parameterPointer;
        programCounter = stack.get(framePointer);
        parameterPointer = stack.get(framePointer + 1);
        framePointer = stack.get(framePointer + 2);
        stack.setSize(top);
        stack.push(element);
    }

    private void doStore(int k, int d) {
        int index = spp(d, parameterPointer, framePointer) + k;
        if(index != stack.size()-1){
            stack.set(index, stack.pop());
        }
        programCounter++;
    }

    private void doLoad(int k, int d) {
        int index = spp(d, parameterPointer, framePointer) + k;
        stack.push(stack.get(index));
        programCounter++;
    }

    private void doPop() {
        stack.pop();
        programCounter++;
    }

    private void doError() {
        programCounter = -2;
    }

    String indent(int count){
        return new String(new char[count]).replace("\0", " ");
    }

    // DEBUG INSTRUCTIONS
    private void debugStack( Instruction i){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("After instructruction [")
                .append(i.toString()).append("]; configuration [")
                .append("PC ").append(programCounter).append(", ")
                .append("PP ").append(parameterPointer).append(", ")
                .append("FP ").append(framePointer).append(", ")
                .append("TOP ").append(getTop()).append(" ")
                .append("]").append("\nStack:\n");
        for (int j = 0;j<stack.size();j++) {
            stringBuilder.append("[").append(j).append("] = ").append(stack.get(j));
            if(programCounter == j || parameterPointer == j || framePointer == j ||getTop()  == j){
                int l1 = j>0?(int) (Math.log10(j) + 1):1;
                int l2 = stack.get(j)>0?(int) (Math.log10(stack.get(j)) + 1):1;
                stringBuilder.append(indent((int)Math.max(10-(l1+l2),0))).append("<---- (");
            }
            if(programCounter == j){
                stringBuilder.append(" PC = ").append(programCounter).append(",");
            }
            if(parameterPointer == j){
                stringBuilder.append(" PP = ").append(parameterPointer).append(",");
            }
            if(framePointer == j){
                stringBuilder.append(" FP = ").append(framePointer).append(",");
            }
            if(getTop() == j){
                stringBuilder.append(" TOP = ").append(getTop()).append(",");
            }

            if(programCounter == j || parameterPointer == j || framePointer == j ||getTop()  == j){
                stringBuilder.deleteCharAt(stringBuilder.length()-1);
                stringBuilder.append(" )");
            }

            stringBuilder.append("\n");
        }
        logger.debug(stringBuilder.toString());
    }
}
