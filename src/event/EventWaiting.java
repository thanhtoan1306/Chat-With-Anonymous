package event;

/**
 *
 * @author letoan
 */
public interface EventWaiting {

    public void setNickname(String name);

    public void waitingPair(String user);

    public void cancelConverse(String user);

    public void exitConverse();
}
