package HBDMIPS;

import java.util.ArrayList;
import java.util.List;


public class Register_file {
	int[] Regfile = new int[31];

	public Register_file(){
		List<String> ins_mem = new ArrayList<String>(FileHandler.FileIO.FiletoStringArray("RegData.txt"));
		for (int i = 0; i < Regfile.length; i++) {
			Regfile[i] = Integer.parseInt(ins_mem.get(i), 2);
		}
	}
	public int[] getRegfile() {
		return Regfile;
	}
	public int getRegfile(int regNum) {
		return Regfile[regNum];
	}

	public void setRegfile(int[] regfile) {
		Regfile = regfile;
	}
	public void setRegfile(int regNum,int Value) {
		Regfile[regNum] = Value;
	}
}
