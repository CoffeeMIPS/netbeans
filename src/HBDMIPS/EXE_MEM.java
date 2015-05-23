package HBDMIPS;
/**
 * Represents Execute/Memory Pipeline Register.
 * Pipeline Registers are included for ease of 
 * Pipeline implementation in future.
 * @author HBD
 */
public class EXE_MEM {
        
        /**
         * Come originally from CU.
         */
	String controlBits;
        /**
         * Set by PC's ALU in EXE stage.
         */
	int new_PC;
        /**
         * Determined by ALU Showing branch happens.
         */
	boolean ZERO;
        /**
         * Result of ALU
         */
	int ALU_result;
        /**
         * Originally come from ID.
         * Data of RT Address Register determined in ID by RegisterFile
         */
	int RT_DATA;
        /**
         * Depending on I-Type or R-Type; In order its RT OR RD  
         */
	private int Write_Register;
        
        /**
         * 
         * @return controlBits - currently stored in EXE/MEM.
         */
	public String getControlBits() {
		return controlBits;
	}
        /**
         * 
         * @param controlBits - 13 bits of control represented in String.
         */
	public void setControlBits(String controlBits) {
		this.controlBits = controlBits;
	}
        /**
         * 
         * @return new-PC - stored in EXE/MEM come from EXE. 
         */
	public int getNew_PC() {
		return new_PC;
	}
        /**
         * 
         * @param new_PC - It should be set in EXE stage. 
         */
	public void setNew_PC(int new_PC) {
		this.new_PC = new_PC;
	}
        /**
         * 
         * @return ZERO - show if branch taken. 
         */
	public boolean getZERO() {
		return ZERO;
	}
        /**
         * 
         * @param ZERO - Set if branch taken.  
         */
	public void setZERO(boolean ZERO) {
		this.ZERO = ZERO;
	}
        /**
         * 
         * @return ALU_Result - what was the result of ALU in EXE.
         */
	public int getALU_result() {
		return ALU_result;
	}
        /**
         * 
         * @param ALU_result - Set the result of ALU .
         */
	public void setALU_result(int ALU_result) {
		this.ALU_result = ALU_result;
	}
        /**
         * 
         * @return RT_DATA - Data stored in RT address Register 
         * moved in Pipeline.
         */
	public int getRT_DATA() {
		return RT_DATA;
	}
        /**
         * 
         * @param rT_DATA - Dat that should be stored in RT_DATA.
         */
	public void setRT_DATA(int rT_DATA) {
		RT_DATA = rT_DATA;
	}
        /**
         * 
         * @return Write_Register - address want to write to in RegisterFile.
         */
	public int getWrite_Register() {
		return Write_Register;
	}
        /**
         * If it's R-Type comes from RD.
         * If it's I-Type comes from RT.
         * @param write_Register - address should be written to.  
         */
	public void setWrite_Register(int write_Register) {
		Write_Register = write_Register;
	}
	
}