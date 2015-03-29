package HBDMIPS;

public class MEM_WB {
	int READ_DATA = 0;
	int ALU_result;
	int Write_Register;
	String controlBits;

	public int getREAD_DATA() {
		return READ_DATA;
	}

	public void setREAD_DATA(int rEAD_DATA) {
		READ_DATA = rEAD_DATA;
	}

	public int getALU_result() {
		return ALU_result;
	}

	public void setALU_result(int aLU_result) {
		ALU_result = aLU_result;
	}

	public int getWrite_Register() {
		return Write_Register;
	}

	public void setWrite_Register(int write_Register) {
		Write_Register = write_Register;
	}

	public String getControlBits() {
		return controlBits;
	}

	public void setControlBits(String controlBits) {
		this.controlBits = controlBits;
	}

}
