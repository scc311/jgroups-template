package utility;

import org.jgroups.JChannel;

public class GroupUtils {

  /**
   * Returns a JGroup Channel in which a connection has already been established.
   * The channel name is taken from the "GROUP" env var, or a default is used if
   * no var present. note: this channel will discard self messages.
   * 
   * @return the connected jgroups channel or null if an error occurred.
   */
  public static JChannel connect() {
    String channelName = System.getenv("GROUP") == null ? "DEFAULT_GROUP" : System.getenv("GROUP");
    try {
      JChannel channel = new JChannel();
      channel.connect(channelName);
      System.out.printf("✅    connected to jgroups channel: %s\n", channelName);
      channel.setDiscardOwnMessages(true);
      return channel;
    } catch (Exception e) {
      System.err.printf("🆘    could not connect to jgroups channel: %s\n", channelName);
    }

    return null;
  }

}
