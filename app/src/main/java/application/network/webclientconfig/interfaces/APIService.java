package application.network.webclientconfig.interfaces;

import application.networkPojo.response.InquiryListPojo;
import application.networkPojo.response.InquiryResponsePojo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * @author Chathun
 *         Author:- Chathuranga Bandara
 *         Email :- chathunbandara@gmail.com
 *         Mobile:- +94716271637
 */
public interface APIService {

    @POST("saveInquiry")
    Call<InquiryResponsePojo> saveInquiry(@Body String data, @Query("token") String token);

    @GET("inquiryList")
    Call<InquiryListPojo> inquiryList(@Query("token") String token);

//    @GET("getSources")
//    Call<SourcePojo> getSources(@Query("token") String token);
//
//    @GET("getSectors")
//    Call<SectorPojo> getSectors(@Query("token") String token);

}
