package backend;

import org.jgroups.JChannel;
import org.jgroups.blocks.RpcDispatcher;

import java.util.Random;
import utility.GroupUtils;

public class Backend {

  private JChannel groupChannel;
  private RpcDispatcher dispatcher;
  private int requestCount;

  public Backend() {
    this.requestCount = 0;

    // Connect to the group (channel)
    this.groupChannel = GroupUtils.connect();
    if (this.groupChannel == null) {
      System.exit(1); // error to be printed by the 'connect' function
    }

    // Make this instance of Backend a dispatcher in the channel (group)
    this.dispatcher = new RpcDispatcher(this.groupChannel, this);
  }

  public int generateRandomNumber(int min, int max) {
    this.requestCount++;
    System.out.printf("📩   random number request | %d -> %d\n🧮    total requests: %d\n", min, max, this.requestCount);
    return (new Random()).nextInt(max - min) + min;
  }

  public static void main(String args[]) {
    new Backend();
  }

}
