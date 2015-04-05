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

	public String decode(int op) { // regwrite aluop(2) alusrc memread
                                       // memwrite branch regdest mem2reg
                                       // this bit added optionaly not compatible with book :
                                       // notbits
                                       // jump
                                       // second aluop (2)
		switch (op) {
		case 0:
			// RType
			return "1100000100000";
                case 13:
                        //ORI
                        return "1111000000000";
                case 12:
                        //ANDI
                        return "1111000000010";
		case 8:
                        // ADDI
			return "1001000000000";
		case 35:
			// LW
			return "1001100010000";
		case 43:
			// SW
			return "0001010100000";
		case 4:
			// BEQ
			return "0010001000000";
                case 5:
			// BNE
			return "0010001001000";
		case 2:
			// JUMP
			return "0000000000100";
                        // not implemented yet
                case 3:
                        // JAL
                        return "1000000000100";
		default:
			return null;
		}

	}

	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}
}
