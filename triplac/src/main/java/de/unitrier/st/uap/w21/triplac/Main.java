package de.unitrier.st.uap.w21.triplac;

import java.io.*;

import de.unitrier.st.uap.w21.tram.AbstractMachine;
import de.unitrier.st.uap.w21.tram.Instruction;
import de.unitrier.st.uap.w21.triplac.exporter.DotExporter;
import de.unitrier.st.uap.w21.triplac.lex.Lexer;
import de.unitrier.st.uap.w21.triplac.nodes.Node;
import de.unitrier.st.uap.w21.triplac.parser.Parser;
import de.unitrier.st.uap.w21.triplac.exporter.*;
import de.unitrier.st.uap.w21.triplac.visitor.FlattenVisitor;
import de.unitrier.st.uap.w21.triplac.visitor.TramCodeVisitor;
import org.apache.commons.cli.*;

final class Main
{
    private Main(){}

    public static void main(String args[])
    {
        try
        {
            final CommandLineParser commandLineParser = new DefaultParser();
            final Options options = new Options();
            options.addOption("e", "expast", false, "Export in dot Format (./output.dot)");
            try {
                CommandLine commandLine = commandLineParser.parse(options, args);

                if(commandLine.getArgList().size() > 0){
                    String filePath = commandLine.getArgList().get(0);
                    Reader input = new FileReader(filePath);
                    Parser p = new Parser(new Lexer(input));

                    Node result = (Node)p.parse().value;
                    //System.out.println(result.toString());
                    FlattenVisitor f = new FlattenVisitor();
                    f.visit(result);

                    System.out.println(new TerminalExporter().export(result,false));
                    TramCodeVisitor t = new TramCodeVisitor();
                    t.visit(result);
                    for (Instruction i : t.output) {
                        System.out.println(i.toString());
                    }
                    AbstractMachine abstractMachine = new AbstractMachine();
                    abstractMachine.execute(t.output);
                    System.out.println("Result : " + abstractMachine.getResult());
                    //System.out.println(new XMLExporter().export(result,true));
                    //System.out.println(new XMLExporter().export(result));
                    if(commandLine.hasOption("e")){

                        DotExporter dotExporter = new DotExporter();
                        FileWriter output = new FileWriter("graph.dot");
                        output.write(dotExporter.export(result));
                        output.close();
                    }
                }else{
                    final HelpFormatter formatter = new HelpFormatter();
                    formatter.printHelp("[file]", options);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

