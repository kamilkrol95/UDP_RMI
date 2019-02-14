package RMI_Communication;

import java.io.Serializable;
import java.util.Date;

public class Event  implements Serializable {

    private Date date;
    private String text;

    public Event(String Text) {
        text = Text;
        date = new Date();
    }

    public String toString() {
        return text + "   <Date>: " + date + ">";
    }
}
