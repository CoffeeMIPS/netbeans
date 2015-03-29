package HBDMIPS;

// note ourselves about write back
public class ID{
	public Register_file regfile = new Register_file();
	private CU cu = new CU();
	private IF_ID ifid;
	private ID_EXE idexe;
	private IF stage_if;

	public ID(IF_ID ifid, ID_EXE idexe,IF stage_if) {
		this.ifid = ifid;
		this.idexe = idexe;
		this.stage_if = stage_if;
	}

	public void action() {

		String instruction = ifid.getIns();
		cu.setOpcode(instruction.substring(0, 6));
		if (Integer.parseInt(instruction.substring(0, 6),2) == 2){
			stage_if.setPC(Integer.parseInt(instruction.substring(6, 32),2)); 
		}
		int RS = Integer.parseInt(instruction.substring(6, 11), 2);// Instruction
		int RT = Integer.parseInt(instruction.substring(11, 16), 2);
		int RS_DATA = regfile.getRegfile(RS);
		int RT_DATA = regfile.getRegfile(RT);
		int RD = Integer.parseInt(instruction.substring(16, 21), 2);
		// shamt not implement
		idexe.setSignExt(signExt(instruction.substring(16, 32)));
		idexe.setControlBits(cu.action(instruction.substring(0, 6)));
		idexe.setRS_DATA(RS_DATA);
		idexe.setRT_DATA(RT_DATA);
		idexe.setRT(RT);
		idexe.setRD(RD);
		idexe.setPC(ifid.getPC());
		
	}

	public Register_file getRegfile() {
		return regfile;
	}

	public void setRegfile(Register_file regfile) {
		this.regfile = regfile;
	}

	public CU getCu() {
		return cu;
	}

	public void setCu(CU cu) {
		this.cu = cu;
	}

	public IF_ID getIfid() {
		return ifid;
	}

	public void setIfid(IF_ID ifid) {
		this.ifid = ifid;
	}

	public ID_EXE getIdexe() {
		return idexe;
	}

	public void setIdexe(ID_EXE idexe) {
		this.idexe = idexe;
	}

	private String signExt(String inp) {
		String out = null;
		if (inp.charAt(0) == '1') {
			out = "1111111111111111" + inp;
		} else if (inp.charAt(0) == '0') {
			out = "0000000000000000" + inp;
		}
		return out;
	}
}
