
public class SmartPhone extends Device {
    protected long imei;

    public SmartPhone(double weight, long memory, long imei) {
        super(("Bravo de Esmolfe"),weight, memory);
        setImei(imei);
    }

    public long getImei() {
        return imei;
    }


    public void setImei(long imei) {
        this.imei = imei;
    }


    public  boolean isHeavy(){
        if(getWeight() > 100){
            return true;
        }
        return false;
    }
}
