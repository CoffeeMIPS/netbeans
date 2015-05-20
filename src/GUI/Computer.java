/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import HBDMIPS.EXE;
import HBDMIPS.EXE_MEM;
import HBDMIPS.ID;
import HBDMIPS.ID_EXE;
import HBDMIPS.IF;
import HBDMIPS.IF_ID;
import HBDMIPS.MEM;
import HBDMIPS.MEM_WB;
import HBDMIPS.Register_file;
import HBDMIPS.Timer;
import HBDMIPS.WB;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import memory.AddressAllocator;

/**
 *
 * @author cloud
 */
public class Computer {
    String filePath = null;
    private String memory;
    private boolean runable;
    int lineOfInstructions;
    int currentLineOfInstructions;
    boolean modeBit;
    boolean interruptBit;
    Timer timer;
    //
    ID_EXE idexe;
    IF_ID ifid;
    EXE_MEM exemem;
    MEM_WB memwb;

    IF stage_if;
    ID stage_id;
    EXE stage_exe;
    MEM stage_mem;
    WB stage_wb;
    AddressAllocator aa;
    
    public Computer(){
        runable = true;
        currentLineOfInstructions = 0;
        aa = new AddressAllocator();
        this.memory="";
        for (int i = 0; i < aa.getMemory().size(); i++) {
            this.memory += (aa.parse8DigitHex(i) + " : " + aa.getMemory().get(aa.parse8DigitHex(i))+"\n");
        }
    }
    
    public Register_file getRegisterFile(){
        return stage_id.getRegfile();
    }
           
    
    public String get_reg_monitor(){
        return stage_id.getRegfile().print();
    }
    
    public String get_cache_mem(){
        return stage_mem.print();
    }
    public boolean runSingleSigle(){
        if (currentLineOfInstructions < lineOfInstructions) {
            stage_if.action();
            stage_id.action();
            stage_exe.action();
            if (stage_exe.isJump()){ // PC & 0xf0000000
                int old_pc = stage_if.getPC();
                int pcbits = old_pc/(2^28);
                // not added pc to sign but it's ready for use then
                int offset = Integer.parseInt(stage_exe.getJ_pc(), 2);
                stage_if.setPC(offset);
                if(stage_exe.isRegwrite()){
                    stage_exe.getExemem().setALU_result(old_pc);
                    stage_exe.getExemem().setWrite_Register(31);
                }
            }
            if (stage_exe.isJumpReg()){
                int pc = stage_exe.getIdexe().getRS_DATA();
                stage_exe.getExemem().setControlBits("0000000000100");
                stage_if.setPC(pc); 
            }
            if (stage_exe.isBranch()) {
                if (exemem.getALU_result() == 0 && !stage_exe.isNot()) {
                    int offset;
                    
                    offset = Integer.parseInt(stage_exe.getIdexe().getSignExt(), 2);
                    stage_if.setPC(stage_if.getPC() + offset);

                }
                if (exemem.getALU_result() != 0 && stage_exe.isNot()) {
                    int offset;
                    offset = Integer.parseInt(stage_exe.getIdexe().getSignExt(), 2);
                    stage_if.setPC(stage_if.getPC() + offset);

                }
            }
            stage_mem.action();
            stage_wb.action();
            currentLineOfInstructions = stage_if.getPC();
            timer.action();
            if(timer.check_timer()){
                interruptBit = true;
                modeBit=true; //means in kernel Mode now
            }
            if(interruptBit){
//                here where must go to IVT
            }
            return true;
        } else {
            System.out.println("end of file ...");
            setRunable(true);
            return false;
        }
    }
    public void run_init(String filePath,int lineOfInstructions) {                                          
        if (isRunable()) {
            timer = new Timer();
            currentLineOfInstructions = 0;
            this.lineOfInstructions = lineOfInstructions;
            idexe = new ID_EXE();
            ifid = new IF_ID();
            exemem = new EXE_MEM();
            memwb = new MEM_WB();

            stage_if = new IF(ifid, filePath);
            stage_id = new ID(ifid, idexe, stage_if);
            stage_exe = new EXE(idexe, exemem);
            stage_mem = new MEM(exemem, memwb, stage_if);
            stage_wb = new WB(stage_id, memwb);
            setRunable(false);
        }
    }                                         

    public String getCurrentIns(){
        return stage_if.getInstruction();
    }   


    /**
     * @return the memory
     */
    public String getMemory() {
        return memory;
    }

    /**
     * @param memory the memory to set
     */
    public void setMemory(String memory) {
        this.memory = memory;
    }

    /**
     * @return the runable
     */
    public boolean isRunable() {
        return runable;
    }

    /**
     * @param runable the runable to set
     */
    public void setRunable(boolean runable) {
        this.runable = runable;
    }

    void Fix_regfile_table(JTable regTable) {
        Register_file regfile = stage_id.getRegfile();
        for (int n = 0; n < 32; n++) {
            int c =0;
            int r = n%8;
            if (n <=7){
                c = 0;
            }else if(n <= 15){
                c = 1;
            }else if(n <= 23){
                c = 2;
            }else{
                c = 3;
            }
            regTable.setValueAt(regfile.getRegfile(n), r, 2*c+1);
        }
        
    }

    void fix_memory_table(JTable memoryTable) {
        DefaultTableModel model=new DefaultTableModel(new Object[]{"Address","Content"}, 0);
        memoryTable.setModel(model);
        for (int i = 0; i < aa.getMemory().size(); i++) {
            model.addRow(new Object[]{aa.parse8DigitHex(i), aa.getMemory().get(aa.parse8DigitHex(i))});
        }            
    }
}