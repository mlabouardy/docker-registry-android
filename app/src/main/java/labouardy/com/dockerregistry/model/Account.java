package labouardy.com.dockerregistry.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mlabouardy on 21/02/16.
 */
public class Account implements Serializable{
    private static Account instance;
    private List<Registry> registries;

    private Account(){
        this.registries=new ArrayList();
    }

    public static Account getInstance(){
        if(instance==null)
            instance=new Account();
        return instance;
    }

    public void add(Registry registry){
        registries.add(registry);
    }

    public List<Registry> getRegistries(){
        return registries;
    }

    public boolean alreadyExist(Registry r){
        for(Registry tmp:registries){
            if(tmp.same(r))
                return true;
        }
        return false;
    }

    public void save(List<Registry> registries){
        this.registries=registries;
    }
}
