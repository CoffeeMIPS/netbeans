package HBDMIPS;
/**
 * Represents the InstructionFetch/InstructionDecode Pipeline Register.
 * Pipeline Registers are included for ease of 
 * Pipeline implementation in future.
 * @author HBD
 */
public class IF_ID {
	String ins;
	int PC;
        
        
        /**
         * 
         * @return PC - current Program Counter saved in IF/ID Pipeline Register.
         */
	public int getPC() {
		return PC;
	}
        
        
        /**
         * Set current Program Counter in IF/ID pipeline register.
         * @param pc  
         */
	public void setPC(int pc) {
		this.PC = pc;
	}
        /**
         * 
         * @return ins - instruction currently saved in IF/ID Pipeline Register.
         */
	public String getIns() {
		return ins;
	}
        
        /**
         * 
         * @param ins - instruction to save in IF/ID Pipeline Register.
         */
	public void setIns(String ins) {
		this.ins = ins;
	}
}
