/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HBDMIPS;

/**
 *
 * @author cloud
 */
public class Timer {
    int current_coutner=0;
    int counter=0;
    public void set_timer(int num){
        current_coutner=0;
        counter = num;
    }
    public int get_timer(){
        return current_coutner;
    }
    public boolean check_timer(){
        return current_coutner==counter;
    }
    public void action(){
        current_coutner=current_coutner+1;
    }
}
