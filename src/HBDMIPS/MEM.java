package HBDMIPS;

import java.util.ArrayList;
import java.util.List;

public class MEM {

	List<String> data_mem;
	EXE_MEM exemem;
	MEM_WB memwb;
	IF stage_if;

	public MEM(EXE_MEM exemem, MEM_WB memwb, IF stage_If) {
		this.exemem = exemem;
		this.memwb = memwb;
		this.stage_if = stage_If;
		data_mem = new ArrayList<>(FileHandler.FileIO.FiletoStringArray("dataCache.txt"));
	}

	public void action() {
		boolean MEM_READ = (exemem.getControlBits().charAt(4)) == '0' ? false
				: true;
		boolean MEM_WRITE = (exemem.getControlBits().charAt(5)) == '0' ? false
				: true;
		boolean BRANCH = (exemem.getControlBits().charAt(6)) == '0' ? false
				: true;

		if (BRANCH && exemem.ZERO) {
			stage_if.setPC(exemem.getNew_PC());
		}
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
        
        
        	public String print() {
                    String print="";
		for (int i = 0; i < data_mem.size(); i++) {
                    print+="Cell  [" + i + "] : " + Integer.parseInt(data_mem.get(i)) +"\n";
		}
                return print;
	}
}
