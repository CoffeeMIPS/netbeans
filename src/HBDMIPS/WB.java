package HBDMIPS;
/**
 * Represents WriteBack stage.
 * @author HBD
 */
public class WB {
    
        /**
         * Uses instance of InstructionDecode initialized
         * earlier; Because <b>RegisterFile</b> is contained in it! :|
         */
	ID id;
        
        /**
         * MEM/WB Pipeline Register of MEM stage. 
         */
	MEM_WB memwb;//

	public WB(ID id, MEM_WB memwb) {
		this.id = id;
		this.memwb = memwb;
	}
        
        
        /**
         * Do the job of WriteBack.
         * This includes:
         * 1- Get MEM2REG, REG_WRITE controlBits from MEM/WB Pipeline Register.
         * 2- If something should be written to RegisterFile;
         *      Then decide according to the MEM2REG which one of
         *      ALU_Result [R-Type] or READ_Data [Load] should be used.
         */
	public void action(boolean modebit) {
		boolean MEM2REG = (memwb.getControlBits().charAt(8)) == '0' ? false
				: true;
		boolean REG_WRITE = (memwb.getControlBits().charAt(0)) == '0' ? false
				: true;
		if (REG_WRITE) {
			if (!MEM2REG)
				id.regfile.setReg(memwb.Write_Register,
						memwb.getALU_result());
			else
				id.regfile.setReg(memwb.Write_Register,
						memwb.getREAD_DATA());
		}
	}
}