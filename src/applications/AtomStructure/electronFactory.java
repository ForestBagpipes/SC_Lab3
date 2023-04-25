package applications.AtomStructure;

/**
 * 电子的工厂类
 */
public class electronFactory {
    public electron createElectron_2(String name){
        return new electron(name);
    }

    public electron createElectron_1(){
        return new electron();
    }
}
