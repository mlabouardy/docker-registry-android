package labouardy.com.dockerregistry.model;

/**
 * Created by mlabouardy on 21/02/16.
 */
public class Image {
    private String name;
    private String tag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
