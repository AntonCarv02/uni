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
    public List<donative> searchTableDonatives(String fields) throws java.rmi.RemoteException{
        return bd.consultDonatives(fields);
    }
    public int sendDonative(donative donative) throws java.rmi.RemoteException{
        return bd.insertTableDonatives(donative);
    }
    public String sendUser(user user) throws java.rmi.RemoteException{
        return bd.insertTableUsers(user);
    }
    public List<user> searchTableUser(String fields) throws java.rmi.RemoteException{
        return bd.consultUsers(fields);
    }
    public int sendPerformance(performances performances) throws java.rmi.RemoteException{
        return bd.insertTablePerformances(performances);
    }
    public List<performances> searchTablePerformance(String fields) throws java.rmi.RemoteException{
        return bd.consultPerformances(fields);
    }

  
    /*ADMIN CLIENT */
    public void changeUserState(String username) throws java.rmi.RemoteException{
        bd.changeUserState(username);
    }
    public void changeArtistState(String set, int artistId) throws java.rmi.RemoteException{
        bd.changeArtist(set,artistId);
    }

    public int sendRating(rating avaliacao)  throws java.rmi.RemoteException{
        return bd.registerRating(avaliacao);
    }

    public double ArtistRating(int artistID)  throws java.rmi.RemoteException{
        return bd.AverageArtistRating(artistID);
    }

   

  
}
