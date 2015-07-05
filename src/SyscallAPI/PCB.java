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
    private Register_file regs;
    private int PC;
    int stackPointer;
    int framePointer;
    
    //Process control information
    private int schedulingState;
    private int inputTime;
    private int wait_time;
    
    public PCB(int pid,int schedulingState,int inputTime){
        this.pid = pid;
        this.schedulingState = schedulingState;
        this.inputTime = inputTime;
        this.regs = new Register_file("NEW");
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

    /**
     * @return the regs
     */
    public Register_file getRegs() {
        return regs;
    }

    /**
     * @param regs the regs to set
     */
    public void setRegs(Register_file regs) {
        this.regs = regs;
    }

    /**
     * @return the PC
     */
    public int getPC() {
        return PC;
    }

    /**
     * @param PC the PC to set
     */
    public void setPC(int PC) {
        this.PC = PC;
    }
    public void waitAction(){
        if(wait_time>0)
            wait_time=wait_time-1;
        else if(schedulingState == BUSY_STATE){
            schedulingState = READY_STATE;
        }
    }
    /**
     * @return the wait_time
     */
    public int getWait_time() {
        return wait_time;
    }

    /**
     * @param wait_time the wait_time to set
     */
    public void setWait_time(int wait_time) {
        this.wait_time = wait_time;
    }
}
