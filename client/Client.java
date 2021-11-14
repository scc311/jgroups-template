package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import api.API;

public class Client {

  private final String SERVER_NAME = "random_number_generator";
  public final int REGISTRY_PORT = 1099;

  public Client(int min, int max) {
    try {
      Registry registry = LocateRegistry.getRegistry();
      API server = (API) registry.lookup(this.SERVER_NAME);
      float result = server.getRandom(min, max);
      System.out.printf("🎉 your random number is: %f\n", result);
      System.exit(0);
    } catch (Exception e) {
      System.err.println("🆘 exception:");
      e.printStackTrace();
      System.exit(1);
    }
  }

  public static void main(String[] args) {

    if (args.length != 2) {
      System.out.printf("🚨 error: must supply min and mac values for the random number\n\tusage | <command> min(int) max(int)");
      System.exit(1);
    }
    int min = Integer.parseInt(args[0]);
    int max = Integer.parseInt(args[1]);

    new Client(min, max);
  }
}