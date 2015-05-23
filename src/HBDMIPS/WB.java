package HBDMIPS;

public class WB {
	ID id;
	MEM_WB memwb;

	public WB(ID id, MEM_WB memwb) {
		this.id = id;
		this.memwb = memwb;
	}

	public void action(boolean mode) {
		boolean MEM2REG = (memwb.getControlBits().charAt(8)) == '0' ? false
				: true;
		boolean REG_WRITE = (memwb.getControlBits().charAt(0)) == '0' ? false
				: true;
		if (REG_WRITE) {
			if (!MEM2REG)
				id.regfile.setRegfile(memwb.Write_Register,
						memwb.getALU_result());
			else
				id.regfile.setRegfile(memwb.Write_Register,
						memwb.getREAD_DATA());
		}
	}
}
