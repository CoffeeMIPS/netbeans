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
    //Static properties of this calss
    public static final int READY_STATE = 0;
    public static final int EXECUTE_STATE = 1;
    public static final int BUSY_STATE = 2;
    public static final int FINISH_STATE = 3;
    
    //Process identification data
    private int pid;
    int ppid;
    int memarea;   
    
    //Processor state data
    Register_file regs;
    int stackPointer;
    int framePointer;
    
    //Process control information
    private int schedulingState;
    private int inputTime;
    
    public PCB(int pid,int schedulingState,int inputTime){
        this.pid = pid;
        this.schedulingState = schedulingState;
        this.inputTime = inputTime;                
    }

    /**
     * @return the inputTime
     */
    public int getInputTime() {
        return inputTime;
    }

    /**
     * @param inputTime the inputTime to set
     */
    public void setInputTime(int inputTime) {
        this.inputTime = inputTime;
    }

    /**
     * @return the pid
     */
    public int getPid() {
        return pid;
    }

    /**
     * @param pid the pid to set
     */
    public void setPid(int pid) {
        this.pid = pid;
    }

    /**
     * @return the schedulingState
     */
    public int getSchedulingState() {
        return schedulingState;
    }

    /**
     * @param schedulingState the schedulingState to set
     */
    public void setSchedulingState(int schedulingState) {
        this.schedulingState = schedulingState;
    }
}
