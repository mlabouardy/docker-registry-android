package labouardy.com.dockerregistry.handler;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;

import java.util.List;

import labouardy.com.dockerregistry.model.Registry;

/**
 * Created by mlabouardy on 21/02/16.
 */
public interface APIHandler {
    Call getRepositories(Registry registry, Callback callback);
    Call getImages(Registry registry, String repository, Callback callback);
}
