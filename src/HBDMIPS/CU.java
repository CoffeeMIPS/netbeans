package HBDMIPS;

public class CU {
	String opcode;
	String out;

	public CU() {

	}

	public String action(String op) {
		opcode = op;
		out = decode(Integer.parseInt(op, 2));
		return out;
	}

	public String decode(int op) { // regwrite aluop alusrc memread
									// memwrite branch regdest mem2reg
		switch (op) {
		case 0:
			// RType
			return "110000010";
		case 8:
			return "100100000";
		case 35:
			// LW
			return "100110001";
		case 43:
			// SW
			return "000101010";
		case 4:
			// BEQ
			return "001000100";
			// not implemented yet
		case 2:
			// JUMP
			return "000000000";
		default:
			return null;
		}

	}

	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}
}
