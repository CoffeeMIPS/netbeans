package HBDMIPS;
/**
 * Represents Memory/WriteBack Pipeline Register.
 * Pipeline Registers are included for ease of 
 * Pipeline implementation in future.
 * @author HBD
 */
public class MEM_WB {
        /**
         * Data which was read from memory in MEM stage.
         */
	int READ_DATA = 0;
        /**
         * Result calculated by ALU in EXE stage.
         * For a R-Type Instruction.
         * Or read from MEM
         */
	int ALU_result;
        /**
         * Address of RegisterFile which data should be written to.
         */
	int Write_Register;
        /**
         * 2 of ControlBits would be used in WriteBack stage. 
         */
	String controlBits;

        
        /**
         * Data that has been read from Memory in MEM stage.
         * @return Data - an INT containing data, ALU_Result points to.
         */
	public int getREAD_DATA() {
		return READ_DATA;
	}

        
        /**
         * Set Data that was read fr
         * @param READ_DATA 
         */
	public void setREAD_DATA(int READ_DATA) {
		this.READ_DATA = READ_DATA;
	}

        /**
         * Result from ALU, Strored In MEM/WB Pipeline Register. 
         * @return ALU_Result - ALU Operation Result represented in INT
         * in EXE stage.
         */
	public int getALU_result() {
		return ALU_result;
	}

        
        /**
         * 
         * @param ALU_result - that should be stored in MEM/WB.
         */
	public void setALU_result(int ALU_result) {
		this.ALU_result = ALU_result;
	}
        /**
         * Get address of the register that should be written to 
         * in WriteBack stage.
         * @return Write_Register - the address of register.
         */
	public int getWrite_Register() {
		return Write_Register;
	}

        
        /**
         * Set the address of register that should be written to
         * in WriteBack stage.
         * @param write_Register - an INT representing 
         * address of Register to write to.
         */
	public void setWrite_Register(int write_Register) {
		Write_Register = write_Register;
	}

        /**
         * ControlBits that have been saved to the MEM/WB.
         * 2bits MEM2REG and REG_Write will be used in WriteBack stage.
         * @return controlBits - a String originally come from CU.
         */
	public String getControlBits() {
		return controlBits;
	}

        
        /**
         * Save controlBits of CU to MEM/WB Pipeline Register.
         * 2bits MEM2REG and REG_Write will be used in WriteBack stage.
         * @param controlBits -a String Representing Bits of CU. 
         */
	public void setControlBits(String controlBits) {
		this.controlBits = controlBits;
	}
        
}
