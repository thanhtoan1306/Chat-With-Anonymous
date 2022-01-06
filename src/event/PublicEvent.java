package event;

/**
 *
 * @author letoan
 */
public class PublicEvent {

    private static PublicEvent instance;
    private EventChoose eventChoose;
    private EventMain eventMain;
    private EventWaiting eventWaiting;
    private EventChat eventChat;

    public static PublicEvent getInstance() {
        if (instance == null) {
            instance = new PublicEvent();
        }
        return instance;
    }

    public PublicEvent() {
    }

    public void addEventChoose(EventChoose event) {
        this.eventChoose = event;
    }

    public EventChoose getEventChoose() {
        return eventChoose;
    }

    public void addEventMain(EventMain event) {
        this.eventMain = event;
    }

    public EventMain getEventMain() {
        return eventMain;
    }

    public void addEventWaiting(EventWaiting event) {
        this.eventWaiting = event;
    }

    public EventWaiting getEventWaiting() {
        return eventWaiting;
    }
    
    public void addEventChat(EventChat event) {
        this.eventChat = event;
    }

    public EventChat getEventChat() {
        return eventChat;
    }

}
