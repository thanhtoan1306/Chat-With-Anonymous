package common;

import java.util.ArrayList;

/**
 *
 * @author letoan
 */
public class ChatModel {

    private String nickname1;
    private String nickname2;
    private boolean confirmChatWith1;
    private boolean confirmChatWith2;

    public ChatModel() {
    }

    public ChatModel(String nickname1, String nickname2) {
        this.nickname1 = nickname1;
        this.nickname2 = nickname2;
        this.confirmChatWith1 = false;
        this.confirmChatWith2 = false;
    }

    public String getNickname1() {
        return nickname1;
    }

    public void setNickname1(String nickname1) {
        this.nickname1 = nickname1;
    }

    public String getNickname2() {
        return nickname2;
    }

    public void setNickname2(String nickname2) {
        this.nickname2 = nickname2;
    }

    public boolean isConfirmChatWith1() {
        return confirmChatWith1;
    }

    public void setConfirmChatWith1(boolean confirmChatWith1) {
        this.confirmChatWith1 = confirmChatWith1;
    }

    public boolean isConfirmChatWith2() {
        return confirmChatWith2;
    }

    public void setConfirmChatWith2(boolean confirmChatWith2) {
        this.confirmChatWith2 = confirmChatWith2;
    }

//    public boolean isResponse1() {
//        return confirmChatWith1;
//    }
//
//    public void setResponse1(boolean response1) {
//        this.confirmChatWith1 = response1;
//    }
//
//    public boolean isResponse2() {
//        return confirmChatWith2;
//    }
//
//    public void setResponse2(boolean response2) {
//        this.confirmChatWith2 = response2;
//    }

    public ArrayList<String> nicknameList() {

        ArrayList<String> data = new ArrayList<String>() {
            {
                add("Shark");
                add("Bào Ngư");
                add("Tị");
                add("Tí");
                add("Thìn");
                add("Super Kid");
                add("Super Ken");
                add("Superman");
                add("Black widow");
                add("Captain America");
                add("Hawkeye");
                add("Spiderman");
            }
        };
        return data;
    }
}
