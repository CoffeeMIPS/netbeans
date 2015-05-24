package HBDMIPS;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents RegisterFile in stage InstructionDecode.
 * 32 of 32bits Registers.
 * 
 * @author HBD
 */
public class Register_file {
	int[] Regfile = new int[32];

	public Register_file(){
		List<String> ins_mem = new ArrayList<String>(FileHandler.FileIO.FiletoStringArray("RegData.txt"));
                //RegisterFile is Saved and wiil be saved after run into RegData.txt
                for (int i = 0; i < Regfile.length; i++) {
			Regfile[i] = Integer.parseInt(ins_mem.get(i), 2);//Load registerFile from
                                                                         //RegData.txt line by line.
		}
	}
        /**
         * Get whole of current registerFile .
         * @return Regfile - int[32]
         */
	public int[] getRegfile() {
		return Regfile;
	}
        
        
        /**
         * Get specified register content.
         * @param regNum - number of register
         * @return Regfile[regNum] - Register content.
         */
	public int getRegfile(int regNum) {
		return Regfile[regNum];
	}

        
        /**
         * Set whole of RegisterFile represented in an INT[].
         * @param regfile - int[32].
         */
	public void setRegfile(int[] regfile) {
		Regfile = regfile;
	}
	
        
        /**
         * Set the content of specified register.
         * @param regNum - number of specified register.
         * @param Value - content to be saved to that register.
         */
        public void setRegfile(int regNum,int Value) {
		Regfile[regNum] = Value;
	}
        
        
        /**
         * get RegisterFile in a customized String format.
         * @return Print - representation of current 
         * RegisterFile in a String.
         */
        public String print() {
            String print="";
            for (int i = 0; i < 32; i++) {
                if (i < 10) {
                        print+="  $" + i + " : " + Regfile[i] + " \t ";
                } else
                        print+="$" + i + " : " + Regfile[i] + " \t ";
                if ((i + 1) % 4 == 0) {
                        print+="\n";
                }
            }
            return print;
    }
}
