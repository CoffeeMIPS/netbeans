package HBDMIPS;

public class ID_EXE {
	String signExt;
	String controlBits;
	int PC;
	int RS_DATA;
	int RT_DATA;
	int RD;
	int RT;

	public int getPC() {
		return PC;
	}

	public void setPC(int PC) {
		this.PC = PC;
	}

	public String getSignExt() {
		return signExt;
	}

	public void setSignExt(String signExt) {
		this.signExt = signExt;
	}

	public String getControlBits() {
		return controlBits;
	}

	public void setControlBits(String controlBits) {
		this.controlBits = controlBits;
	}

	public int getRS_DATA() {
		return RS_DATA;
	}

	public void setRS_DATA(int rS_DATA) {
		RS_DATA = rS_DATA;
	}

	public int getRT_DATA() {
		return RT_DATA;
	}

	public void setRT_DATA(int rT_DATA) {
		RT_DATA = rT_DATA;
	}

	public int getRD() {
		return RD;
	}

	public void setRD(int rD) {
		RD = rD;
	}

	public int getRT() {
		return RT;
	}

	public void setRT(int rT) {
		RT = rT;
	}

}
