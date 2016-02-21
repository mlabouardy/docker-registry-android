package labouardy.com.dockerregistry.handler;

import com.squareup.okhttp.OkHttpClient;

/**
 * Created by mlabouardy on 21/02/16.
 */
public abstract class APIHandlerImp implements APIHandler {
    protected OkHttpClient client;

    public APIHandlerImp(){
        this.client=new OkHttpClient();
    }
}
