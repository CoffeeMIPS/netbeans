package HBDMIPS;
/**
 * Represents InstructionDecode/Execute Pipeline Register.
 * Pipeline Registers are included for ease of 
 * Pipeline implementation in future.
 * @author HBD
 */
public class ID_EXE {
	private String signExt;//Now it's Extended[32bits] String.
	String controlBits;
	int PC;
	int RS_DATA;
	int RT_DATA;
	int RD;
	int RT;
        
        /**
         * 
         * @return PC - current Program Counter saved in ID/EXE Pipeline Register.
         */
	public int getPC() {
		return PC;
	}
        /**
         * Set current Program Counter in IF/ID pipeline register.
         * @param PC 
         */
	public void setPC(int PC) {
		this.PC = PC;
	}
        
        
        /**
         * Get SignExtended Address saved in ID/EXE Pipeline Register.
         * @return signExt - 32bits string of address.
         */
	public String getSignExt() {
		return signExt;
	}

        /**
         * Save SignExtended Address in ID stage into ID/EXE Pipeline Register.
         * @param signExt - 32bits string of address.
         */
	public void setSignExt(String signExt) {
		this.signExt = signExt;
	}
        
        
        /**
         * Get 13bits of Control saved in ID/EXE Pipeline Register.
         * @return controlBits - 13bits string.
         */
	public String getControlBits() {
		return controlBits;
	}
        
        
        /**
         * Save 13bits of Control which <b>originally</b>come from CU.
         * @param controlBits - 13bits represented in string.
         */
	public void setControlBits(String controlBits) {
		this.controlBits = controlBits;
	}

        
        
	/**
         * Get Data of the RS address Stored in ID/EXE Pipeline Register.
         * @return RS_DATA - 32bit Data represented in INT. 
         */
        public int getRS_DATA() {
		return RS_DATA;
	}
        
        
        /**
         * Store Data of the RS address in ID/EXE Pipeline Register.
         * @param RS_DATA - 32bit Data represented in INT.
         */
	public void setRS_DATA(int RS_DATA) {
		this.RS_DATA = RS_DATA;
	}

        
        /**
         * Get stored Data of the RT address in ID/EXE Pipeline Register.
         * @return RT_DATA - 32bit Data represented in INT.
         */
	public int getRT_DATA() {
		return RT_DATA;
	}

        
        /**
         * Store Data of the RT address in ID/EXE Pipeline Register.
         * @param RT_DATA - 32bit Data represented in INT.
         */
	public void setRT_DATA(int RT_DATA) {
		this.RT_DATA = RT_DATA;
	}

        /**
         * Get address stored in RD in ID/EXE Pipeline Register.
         * @return RD - 5bit RD address represented in INT.
         */
	public int getRD() {
		return RD;
	}

        
        /**
         * Store  RD address in ID/EXE Pipeline Register.
         * @param RD - 5bit RD address represented in INT.
         */
	public void setRD(int RD) {
		this.RD = RD;
	}
        
        
        /**
         * Get address stored in RT in ID/EXE Pipeline Register.
         * @return RT - 5bit RT address represented in INT. 
         */
	public int getRT() {
		return RT;
	}

        
        /**
         * Store  RT address in ID/EXE Pipeline Register.
         * @param RT - 5bit RD address represented in INT.
         */
	public void setRT(int RT) {
		this.RT = RT;
	}

}
