
public class Laptop extends Device{

    protected boolean ris;

    Laptop(String brand, double weight, boolean risc){
        super(brand,weight, 32768);
        setRisc(risc);
        
    }

    public boolean isRisc() {
        return ris;
    }

    public void setRisc(boolean risc) {
        this.ris = risc;
    }

    public  boolean isHeavy(){
        if(getWeight() > 750){
            return true;
        }
        return false;
    }

}
