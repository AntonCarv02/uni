import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class remoteObjectImpl extends UnicastRemoteObject implements remoteObject {
    private BDserver bd;
    public remoteObjectImpl(BDserver bd) throws java.rmi.RemoteException {
        super();
        this.bd = bd;
    }

    public int sendArtist(artist artist) throws java.rmi.RemoteException{
        return bd.insertTableArtist(artist);
    }
    public List<artist> searchTableArtist(String fields) throws java.rmi.RemoteException{
        return bd.consultArtists(fields);
    }
    public List<donative> searchTableDonatives(String fields){
        return bd.consultDonatives(fields);
    }
    public int sendDonative(donative donative){
        return bd.insertTableDonatives(donative);
    }


    /*ADMIN CLIENT */
    public void changeState(Boolean state, int artistId) throws java.rmi.RemoteException{
         bd.changeArtistState(state, artistId);
    }
}
