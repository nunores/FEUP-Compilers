import pt.up.fe.comp.jmm.JmmParser;
import pt.up.fe.comp.jmm.JmmParserResult;
import pt.up.fe.comp.jmm.analysis.JmmAnalysis;
import pt.up.fe.comp.jmm.report.Report;
import pt.up.fe.comp.jmm.report.ReportType;
import pt.up.fe.comp.jmm.report.Stage;
import pt.up.fe.comp.jmm.analysis.JmmSemanticsResult;
import pt.up.fe.comp.jmm.JmmNode;
import pt.up.fe.comp.jmm.analysis.table.SymbolTable;
import pt.up.fe.comp.MainAnalysis;
import pt.up.fe.comp.jmm.analysis.JmmAnalysis;
import pt.up.fe.comp.jmm.ollir.*;
import pt.up.fe.comp.jmm.jasmin.JasminResult;

import java.util.List;
import java.util.ArrayList;
import java.io.StringReader;
import java.io.*;
import java.nio.file.*;

public class Main implements JmmParser {

	public String readFile(String filePath) {
		try {
			Path path = Path.of(filePath);
			return Files.readString(path);	
		} catch (IOException e) {
			return null;
		}
	}

	public JmmParserResult parse(String jmmCode) {
		Parser parser = new Parser(new StringReader(jmmCode));

		try {
    		SimpleNode root = parser.Program(); // returns reference to root node
            	
    		root.dump(""); // prints the tree on the screen
			//System.out.println(root.toJson());

    		return new JmmParserResult(root, parser.getReports());
		} catch(ParseException e) {
			Report r = new Report(ReportType.ERROR, Stage.SYNTATIC, 0, e.getMessage());
			parser.getReports().add(r);

			return new JmmParserResult(null, parser.getReports());
		}
	}

    public static void main(String[] args) {
		if (args.length != 1) { return; }

		Main compiler = new Main();
		String filename = compiler.readFile(args[0]);
		JmmParserResult parserResult = compiler.parse(filename);

		if(!parserResult.getReports().isEmpty()){
			System.out.println(parserResult.getReports());
			return;
		}

		AnalysisStage as = new AnalysisStage();
		JmmSemanticsResult semanticsResult = as.semanticAnalysis(parserResult);

		if(!semanticsResult.getReports().isEmpty()){
			System.out.println(semanticsResult.getReports());
			return;
		}

		OptimizationStage os = new OptimizationStage();
		OllirResult ollirResult = os.toOllir(semanticsResult);
		System.out.println("\nOLLIR Code generated with success.\n");

		if(!ollirResult.getReports().isEmpty()){
			System.out.println(ollirResult.getReports());
			return;
		}

		BackendStage bs = new BackendStage();
		JasminResult jasminResult = bs.toJasmin(ollirResult);
		compiler.compile(jasminResult, filename);
	}

	public void compile(JasminResult jasminResult, String filename){
		jasminResult.run();
	}
}
