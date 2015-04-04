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
                                       // jump
            
		switch (op) {
		case 0:
			// RType
			return "11000001000";
                case 13:
                        //ORI
                        return "11110000000";
		case 8:
                        // ADDI
			return "10010000000";
		case 35:
			// LW
			return "10011000100";
		case 43:
			// SW
			return "00010101000";
		case 4:
			// BEQ
			return "00100010000";
                case 5:
			// BNE
			return "00100010010";
		case 2:
			// JUMP
			return "00000000001";
                        // not implemented yet
                case 3:
                        // JUMP AND LINK
                        return "10000000001";
		default:
			return null;
		}

	}

	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}
}
