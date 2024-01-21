
public class server {

    static BDserver bd;

    public static void main(String[] args) {

        int regPort = 1099;

        if (args.length < 1) {
            System.out.println("Faltam argumentos: Porto");
            System.exit(1);
        }

        try {
            regPort = Integer.parseInt(args[0]);
            bd = new BDserver();
            bd = new BDserver(  "localhost", "bd1","user1","ANTmar02");

            /*
             * //
             * bd = new BDserver( args[1],args[2],args[3],args[4]);
             * localhost bd1 user1 ANTmar02
             * bd = new BDserver( args[1],args[2],args[3],args[4]);

             */
            
            bd.connect();

            remoteObject obj = new remoteObjectImpl(bd);

            java.rmi.registry.Registry registry = java.rmi.registry.LocateRegistry.getRegistry(regPort);

            registry.rebind("remoteobject", obj);

            System.out.println("servidor pronto");
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
