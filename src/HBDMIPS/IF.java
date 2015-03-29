package HBDMIPS;

import java.util.ArrayList;
import java.util.HashMap;

import Assembler.Assembler;
import Assembler.Instruction;

public class IF {
        private String filePath;
	HashMap<Integer, Instruction> ins_mem;
	int PC = 0;
	public int getPC() {
		return PC;
	}

	public void setPC(int pC) {
		PC = pC;
	}
        
        public void setfilePath(String filePath){
            this.filePath = filePath;
        }
        public String getfilePath(){
            return this.filePath;
        }

	String ins = null;
	IF_ID ifid;

	public IF(IF_ID ifid,String filePath) {
		this.ifid = ifid;
                this.filePath = filePath;
		ins_mem = new HashMap<Integer, Instruction>(Assembler.assembleFile(this.filePath));
	}

	public void action() {
		ifid.setIns(ins_mem.get(PC).getInstruction());
		PC++;
		ifid.setPC(PC);
	}
}
