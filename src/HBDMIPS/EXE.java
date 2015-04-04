package HBDMIPS;

public class EXE {
	private ID_EXE idexe = new ID_EXE();
	private EXE_MEM exemem = new EXE_MEM();

	public EXE(ID_EXE idexe, EXE_MEM exemem) {
		this.exemem = exemem;
		this.idexe = idexe;
	}
        public boolean isjump(){
            return idexe.getControlBits().charAt(10)=='1';
        }
        public boolean isbranch(){
            return idexe.getControlBits().charAt(6)=='1';
        }
        public boolean isnot(){
            return idexe.getControlBits().charAt(9)=='1';
        }
	public void action() {
		boolean REG_DEST = (getIdexe().getControlBits().charAt(7)) == '0' ? false : true;
		String ALUOp = getIdexe().getControlBits().substring(1, 3);
		boolean ALU_Src = (getIdexe().getControlBits().charAt(3)) == '0' ? false
				: true;
		String func_bit = getIdexe().getSignExt().substring(26, 32);
		if (REG_DEST) {
			exemem.Write_Register = getIdexe().RD;
		} else if (!REG_DEST) {
			exemem.Write_Register = getIdexe().RT;
		}
		getExemem().setALU_result(alu(getIdexe().getRS_DATA(),ALU_Src?(byte)Long.parseLong(getIdexe().getSignExt(), 2):getIdexe().getRT_DATA(),alu_cu(func_bit,ALUOp)));
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
		case "0111": //SLT If $s is less than $t, $d is set to one. It gets zero otherwise. 
			return data_1 < data_2 ? 1 : 0;
                case "1001": // nor function (not compatible with book)
                        return nor32(data_1,data_2);
		default:
			break;
		}
		return 0;
	}

	public String alu_cu(String func_bit, String Aluop) {
		switch (Aluop) {
		case "10":
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
                        case "001000"://JR
                                return "0010"; // it's fake not realy do anything
			case "101010": //SLT If $s is less than $t, $d is set to one. It gets zero otherwise. 
				return "0111";
			default:
				break;
			}
			break;
		case "00": // add operation used for addi,lw,sw
			return "0010";
		case "01": // sub operation used for beq,bne
			return "0110";
                case "11": // it's used for ORi
                        return "0001";
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

}
