package common;

import java.util.ArrayList;

/**
 *
 * @author letoan
 */
public class ChatModel {

    private String nickname1;
    private String nickname2;
    private boolean response1;
    private boolean response2;

    public ChatModel() {
    }

    public ChatModel(String nickname1, String nickname2) {
        this.nickname1 = nickname1;
        this.nickname2 = nickname2;
        this.response1 = false;
        this.response2 = false;
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

    public boolean isResponse1() {
        return response1;
    }

    public void setResponse1(boolean response1) {
        this.response1 = response1;
    }

    public boolean isResponse2() {
        return response2;
    }

    public void setResponse2(boolean response2) {
        this.response2 = response2;
    }

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
