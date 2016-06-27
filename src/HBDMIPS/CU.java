package HBDMIPS;


/**
 * Represents Control Unit in stage InstructionDecode. 
 * @author HBD
 */
public class CU {
	String opcode;
	String out;

	public CU() {

	}

        
        /**
         * Do the job of CU.
         * This includes:
         * 1- Gets 6bit Opcode from 26 to 31.
         *    [0 to 5 in our convention from left to right :| ]
         * 2- Outputs 13bits of Control.
         * @param op - 5bits of opcode.
         * @return out - 13bits of Control represented in String.
         */
	public String action(String op,String ins) {
		opcode = op;
		out = decode(Integer.parseInt(op, 2),ins);
		return out;
	}

        
        
        /**
         * 
         * @param op - 5bits opcode represented in String.
         * @return out - 13bits of CotrolBits represented in String.
         */
	public String decode(int op,String ins) {
	// regwrite aluop(2) alusrc memread
	   // memwrite branch regdest mem2reg
	   // this bit added optionaly not compatible with book :
	   // notbits
	   // jump
	   // second aluop (2)
	   // jr bit
		switch (op) {
		case 0:
                        if("001000".equals(ins.substring(26,32)))
                            return "11000001000001";
			// RType
			return "11000001000000";
                case 13:
                        //ORI
                        return "11110000000000";
                case 12:
                        //ANDI
                        return "11110000000100";
		case 8:
                        // ADDI
			return "10010000000000";
		case 35:
			// LW
			return "10011000100000";
		case 43:
			// SW
			return "00010101000000";
		case 4:
			// BEQ
			return "00100010000000";
                case 5:
			// BNE
			return "00100010010000";
		case 2:
			// JUMP
			return "00000000001000";
                case 3:
                        // JAL
                        return "10000000001000";
		default:
			return null;
		}

	}
        
        
        /**
         * Set Opcode which CU should decide on.
         * @param opcode - 5bits Opcode represented in String.
         */
	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}
}