package HBDMIPS;

public class EXE_MEM {
	String controlBits;
	int new_PC;
	boolean ZERO;
	int ALU_result;
	int RT_DATA;
	private int Write_Register;
	public String getControlBits() {
		return controlBits;
	}
	public void setControlBits(String controlBits) {
		this.controlBits = controlBits;
	}
	public int getNew_PC() {
		return new_PC;
	}
	public void setNew_PC(int new_PC) {
		this.new_PC = new_PC;
	}
	public boolean getZERO() {
		return ZERO;
	}
	public void setZERO(boolean zERO) {
		ZERO = zERO;
	}
	public int getALU_result() {
		return ALU_result;
	}
	public void setALU_result(int aLU_result) {
		ALU_result = aLU_result;
	}
	public int getRT_DATA() {
		return RT_DATA;
	}
	public void setRT_DATA(int rT_DATA) {
		RT_DATA = rT_DATA;
	}
	public int getWrite_Register() {
		return Write_Register;
	}
	public void setWrite_Register(int write_Register) {
		Write_Register = write_Register;
	}
	
}