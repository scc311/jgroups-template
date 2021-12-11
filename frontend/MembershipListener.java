package frontend;

import org.jgroups.Address;
import org.jgroups.View;

/*
 * MembershipListener implements the JGroups Membership Listener interface in a
 * way that can be useful when debugging small JGroups applications. 
 *
 * Reading the documentation for these is particularly important.
 *
 * For more information about this, see
 * http://jgroups.org/javadoc3/org/jgroups/MembershipListener.html
 */
public class MembershipListener implements org.jgroups.MembershipListener {

  /**
   * Called when a change in membership has occurred. No long running actions,
   * sending of messages or anything that could block should be done in this
   * callback. If some long running action needs to be performed, it should be
   * done in a separate thread.
   */
  public void viewAccepted(View newView) {
    System.out.printf("👀    jgroups view changed\n✨    new view: %s\n", newView.toString());
  }

  /**
   * Called whenever a member is suspected of having crashed, but has not yet
   * been excluded.
   */
  public void suspect(Address suspectedMember) {
    System.out.printf("👀    jgroups view suspected member crash: %s\n", suspectedMember.toString());
  }

  /**
   * Called (usually by the FLUSH protocol), as an indication that the member
   * should stop sending messages. Any messages sent after returning from this
   * callback might get blocked by the FLUSH protocol. When the FLUSH protocol
   * is done, and messages can be sent again, the FLUSH protocol will simply
   * unblock all pending messages.
   */
  public void block() {
    System.out.printf("👀    jgroups view block indicator\n");
  }

  /**
   * Called <em>after</em> the FLUSH protocol has unblocked previously blocked
   * senders, and messages can be sent again. This callback only needs to be
   * implemented if we require a notification of that.
   *
   * <p>
   * Note that during new view installation we provide guarantee that unblock
   * invocation strictly follows view installation at some node A belonging to
   * that view . However, some other message M may squeeze in between view and
   * unblock callbacks.
   *
   * For more details see https://jira.jboss.org/jira/browse/JGRP-986
   */
  public void unblock() {
    System.out.printf("👀    jgroups view unblock indicator\n");
  }

}
