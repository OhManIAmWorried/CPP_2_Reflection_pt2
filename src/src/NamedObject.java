package src;

/**
 * Created by Oly on 12.03.2017.
 */
public class NamedObject {
    private Object obj;
    private String name;

    public NamedObject(Object obj, String name) {
        this.name = name;
        this.obj = obj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}