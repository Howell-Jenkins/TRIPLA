package de.unitrier.st.uap.w21.tram;

import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.Stack;

public class TramTokenizer {
    private String filePath;
    private boolean hasError;
    private String errorMessage;
    private Stack<Instruction> instructions;

    public TramTokenizer(){
        filePath = "";
        hasError = false;
        errorMessage = "";
        instructions = new Stack<Instruction>();
    }

    public Instruction[] getInstructions() {
        return instructions.toArray(new Instruction[instructions.size()]);
    }

    private int tokenizerGetInt(StreamTokenizer tokenizer) throws IOException {
        if(!hasError) {
            if (tokenizer.nextToken() == StreamTokenizer.TT_NUMBER) {
                return (int) tokenizer.nval;
            } else {
                hasError = true;
                errorMessage = "Expected Number as an argument on line:" + (tokenizer.lineno() -1);
                return -1;
            }
        }
        return -1;
    }

    private void tokenizerGetEOL(StreamTokenizer tokenizer) throws IOException {
        if(!hasError){
            if(tokenizer.nextToken() != StreamTokenizer.TT_EOF){
                if(tokenizer.ttype != StreamTokenizer.TT_EOL){
                    hasError = true;
                    errorMessage = "Too many arguments on line:" + tokenizer.lineno();
                }
            }
        }

    }
    public String getFilePath(){
        return filePath;
    }

    public boolean parse(String file) {
        filePath = file;
        hasError = false;
        errorMessage = "";
        instructions.clear();

        FileReader reader ;
        try {
            reader = new FileReader(file);
            StreamTokenizer tokenizer = new StreamTokenizer(reader);
            tokenizer.eolIsSignificant(true);
            int args1,args2,args3;
            while(tokenizer.nextToken() != StreamTokenizer.TT_EOF && !hasError){
                if(tokenizer.ttype == StreamTokenizer.TT_WORD){
                    switch (tokenizer.sval){
                        case "CONST":
                            args1 = tokenizerGetInt(tokenizer);
                            if(!hasError){
                                instructions.push(new Instruction(Instruction.CONST,args1));
                            }
                            break;
                        case "LOAD":
                            args1 = tokenizerGetInt(tokenizer);
                            args2 = tokenizerGetInt(tokenizer);
                            if(!hasError){
                                instructions.push(new Instruction(Instruction.LOAD,args1,args2));
                            }
                            break;
                        case "STORE":
                            args1 = tokenizerGetInt(tokenizer);
                            args2 = tokenizerGetInt(tokenizer);
                            if(!hasError){
                                instructions.push(new Instruction(Instruction.STORE,args1,args2));
                            }
                            break;
                        case "ADD":
                            instructions.push(new Instruction(Instruction.ADD));
                            break;
                        case "SUB":
                            instructions.push(new Instruction(Instruction.SUB));
                            break;
                        case "MUL":
                            instructions.push(new Instruction(Instruction.MUL));
                            break;
                        case "DIV":
                            instructions.push(new Instruction(Instruction.DIV));
                            break;
                        case "LT":
                            instructions.push(new Instruction(Instruction.LT));
                            break;
                        case "GT":
                            instructions.push(new Instruction(Instruction.GT));
                            break;
                        case "EQ":
                            instructions.push(new Instruction(Instruction.EQ));
                            break;
                        case "NEQ":
                            instructions.push(new Instruction(Instruction.NEQ));
                            break;
                        case "IFZERO":
                            args1 = tokenizerGetInt(tokenizer);
                            instructions.push(new Instruction(Instruction.IFZERO,args1));
                            break;
                        case "GOTO":
                            args1 = tokenizerGetInt(tokenizer);
                            if(!hasError){
                                instructions.push(new Instruction(Instruction.GOTO,args1));
                            }
                            break;
                        case "HALT":
                            instructions.push(new Instruction(Instruction.HALT));
                            break;
                        case "NOP":
                            instructions.push(new Instruction(Instruction.NOP));
                            break;
                        case "INVOKE":
                            args1 = tokenizerGetInt(tokenizer);
                            args2 = tokenizerGetInt(tokenizer);
                            args3 = tokenizerGetInt(tokenizer);
                            if(!hasError){
                                instructions.push(new Instruction(Instruction.INVOKE,args1,args2,args3));
                            }
                            break;
                        case "RETURN":
                            instructions.push(new Instruction(Instruction.RETURN));
                            break;
                        case "POP":
                            instructions.push(new Instruction(Instruction.POP));
                            break;
                        default:
                            hasError = true;
                            errorMessage = "Unknown input " + tokenizer.sval + " on line " + tokenizer.lineno();
                            break;
                    }
                    tokenizerGetEOL(tokenizer);
                }else{
                    if(tokenizer.ttype != 0){
                        hasError = true;
                        errorMessage = "Error on line " + tokenizer.lineno();
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            hasError = true;
            errorMessage = e.getMessage();
        }

        if(hasError){
            instructions.clear();
        }
        return !hasError;
    }

    public boolean hasError(){
        return hasError;
    }

    public String getErrorMessage(){
        return errorMessage;
    }
}
