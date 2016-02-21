package labouardy.com.dockerregistry.model;

import java.io.Serializable;

/**
 * Created by mlabouardy on 21/02/16.
 */
public class RegistrySingleton implements Serializable {
    private static Registry instance;

    private RegistrySingleton(){}

    public static Registry getInstance(){
        if(instance==null)
            instance=new Registry();
        return instance;
    }
}
