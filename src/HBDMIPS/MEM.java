
package HBDMIPS;

import java.util.ArrayList;
import java.util.List;
/**
 * Represents Memory Stage.
 * @author HBD
 */
public class MEM {

        /**
         * Stored as a List of String Represents Memory.
         */
	List<String> data_mem;
        /**
         * EXE/MEM Pipeline Register of MEM stage.
         */
	EXE_MEM exemem;
        /**
         * MEM/WB Pipeline Register of MEM stage.
         */
	MEM_WB memwb;
        /**
         * ???????????????????????????????????????????????????????????????????
         */
	IF stage_if;

	public MEM(EXE_MEM exemem, MEM_WB memwb, IF stage_If) {
		this.exemem = exemem;
		this.memwb = memwb;
		this.stage_if = stage_If;
		data_mem = new ArrayList<>(FileHandler.FileIO.FiletoStringArray("dataCache.txt"));
	}
        /**
         * Do the job of Memory.
         * This includes:
         * 1- Get controlBits from EXE/MEM Pipeline Register.
         * 2- If MEM_Write is Set then we should write Data which 
         *    is stored in RT_DATA in EXE/MEM, to the address of 
         *    memory that ALU_Result points to.
         * 3- If MEM_READ is Set then we should read Data which
         *    ALU_Result points to its address in memory.
         * 4- Store ALU_Result, WriteRegister, controlBits in 
         *    in MEM/WB Pipeline Register.
         */
	public void action(boolean modebit) {
		boolean MEM_READ = (exemem.getControlBits().charAt(4)) == '0' ? false
				: true;
		boolean MEM_WRITE = (exemem.getControlBits().charAt(5)) == '0' ? false
				: true;
		if (MEM_WRITE) {
			data_mem.set(exemem.getALU_result(), Integer.toString(exemem.getRT_DATA()));
			System.out.println("datamem with this address : "+ exemem.getALU_result() + "\t"+ data_mem.get(exemem.getALU_result()));
		}
		// MEM_READ
		if (MEM_READ) {
			memwb.setREAD_DATA(Integer.parseInt(data_mem.get(exemem.getALU_result())));
		}
		memwb.setALU_result(exemem.getALU_result());
		memwb.setWrite_Register(exemem.getWrite_Register());
		memwb.setControlBits(exemem.getControlBits());

	}
        
        
        /**
         * View current Memory.
         * @return Print - memory in a String each cell in a line formatted. 
         */        
        public String print() {
            String print="";
            for (int i = 0; i < data_mem.size(); i++) {
                print+="Cell  [" + i + "] : " + Integer.parseInt(data_mem.get(i)) +"\n";
            }
            return print;
        }
}