package HBDMIPS;


/**
 * Represents Execute stage.
 * 
 * @author HBD
 */
public class EXE {
	private ID_EXE idexe = new ID_EXE();// ID/EXE for EXE stage.
	private EXE_MEM exemem = new EXE_MEM();// EXE/MEM for EXE stage.
        public String prev_instruction = "";// ?????????????????????????????????
        private String j_pc;//If Instruction is jump PC should change.
	public EXE(ID_EXE idexe, EXE_MEM exemem) {
		this.exemem = exemem;
		this.idexe = idexe;
	}
        
        
        
        /**
         * Is it Jump Register Instruction?
         * Detect by functionBit which is contained in 6 low value bits
         * of SignExtend.
         * 
         * @return isJReg - Boolean representing detection of JR. 
         */
        public boolean isJumpReg(){
            return idexe.getControlBits().charAt(13)=='1';
        }
        
        
        /**
         * Is it System Call Register Instruction?
         * 
         * Detect by functionBit which is contained in 6 low value bits
         * of SignExtend of ID/EXE
         * and also ALUOp which is contained in ControlBits of 
         * ID/EXE in substrings [1,2] , [11,12].
         * Note : In our convention we use 2 of 2bits ALUOp.
         * @return isSysCall - Boolean representing detection of SysCall.
         */
        public boolean isSyscall(){
            String func_bit = getIdexe().getSignExt().substring(26, 32);
            String ALUOp = getIdexe().getControlBits().substring(1, 3)+getIdexe().getControlBits().substring(11, 13);
            if("001100".equals(func_bit) && "1000".equals(ALUOp)){
                return true;
            }
            return false;
        }
        
        
        /**
         * Detects If Instruction contains value which
         * should be written back IN RegisterFile? [LW..]
         * It uses ID/EXE Control Bits to detect.[bit 1]
         * @return isRW - Boolean representing detection of RegisterWrite.
         */
        public boolean isRegwrite(){
            return idexe.getControlBits().charAt(0)=='1';
        }
        
        
        /**
         * Detects if Instruction is Jump ??????????????????????????????????????
         * It uses ID/EXE Control Bits to detect.[bit 11]
         * @return isJ - Boolean representing detection of Jump. 
         */
        public boolean isJump(){
            return idexe.getControlBits().charAt(10)=='1';
        }
        
        
        
        /**
         * Detects if Instruction is Branch Equal?[beq]
         * It uses ID/EXE Control Bits to detect.[bit 7]
         * @return 
         */
        public boolean isBranch(){
            return idexe.getControlBits().charAt(6)=='1';
        }
        
        
        /**
         * Detects if Instruction is Branch not Equal[bnq].
         * It uses ID/EXE Control Bits to detect.[bit 10]
         * @return isBnq - Boolean representing if its branch not equal.
         */
        public boolean isNot(){
            return idexe.getControlBits().charAt(9)=='1';
        }
        
        /**
         * Take a look at implementation :D
         * Explained there.
         */
	public void action(boolean mode) {
		boolean REG_DEST = (getIdexe().getControlBits().charAt(7)) == '0' ? false : true;
                                            //false means RT Should be used,  an I-Type instruction.
                                            //true means RD Should be used, is a R-Type instruction. 
		String ALUOp = getIdexe().getControlBits().substring(1, 3)+getIdexe().getControlBits().substring(11, 13);
                                            //which is used in ALUControl
                boolean ALU_Src = (getIdexe().getControlBits().charAt(3)) == '0' ? false
				: true;// false means use readData is an R-type instruction.
                                        //true means use signExtend . an I-Type instruction.
		String func_bit = getIdexe().getSignExt().substring(26, 32);//use SignExtend to 
                                                                            //detect function bits.
		if (REG_DEST) {
			exemem.setWrite_Register(getIdexe().RD);//RD Should be used.
                        //is a R-Type.
		} else if (!REG_DEST) {//RT should be used.Is a I-Type.
			exemem.setWrite_Register(getIdexe().RT);
		}
                String alucu_func = alu_cu(func_bit,ALUOp);//initialize ALUControl.
                if(isJump()){
                    setJ_pc(getIdexe().getSignExt());
                    //to the signExtend substring 0 to 25 [6 to 31 in our convention]
                    //shifted left by 2bits and then bits 28 to 31 of old PC 
                    //comes in 4 higher value bits.

                }
                
                //If Instruction is Shift then read data1 from ID/EXE Pipeline 
                //Register, read Shift amount from there which consists of bits 6 to 10
                //[21 to 25 in our convention] InstructionCode.
                //Save the Result of Shift in ALU_Result into EXE/MEM Pipeline
                //Register.
                if(alucu_func == "1110" || alucu_func == "1111"){
                    int data1 = getIdexe().getRT_DATA();
                    String Shift_amount = getIdexe().getSignExt().substring(21,26);
                    int data2 = (byte)Long.parseLong(Shift_amount, 2);
                    getExemem().setALU_result(alu(data1,data2,alucu_func));
                }
                
                
                //Else Instruction is a simple ADD, Branch, and or LOAD/Store.
                //Read Data1 from ID/EXE Pipeline Register.
                //Decide on Data2 according to the ALU_SRC[if It's true then We
                //have a Load or something, Otherwise it's R-Type and or branch.]
                else{
                    int data1 = getIdexe().getRS_DATA();
                    int data2 = ALU_Src?(int)Long.parseLong(getIdexe().getSignExt(), 2):getIdexe().getRT_DATA();
                    getExemem().setALU_result(alu(data1,data2,alucu_func));
                }
		//Save Zero, PC, RT_DATA[maybe used for Write Data of MEM], Control Bits
                // All to EXE/MEM.
		getExemem().setZERO(getExemem().getALU_result()==0?true:false);// 1 means branch occurs! and 0 is not occur!
		getExemem().setNew_PC(getIdexe().getPC()+(byte)Long.parseLong(getIdexe().getSignExt(), 2));
		getExemem().setRT_DATA(getIdexe().getRT_DATA());
		getExemem().setControlBits(getIdexe().getControlBits());
		
	}
        
        
        /**
         * Calculate The nor of????????????????????????????????????????????????
         * @param data1
         * @param data2
         * @return result - Not OF OR of Inputs.
         */
        public static int nor32(int data1,int data2){
        int result = 0 ;
        for(int i=1;i<32;i*=2){
            int bit1 = data1/i%2;
            int bit2 = data2/i%2;
            int resbit = (bit1 | bit2);
            if(resbit == 0){
                result +=i;
            }
        }
        return result;
    }
        
        
        /**
         * 
         * @param data_1 - input 1
         * @param data_2 - input 2
         * @param op - Operation that should be taken decided by ALUcontrol.
         * @return result - Operation taken result.
         */
	public int alu(int data_1, int data_2, String op) {
		switch (op) {
		case "0010":
			return data_1 + data_2;
		case "0110":
			return data_1 - data_2;
		case "0000":
			return data_1 & data_2;
		case "0001":
			return data_1 | data_2;
		case "0111":
			return data_1 < data_2 ? 0 : 1;
                case "1001": // nor function (not compatible with book)
                        return nor32(data_1,data_2);
                case "1110": // $d = $t << h
                        return data_1 << data_2;
                case "1111": // $d = $t >> h
                        return data_1 >> data_2;
		default:
			break;
		}
		return 0;
	}
        
        
        
        /**
         * 
         * @param func_bit - 6bit function come from sgnExtend lower bits.
         * @param Aluop - 2 of 2bits ALUop come from controlUnit.
         * @return op - Appropriate operation. 
         */
	public String alu_cu(String func_bit, String Aluop) {
		switch (Aluop) {
		case "1000":
			switch (func_bit) {
			case "100000": //add
				return "0010";
			case "100010": //sub
				return "0110";
			case "100100": //and
				return "0000";
			case "100101": //or
				return "0001";
                        case "100111"://nor
                                return "1001";
			case "101010": //SLT If $s is less than $t, $d is set to one. It gets zero otherwise. 
				return "0111";
                        case "000000":
                                return "1110";
                        case "000010":
                                return "1111";
                        case "001000": // jump register (jr)
                                return "0010"; // it's a fake add 
                        case "001100": // syscall
                                return "0010"; // it's a fake add 
			default:
				break;
			}
			break;
		case "0000": // add operation used for addi,lw,sw
			return "0010";
		case "0100": // sub operation used for beq,bne
			return "0110";
                case "1100": // it's used for ORi
                        return "0001";
                case "1110":
                        return "0000";
		default:
			break;
		}
		return null;

	}

    /**
     * @return ID/EXE - The whole of ID/EXE Pipeline Register in EXE stage.
     */
    public ID_EXE getIdexe() {
        return idexe;
    }

    /**
     * @param idexe - Set the Whole ID/EXE by an object of its type.
     */
    public void setIdexe(ID_EXE idexe) {
        this.idexe = idexe;
    }

    /**
     * @return EXE/MEM - The whole of EXE/MEM as an object of its type.
     */
    public EXE_MEM getExemem() {
        return exemem;
    }

    /**
     * @param exemem - Set the Whole EXE/MEM by an object of its type.
     */
    public void setExemem(EXE_MEM exemem) {
        this.exemem = exemem;
    }

    /**
     * @return the j_pc - PC of Jump if its occurring.
     */
    public String getJ_pc() {
        return j_pc;
    }

    /**
     * @param j_pc the j_pc to set for Jump.
     */
    public void setJ_pc(String j_pc) {
        this.j_pc = j_pc;
    }

}
