package labouardy.com.dockerregistry.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by mlabouardy on 20/02/16.
 */
public class Registry implements Serializable{
    private String hostname;
    private Integer port;
    private String version;
    private Authorization auth;
    private Date creationDate;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Authorization getAuth() {
        return auth;
    }

    public void setAuth(Authorization auth) {
        this.auth = auth;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean same(Registry r){
        return hostname.compareTo(r.getHostname())==0 && port==r.getPort();
    }
}
