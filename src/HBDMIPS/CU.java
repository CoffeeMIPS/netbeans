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
                                       // notbit(this bit added optionaly not compatible with book)
		switch (op) {
		case 0:
			// RType
			return "1100000100";
                case 13:
                        //ORI
                        return "1111000000";
		case 8:
                        // ADDI
			return "1001000000";
		case 35:
			// LW
			return "1001100010";
		case 43:
			// SW
			return "0001010100";
		case 4:
			// BEQ
			return "0010001000";
                case 5:
			// BNE
			return "0010001001";
		case 2:
			// JUMP
			return "0000000000";
                        // not implemented yet
		default:
			return null;
		}

	}

	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}
}
