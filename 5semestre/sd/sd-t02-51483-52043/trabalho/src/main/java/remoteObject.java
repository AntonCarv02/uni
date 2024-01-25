import java.rmi.RemoteException;
import java.util.*;

public interface remoteObject extends java.rmi.Remote {
    public int sendArtist(artist artist) throws java.rmi.RemoteException;

    public List<artist> searchTableArtist(String fields) throws java.rmi.RemoteException;

    public int sendDonative(donative donative) throws java.rmi.RemoteException;

    public List<donative> searchTableDonatives(String fields) throws java.rmi.RemoteException;

    public String sendUser(user user) throws java.rmi.RemoteException;

    public List<user> searchTableUser(String fields) throws java.rmi.RemoteException;

    public int sendPerformance(performances performances) throws java.rmi.RemoteException;

    public List<performances> searchTablePerformance(String fields) throws java.rmi.RemoteException;
    
    public void changeArtistState(String set, int artistId) throws java.rmi.RemoteException;
    
    public void changeUserState(String username) throws java.rmi.RemoteException;

    public int sendRating(rating avaliacao) throws RemoteException;

    public double ArtistRating(int artistID) throws RemoteException;

    
}