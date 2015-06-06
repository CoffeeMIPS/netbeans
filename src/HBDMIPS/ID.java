package HBDMIPS;

// note ourselves about write back
/**
 * This class represents <b>Instruction Decode</b> stage.
 * Instructions index start from left to right.   0 1 2 .. 31  :|
 * @author HBD
 */
public class ID{
	public Register_file regfile = new Register_file("FILE");// 32 of 32bit
                                                           //MIPS architecture 
                                                           //Registers.
	private CU cu = new CU(); //Control Unit
	private IF_ID ifid;// IF/ID for ID stage.
	private ID_EXE idexe;// ID/EXE for ID stage.
	private IF stage_if;// 

	public ID(IF_ID ifid, ID_EXE idexe,IF stage_if) {
		this.ifid = ifid;
		this.idexe = idexe;
		this.stage_if = stage_if;
	}
        
        
        /**
         * ِِDo the job of InstructionDecode stage.
         * This includes:
         * 1- Fetch instruction from IF/ID.
         * 2- Set opcode of Control Unit.
         * 3- Check J-Type or I-Type so Set PC.
         * 4- Set Register File in ID stage. 
         *      [Considering exception of Write Data]
         * 5- Save SignExtended Address of InstructionCode
         *    to ID/EXE Pipeline Register. 
         * 6- Save 13bit ControlBits come from CU
         *    to ID/EXE Pipeline Register.
         * 7- Save RS, RT Addresses & Data to ID/EXE Pipeline Register.
         * 8- Save current PC  to ID/EXE Pipeline Register.
         */
	public void action(boolean mode) {

		String instruction = ifid.getIns();
		cu.setOpcode(instruction.substring(0, 6));
//		if (Integer.parseInt(instruction.substring(0, 6),2) == 2){
//                        //it means I-Type or J-Type instruction,
//                        //so PC should change. 
//			stage_if.setPC(Integer.parseInt(instruction.substring(6, 32),2)); 
//		}
                // R-Type instruction format: 6bit opcode - 5bit RS - 5bit RT
                //                            5bit RD - 5bit shamt - 6bit func.
		int RS = Integer.parseInt(instruction.substring(6, 11), 2);
		int RT = Integer.parseInt(instruction.substring(11, 16), 2);
		int RD = Integer.parseInt(instruction.substring(16, 21), 2);
                
                int RS_DATA = regfile.getRegfile(RS);
		int RT_DATA = regfile.getRegfile(RT);
                
                //Save all SignExtend, ControlBits[Which come from CU],
                //Register Source, Rgister Temp and Register Destination,
                //RegisterFile Datas stored in RS & RT addresses,
                //ID [Or current] stage's Program counter.
                //All in ID/EXE Pipeline Register.
		idexe.setSignExt(signExt(instruction.substring(16, 32)));
                String cu_result = cu.action(instruction.substring(0, 6),instruction);
                if(cu_result.charAt(10)=='1'){// means if instruction is jump
                    idexe.setSignExt(("0000".concat(instruction.substring(6, 32))).concat("00"));
                }
		idexe.setControlBits(cu_result);
		idexe.setRS_DATA(RS_DATA);
		idexe.setRT_DATA(RT_DATA);
		idexe.setRT(RT);
		idexe.setRD(RD);
		idexe.setPC(ifid.getPC());
		
	}
        
        
        /**
         * 
         * @return regfile - all 32 of 32bit registers as a
         */
	public Register_file getRegfile() {
		return regfile;
	}
        
        
        /**
         * 
         * @param regfile - set all 32 of 32bit registers via
         * an instance of Register_file class.
         */
	public void setRegfile(Register_file regfile) {
		this.regfile = regfile;
	}

        
        /**
         * 
         * @return CU - instance of CU class initialized in 
         * ID [this] stage.
         */
	public CU getCu() {
		return cu;
	}

        /**
         * Set Control Unit. 
         * afterward returned CU instance can take Opcode
         * via its action method.
         * @param cu - an instance of CU [Control Unit] Class.
         */
        public void setCu(CU cu) {
		this.cu = cu;
	}

        
        /**
         * 
         * @return ifid - instance of IF/ID currently existing in
         * ID stage.
         */
	public IF_ID getIfid() {
		return ifid;
	}

        
        /**
         * Set the side of IF/ID Pipeline Register
         * existing in ID stage.
         * @param ifid - an instance of IF/ID class.
         */
	public void setIfid(IF_ID ifid) {
		this.ifid = ifid;
	}

        
        /**
         * 
         * @return idexe -an instance of ID/EXE class currently existing
         * in ID stage.
         */
	public ID_EXE getIdexe() {
		return idexe;
	}

        
        /**
         * Set the side of ID/EXE Pipeline Register
         * existing in ID stage.
         * @param idexe - an instance of ID/EXE class.
         */
	public void setIdexe(ID_EXE idexe) {
		this.idexe = idexe;
	}

        
        /**
         * SignExtend I-Type or J-Type Instructions.
         * @param inp - 16bit address existing in the right side of
         * the instructionCode. In convention of HBD bits 16 to 31,
         * from left to Right :!
         * @return out - 32bit extended address.
         */
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
