/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Assembler.Instruction;
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
import SyscallAPI.Mem2Cache;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import memory.AddressAllocator;
import static memory.AddressAllocator.parse8DigitHex;
import memory.SegmentDefragmenter;

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
    int interruptReason;
    boolean enableintrrupt;
    int baseAddress;
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
        enableintrrupt = true;
        currentLineOfInstructions = 0;
        aa = new AddressAllocator();
        modeBit = true;//means in kernel Mode at first
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
        public static int Hex2Decimal(String hex) {
        int deci = (Integer.parseInt(hex, 16) - Integer.parseInt("400000", 16)) / 4;
        return deci;
    }
    public boolean runSingleSigle(){
        if (currentLineOfInstructions < lineOfInstructions) {
            stage_if.action(modeBit);
            stage_id.action(modeBit);
            stage_exe.action(modeBit);
            if (stage_exe.isJump()){ // PC & 0xf0000000
                String pc4bit = "0000";
                String func_sign = "0111111111111111111";
                String func_first = pc4bit.concat(func_sign);
                if(stage_exe.getJ_pc().equals(func_first.concat("1100100".concat("00")))){// function 100
                    System.out.println("func100");
                }
                else if(stage_exe.getJ_pc().equals(func_first.concat("1100011".concat("00")))){// function 99
                    //this function change mode of cpu to user mode
                    modeBit=false;
                    enableintrrupt=true;
                    interruptBit=false;
                }
                else if(stage_exe.getJ_pc().equals(func_first.concat("1100010".concat("00")))){// function 98
                    //this function make pcb for cpu
                    
                }  
                else if(stage_exe.getJ_pc().equals(func_first.concat("1100001".concat("00")))){// function 97
                    //this function set time to variable in v0
                    int time = getRegfile().getRegfile(2);
                    timer.set_timer(time);
                }  
                else if(stage_exe.getJ_pc().equals(func_first.concat("0010100".concat("00")))){// function 20
                    //this function change pc to selected program (program pid must saved in v0)
                    modeBit=false;
                    enableintrrupt=true;
                    interruptBit=false;
                    System.out.println("func20");
                    HashMap<Integer, SegmentDefragmenter> programs= aa.getPrograms();
                    int selected = getRegfile().getRegfile(2);
                    SegmentDefragmenter sd = programs.get(selected);
                    String startadd = sd.getCode_seg_start_address();
                    stage_if.setPC(Integer.parseInt(startadd, 16)/4);
                    lineOfInstructions = Integer.parseInt(startadd, 16)+sd.getCode_seg().size();
                    modeBit = false;
                    HashMap<Integer, Instruction> cache = new HashMap<Integer, Instruction>();              

                    int physicalAddress =Hex2Decimal(startadd);
                    baseAddress = physicalAddress;
                    sd.setCode_seg_start_address(parse8DigitHex(physicalAddress));
			for (int i = 0; i < sd.getCode_seg().size(); i++) {
			cache.put(stage_if.getPC()+i,new Instruction( sd.getCode_seg().get(i),parse8DigitHex(physicalAddress)));
			physicalAddress++;
                        }
                    stage_if.setIns_cache(cache);
                }
                else{
                    int old_pc = getPC();
                    int pcbits = old_pc/(2^28);
                    // not added pc to sign but it's ready for use then
                    int offset = Integer.parseInt(stage_exe.getJ_pc(), 2);
                    stage_if.setPC(offset);
                    if(stage_exe.isRegwrite()){ // it's means have jal (our agreement)
                        stage_exe.getExemem().setALU_result(old_pc); // this old_pc \
                        //should not increment because it increment in IF 
                        stage_exe.getExemem().setWrite_Register(31);
                        // use mips structure to save new_pc in ra (not assign directly)
                    }
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
            if (stage_exe.isSyscall()){
                System.out.println("here is Syscall");
                enableintrrupt=false;
                modeBit=true;
                interruptBit=true;
                interruptReason=1; // reason 1 for syscall in program
            }
            stage_mem.action(modeBit);
            stage_wb.action(modeBit);
            currentLineOfInstructions = stage_if.getPC();
            timer.action();
            if(timer.check_timer()&& enableintrrupt){
                interruptReason = 2;
                interruptBit = true;
                modeBit=true; //means in kernel Mode now
            }
            if(interruptBit){
                int syscallReason=getRegisterFile().getRegfile(2);
                switch(interruptReason){
                    case 1:
                        switch (syscallReason){
                        case 10:
                            System.out.println("exit");
                            break;
                        }
                    
                    case 2:
                        //for timer intrupt
                        break;
                        
                    case 3:
                        //for exception
                        break;
                }
                stage_if.setPC(0);
                currentLineOfInstructions = 0;
                lineOfInstructions=stage_if.getIns_mem().size();
                interruptBit=false;
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

    public int getPC(){
        return stage_if.getPC();
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
            if (n==31)
                regTable.setValueAt(Integer.toHexString(regfile.getRegfile(n)*4), r, 2*c+1);
            else
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
    
    Register_file getRegfile(){
            return stage_id.getRegfile();
    }

    void update_other_table(JTable otherTable) {
        otherTable.setValueAt(getPC(), 0, 1);
    }
}