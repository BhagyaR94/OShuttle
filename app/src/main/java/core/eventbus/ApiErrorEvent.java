package core.eventbus;

import retrofit.RetrofitError;

/**
 * Created by Rasika Nagahawaththa on 6/22/17.
 * e-Mail rasika1600@gmail.com
 * Contact +94779077642
 */

public class ApiErrorEvent {
    RetrofitError error;

    public ApiErrorEvent(RetrofitError error){
        this.error = error;
    }

    public RetrofitError getError(){
        return error;
    }
}
