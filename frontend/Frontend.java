package frontend;

import org.jgroups.JChannel;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.blocks.ResponseMode;
import org.jgroups.blocks.RpcDispatcher;
import org.jgroups.util.RspList;

import api.API;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import utility.GroupUtils;

public class Frontend extends UnicastRemoteObject implements API {

  public static final long serialVersionUID = 42069;

  public final String SERVER_NAME = "random_number_generator";
  public final int REGISTRY_PORT = 1099;

  private JChannel groupChannel;
  private RpcDispatcher dispatcher;

  private final int DISPATCHER_TIMEOUT = 1000;

  public Frontend() throws RemoteException {
    // Connect to the group (channel)
    this.groupChannel = GroupUtils.connect();
    if (this.groupChannel == null) {
      System.exit(1); // error to be printed by the 'connect' function
    }

    // Bind this server instance to the RMI Registry
    this.bind(this.SERVER_NAME);

    // Make this instance of Frontend a dispatcher in the channel (group)
    this.dispatcher = new RpcDispatcher(this.groupChannel, this);
    this.dispatcher.setMembershipListener(new MembershipListener());

  }

  private void bind(String serverName) {
    try {
      Registry registry = LocateRegistry.createRegistry(this.REGISTRY_PORT);
      registry.rebind(serverName, this);
      System.out.println("✅    rmi server running...");
    } catch (Exception e) {
      System.err.println("🆘    exception:");
      e.printStackTrace();
      System.exit(1);
    }
  }

  public float getRandom(int min, int max) throws RemoteException {
    System.out.printf("📩    random number request via rmi\n🧮    %d -> %d\n", min, max);
    try {

      // Call the "generateRandomNumber" function on all the group members, passing 2
      // params of object class integer
      RspList<Integer> responses = this.dispatcher.callRemoteMethods(null, "generateRandomNumber",
          new Object[] { min, max }, new Class[] { int.class, int.class },
          new RequestOptions(ResponseMode.GET_ALL, this.DISPATCHER_TIMEOUT));

      System.out.printf("#️⃣    received %d responses from the group\n", responses.size());
      if (responses.isEmpty()) {
        return 0.0f;
      }
      float average = 0;

      // Iterate through the responses to build a total of all the responses
      for (Integer randNumber : responses.getResults())
        average += randNumber;

      return average / responses.size();
    } catch (Exception e) {
      System.err.println("🆘    dispatcher exception:");
      e.printStackTrace();
    }
    return 0.0f;
  }

  public static void main(String args[]) {
    try {
      new Frontend();
    } catch (RemoteException e) {
      System.err.println("🆘    remote exception:");
      e.printStackTrace();
      System.exit(1);
    }
  }

}
