package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;

public class MJParserTest {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static void main(String[] args) throws Exception {
		
		Logger log = Logger.getLogger(MJParserTest.class);
		
		Reader br = null;
		try {
			File sourceCode = new File("test/test4A.mj");
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			
			MJParser p = new MJParser(lexer);
	        Symbol s = p.parse();  //pocetak parsiranja
	        
	        log.info("Num global var " + p.numGlobVar);
	        log.info("Num local var in main" + p.numLocalVar);
	        log.info("Num global const " + p.numGlobConst);
	        log.info("Num global arrays " + p.numGlobArrays);
	        log.info("Num global func " + p.numGlobFunc);
	        log.info("Num static func " + p.numStaticFunc);
	        log.info("Num func calls in Main " + p.numFuncCallsInMain);
	        log.info("Num formal arguments " + p.numFormalArguments);
	        
	        Tab.dump();
	        
	        if (!p.errorDetected) {
	        	File objFile = new File("test/program.obj");
	        	if (objFile.exists())
	        		objFile.delete();
	        	Code.write(new FileOutputStream(objFile));
	        	
	        	log.info("Parsiranje uspesno zavrseno!");
	        }
	        else {
	        	log.error("Parsiranje NIJE uspesno zavrseno!");
	        }
	        
		} 
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}

	}
	
	
}
