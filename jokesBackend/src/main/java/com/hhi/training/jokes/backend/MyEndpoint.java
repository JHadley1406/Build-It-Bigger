/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.hhi.training.jokes.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import training.hhi.com.jokelibrary.*;

import javax.inject.Named;

/** An endpoint class we are exposing */
@Api(
  name = "myApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "backend.jokes.training.hhi.com",
    ownerName = "backend.jokes.training.hhi.com",
    packagePath=""
  )
)
public class MyEndpoint {

    @ApiMethod(name = "joke", httpMethod = "get")
    public MyBean joke(){
        Jokes joke = new Jokes();
        MyBean response = new MyBean();
        response.setData(joke.getJokes());
        return response;

    }

}
