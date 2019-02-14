package RMI_Communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    public String name;
    private List<String> msgs = new ArrayList<String>();
    private String res = "";

    public User(String newName, String newText) {
        name = newName;
        msgs.add(newText);
    }

    public void addMsg(String text) {
        msgs.add(text);
    }

    public String msgToString() {
        for(String s : msgs)
            res += s + ", ";
        return res;
    }

    public String getMsgByIndex(int index) {
        return msgs.get(index);
    }

    public String getName() {
        return name;
    }
}
