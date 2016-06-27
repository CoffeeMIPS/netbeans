package FPU;

/**
 * Created by mohammad on 6/27/2016.
 */
public class Controller {

    private Registers floatRegisters = new Registers();
    private Add add = new Add();

    public Controller(){
        Registers floatRegisteFile = new Registers();
    }
    public Registers getFloatRegisters() {
        return floatRegisters;
    }


}
