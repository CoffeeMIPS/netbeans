package Assembler;

import java.util.HashMap;


public class Test {

	public static void main(String[] args) {
		String filename = "test.asm";
		HashMap<Integer, Instruction> assembled = new HashMap<Integer, Instruction>(Assembler.assembleFile(filename));
		
		
		
		
		
		//how to use : consider that you want to get line number 1(no 0,lines starts at 1) address and instruction:
		System.out.println("Line 1 Address Is     : "+assembled.get(1).getAddress());
		System.out.println("Line 1 Instruction Is : "+assembled.get(1).getInstruction());

	}

}
