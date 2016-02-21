package labouardy.com.dockerregistry.handler;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;

import java.util.List;

import labouardy.com.dockerregistry.model.Registry;

/**
 * Created by mlabouardy on 21/02/16.
 */
public class APIv1 extends APIHandlerImp {

    @Override
    public Call getRepositories(Registry registry, Callback callback) {
        String url="http://"+registry.getHostname()+":"+registry.getPort()+"/v1/search";
        Request request=new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    public Call getImages(Registry registry, String repository, Callback callback){
        String url="http://"+registry.getHostname()+":"+registry.getPort()+"/v1/repositories/"+repository+"/tags";
        Request request=new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

}
