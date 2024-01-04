import java.util.*;

public interface remoteObject extends java.rmi.Remote{
    public int sendArtist(artist artist) throws java.rmi.RemoteException;
    public List<artist> searchTableArtist(String fields) throws java.rmi.RemoteException;
    public int sendDonative(donative donative) throws java.rmi.RemoteException;
    public List<donative> searchTableDonatives(String fields) throws java.rmi.RemoteException;
    public void changeState(Boolean state, int artistId) throws java.rmi.RemoteException;
}