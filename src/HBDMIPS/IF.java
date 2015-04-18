package HBDMIPS;
import java.util.HashMap;

import Assembler.Assembler;
import Assembler.Instruction;
import java.util.Map;

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
		ins_mem =new HashMap<Integer, Instruction>(Assembler.assembleFile(filePath));
                for (Map.Entry<Integer, Instruction> entrySet : ins_mem.entrySet()) {
                Integer key = entrySet.getKey();
                Instruction value = entrySet.getValue();
                System.out.print(value.getAddress()+" : "+ value.getInstruction()+"\n");
                
            }
	}

	public void action() {
		ifid.setIns(ins_mem.get(PC).getInstruction());
		PC++;
		ifid.setPC(PC);
	}
        public String getInstruction(){
            return ins_mem.get(this.PC-1).getInstruction();
        }
}
