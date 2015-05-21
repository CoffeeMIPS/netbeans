package HBDMIPS;

public class EXE {
	private ID_EXE idexe = new ID_EXE();
	private EXE_MEM exemem = new EXE_MEM();
        public String prev_instruction = "";
        private String j_pc;
	public EXE(ID_EXE idexe, EXE_MEM exemem) {
		this.exemem = exemem;
		this.idexe = idexe;
	}
        
        public boolean isJumpReg(){
            String func_bit = getIdexe().getSignExt().substring(26, 32);
            if("001000".equals(func_bit))
                return Boolean.TRUE;
            return Boolean.FALSE;
        }
        public boolean isSyscall(){
            String func_bit = getIdexe().getSignExt().substring(26, 32);
            String ALUOp = getIdexe().getControlBits().substring(1, 3)+getIdexe().getControlBits().substring(11, 13);
            if("001100".equals(func_bit) && "1000".equals(ALUOp)){
                return true;
            }
            return false;
        }
        public boolean isRegwrite(){
            return idexe.getControlBits().charAt(0)=='1';
        }
        public boolean isJump(){
            return idexe.getControlBits().charAt(10)=='1';
        }
        public boolean isBranch(){
            return idexe.getControlBits().charAt(6)=='1';
        }
        public boolean isNot(){
            return idexe.getControlBits().charAt(9)=='1';
        }
	public void action() {
                
		boolean REG_DEST = (getIdexe().getControlBits().charAt(7)) == '0' ? false : true;
		String ALUOp = getIdexe().getControlBits().substring(1, 3)+getIdexe().getControlBits().substring(11, 13);
		boolean ALU_Src = (getIdexe().getControlBits().charAt(3)) == '0' ? false
				: true;
		String func_bit = getIdexe().getSignExt().substring(26, 32);
		if (REG_DEST) {
			exemem.setWrite_Register(getIdexe().RD);
		} else if (!REG_DEST) {
			exemem.setWrite_Register(getIdexe().RT);
		}
                String alucu_func = alu_cu(func_bit,ALUOp);
                if(isJump()){
                    setJ_pc(getIdexe().getSignExt().substring(6,32));
                }
                if(alucu_func == "1110" || alucu_func == "1111"){
                    int data1 = getIdexe().getRT_DATA();
                    String Shift_amount = getIdexe().getSignExt().substring(21,26);
                    int data2 = (byte)Long.parseLong(Shift_amount, 2);
                    getExemem().setALU_result(alu(data1,data2,alucu_func));
                }
                else{
                    int data1 = getIdexe().getRS_DATA();
                    int data2 = ALU_Src?(byte)Long.parseLong(getIdexe().getSignExt(), 2):getIdexe().getRT_DATA();
                    getExemem().setALU_result(alu(data1,data2,alucu_func));
                }
		
		getExemem().setZERO(getExemem().getALU_result()==0?true:false);// 1 means branch occurs! and 0 is not occur!
		getExemem().setNew_PC(getIdexe().getPC()+(byte)Long.parseLong(getIdexe().getSignExt(), 2));
		getExemem().setRT_DATA(getIdexe().getRT_DATA());
		getExemem().setControlBits(getIdexe().getControlBits());
		
	}	
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
     * @return the idexe
     */
    public ID_EXE getIdexe() {
        return idexe;
    }

    /**
     * @param idexe the idexe to set
     */
    public void setIdexe(ID_EXE idexe) {
        this.idexe = idexe;
    }

    /**
     * @return the exemem
     */
    public EXE_MEM getExemem() {
        return exemem;
    }

    /**
     * @param exemem the exemem to set
     */
    public void setExemem(EXE_MEM exemem) {
        this.exemem = exemem;
    }

    /**
     * @return the j_pc
     */
    public String getJ_pc() {
        return j_pc;
    }

    /**
     * @param j_pc the j_pc to set
     */
    public void setJ_pc(String j_pc) {
        this.j_pc = j_pc;
    }

}
