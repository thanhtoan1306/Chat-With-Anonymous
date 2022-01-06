package event;

/**
 *
 * @author letoan
 */
public interface EventChat {

    public void sendMessage(String text);

    public void reciveMessage(String user, String text);   
}
