package HBDMIPS;

public class EXE {
	ID_EXE idexe = new ID_EXE();
	EXE_MEM exemem = new EXE_MEM();

	public EXE(ID_EXE idexe, EXE_MEM exemem) {
		this.exemem = exemem;
		this.idexe = idexe;
	}

	public void action() {
		boolean REG_DEST = (idexe.getControlBits().charAt(7)) == '0' ? false : true;
		String ALUOp = idexe.getControlBits().substring(1, 3);
		boolean ALU_Src = (idexe.getControlBits().charAt(3)) == '0' ? false
				: true;
		String func_bit = idexe.getSignExt().substring(26, 32);
		if (REG_DEST) {
			exemem.Write_Register = idexe.RD;
		} else if (!REG_DEST) {
			exemem.Write_Register = idexe.RT;
		}
		exemem.setALU_result(alu(idexe.getRS_DATA(),ALU_Src?(byte)Long.parseLong(idexe.getSignExt(), 2):idexe.getRT_DATA(),alu_cu(func_bit,ALUOp)));
		exemem.setZERO(exemem.getALU_result()==0?true:false);// 1 means branch occurs! and 0 is not occur!
		exemem.setNew_PC(idexe.getPC()+(byte)Long.parseLong(idexe.getSignExt(), 2));
		exemem.setRT_DATA(idexe.getRT_DATA());
		exemem.setControlBits(idexe.getControlBits());
		
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

		default:
			break;
		}
		return 0;
	}

	public String alu_cu(String func_bit, String Aluop) {
		switch (Aluop) {
		case "10":
			switch (func_bit) {
			case "100000":
				return "0010";
			case "100010":
				return "0110";
			case "100100":
				return "0000";
			case "100101":
				return "0001";
			case "101010":
				return "0111";
			default:
				break;
			}
			break;
		case "00":
			return "0010";
		case "01":
			return "0110";
		default:
			break;
		}
		return null;

	}

}
