/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SyscallAPI;

import HBDMIPS.Register_file;

/**
 *
 * @author cloud
 */
public class PCB {
    //Process identification data
    int pid;
    int ppid;
    int memarea;
    
    //Processor state data
    Register_file regs;
    int stackPointer;
    int framePointer;
    
    //Process control information
    int schedulingState;
}
