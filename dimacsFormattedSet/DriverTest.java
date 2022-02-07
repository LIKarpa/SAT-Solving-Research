package dimacsFormattedSet;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

class DriverTest {
	
	
	@Test
	void testSAT1HAM() {
		int i = 0;
		File uf2091 = new File("/Users/Liron/Downloads/uf20-91");
		String [] list = uf2091.list();
		
		for (String filename : list) {
			Set<Set<String>> formula = Driver.fileToFormula("/Users/Liron/Downloads/uf20-91/" + filename);
			assertTrue(Driver.fullHAMAlg(formula));
			System.out.println(i++);
		}
	}
	
	@Test
	void testSAT2HAM() {	
		int i = 0;
		File uf50218 = new File("/Users/Liron/Downloads/uf50-218");
		String [] list = uf50218.list();
		
		for (String filename : list) {
			Set<Set<String>> formula = Driver.fileToFormula("/Users/Liron/Downloads/uf50218/" + filename);
			assertTrue(Driver.fullHAMAlg(formula));
			System.out.println(i++);
		}
	}
	
	@Test
	void testUNSAT1HAM() {
		int i = 0;
		File uuf50218 = new File("/Users/Liron/Downloads/UUF50.218.1000");
		String [] list = uuf50218.list();
		
		for (String filename : list) {
			Set<Set<String>> formula = Driver.fileToFormula("/Users/Liron/Downloads/UUF50.218.1000/"+ filename);
			assertFalse(Driver.fullHAMAlg(formula));
			System.out.println(i++);
		}
	}
	

	@Test
	void testRandHAM() {
		int i = 0;
		File uf2091 = new File("/Users/Liron/Downloads/uf20-91");
		String [] list = uf2091.list();
		
		for (String filename : list) {
			Set<Set<String>> formula = Driver.fileToFormula("/Users/Liron/Downloads/uf20-91/" + filename);
			assertTrue(Driver.randInit(formula));
			System.out.println(i++);
		}
	}
	
	@Test
	void testUNSATRandHAM() {
		int i = 0;
		File uuf50218 = new File("/Users/Liron/Downloads/UUF50.218.1000");
		String [] list = uuf50218.list();
		
		for (String filename : list) {
			Set<Set<String>> formula = Driver.fileToFormula("/Users/Liron/Downloads/UUF50.218.1000/"+ filename);
			assertFalse(Driver.randInit(formula));
			System.out.println(i++);
		}
	}
	
	@Test
	void testSATDLCS() {
		int i = 0;
		File uf2091 = new File("/Users/Liron/Downloads/uf20-91");
		String [] list = uf2091.list();
		HashMap<String, String> emptyAssign = new HashMap<String, String>();
		LiteralSelector select = new dlcsSelection();
		
		for (String filename : list) {
			Set<Set<String>> formula = Driver.fileToFormula("/Users/Liron/Downloads/uf20-91/" + filename);
			assertTrue(Driver.DPPL(formula, emptyAssign, select));
			System.out.println(i++);
		}
	}
	
	@Test
	void testUNSATDLCS() {
		int i = 0;
		File uuf50218 = new File("/Users/Liron/Downloads/UUF50.218.1000");
		String [] list = uuf50218.list();
		HashMap<String, String> emptyAssign = new HashMap<String, String>();
		LiteralSelector select = new dlcsSelection();
		
		for (String filename : list) {
			Set<Set<String>> formula = Driver.fileToFormula("/Users/Liron/Downloads/UUF50.218.1000/"+ filename);
			assertFalse(Driver.DPPL(formula, emptyAssign, select));
			System.out.println(i++);
		}
	}
	
	@Test
	void testSATDLIS() {
		int i = 0;
		File uf2091 = new File("/Users/Liron/Downloads/uf20-91");
		String [] list = uf2091.list();
		HashMap<String, String> emptyAssign = new HashMap<String, String>();
		LiteralSelector select = new dlisSelection();
		
		for (String filename : list) {
			Set<Set<String>> formula = Driver.fileToFormula("/Users/Liron/Downloads/uf20-91/" + filename);
			assertTrue(Driver.DPPL(formula, emptyAssign, select));
			System.out.println(i++);
		}
	}

	@Test
	void testUNSATDLIS() {
		int i = 0;
		File uuf50218 = new File("/Users/Liron/Downloads/UUF50.218.1000");
		String [] list = uuf50218.list();
		HashMap<String, String> emptyAssign = new HashMap<String, String>();
		LiteralSelector select = new dlisSelection();
		
		for (String filename : list) {
			Set<Set<String>> formula = Driver.fileToFormula("/Users/Liron/Downloads/UUF50.218.1000/"+ filename);
			assertFalse(Driver.DPPL(formula, emptyAssign, select));
			System.out.println(i++);
		}
	}
	
	
	@Test
	void testSATSmallest() {
		int i = 0;
		File uf2091 = new File("/Users/Liron/Downloads/uf20-91");
		String [] list = uf2091.list();
		HashMap<String, String> emptyAssign = new HashMap<String, String>();
		LiteralSelector select = new shortestClauseSelection();
		
		for (String filename : list) {
			Set<Set<String>> formula = Driver.fileToFormula("/Users/Liron/Downloads/uf20-91/" + filename);
			assertTrue(Driver.DPPL(formula, emptyAssign, select));
			System.out.println(i++);
		}
	}

	@Test
	void testUNSATShortest() {
		int i = 0;
		File uuf50218 = new File("/Users/Liron/Downloads/UUF50.218.1000");
		String [] list = uuf50218.list();
		HashMap<String, String> emptyAssign = new HashMap<String, String>();
		LiteralSelector select = new shortestClauseSelection();
		
		for (String filename : list) {
			Set<Set<String>> formula = Driver.fileToFormula("/Users/Liron/Downloads/UUF50.218.1000/"+ filename);
			assertFalse(Driver.DPPL(formula, emptyAssign, select));
			System.out.println(i++);
		}
	}
	
	@Test
	void testSATLongest() {
		int i = 0;
		File uf2091 = new File("/Users/Liron/Downloads/uf20-91");
		String [] list = uf2091.list();
		HashMap<String, String> emptyAssign = new HashMap<String, String>();
		LiteralSelector select = new LongestClauseSelection();
		
		for (String filename : list) {
			Set<Set<String>> formula = Driver.fileToFormula("/Users/Liron/Downloads/uf20-91/" + filename);
			assertTrue(Driver.DPPL(formula, emptyAssign, select));
			System.out.println(i++);
		}
	}

	@Test
	void testUNSATLongest() {
		int i = 0;
		File uuf50218 = new File("/Users/Liron/Downloads/UUF50.218.1000");
		String [] list = uuf50218.list();
		HashMap<String, String> emptyAssign = new HashMap<String, String>();
		LiteralSelector select = new LongestClauseSelection();
		
		for (String filename : list) {
			Set<Set<String>> formula = Driver.fileToFormula("/Users/Liron/Downloads/UUF50.218.1000/"+ filename);
			assertFalse(Driver.DPPL(formula, emptyAssign, select));
			System.out.println(i++);
		}
	}
	
	@Test
	void testSATStupid() {
		int i = 0;
		File uf2091 = new File("/Users/Liron/Downloads/uf20-91");
		String [] list = uf2091.list();
		HashMap<String, String> emptyAssign = new HashMap<String, String>();
		LiteralSelector select = new StupidSelection();
		
		for (String filename : list) {
			Set<Set<String>> formula = Driver.fileToFormula("/Users/Liron/Downloads/uf20-91/" + filename);
			assertTrue(Driver.DPPL(formula, emptyAssign, select));
			System.out.println(i++);
		}
	}

	@Test
	void testUNSATStupid() {
		int i = 0;
		File uuf50218 = new File("/Users/Liron/Downloads/UUF50.218.1000");
		String [] list = uuf50218.list();
		HashMap<String, String> emptyAssign = new HashMap<String, String>();
		LiteralSelector select = new StupidSelection();
		
		for (String filename : list) {
			Set<Set<String>> formula = Driver.fileToFormula("/Users/Liron/Downloads/UUF50.218.1000/"+ filename);
			assertFalse(Driver.DPPL(formula, emptyAssign, select));
			System.out.println(i++);
		}
	}
	
	
	

}
