package FPU;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohammad on 6/27/2016.
 */
public class Registers {
    float[] Regfile = new float[32];

    /**
     * Get whole of current registerFile of floating points operations.
     * @return Regfile - float[32]
     */
    public float[] getRegfile() {
        return Regfile;
    }


    /**
     * Get specified register content.
     * @param regNum - number of register
     * @return Regfile[regNum] - Register content.
     */
    public float getRegfile(int regNum) {
        return Regfile[regNum];
    }


    /**
     * Set whole of RegisterFile represented in an float[].
     * @param regfile - float[32].
     */
    public void setRegfile(float[] regfile) {
        Regfile = regfile;
    }


    /**
     * Set the content of specified register.
     * @param regNum - number of specified register.
     * @param Value - content to be saved to that register.
     */
    public void setReg(int regNum,float Value) {
        Regfile[regNum] = Value;
    }


    /**
     * get RegisterFile in a customized String format.
     * @return Print - representation of current
     * RegisterFile in a String.
     */
    public String print() {
        String print="";
        for (int i = 0; i < 32; i++) {
            if (i < 10) {
                print+="  $" + i + " : " + Regfile[i] + " \t ";
            } else
                print+="$" + i + " : " + Regfile[i] + " \t ";
            if ((i + 1) % 4 == 0) {
                print+="\n";
            }
        }
        return print;
    }

}
