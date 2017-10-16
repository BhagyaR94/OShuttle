/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package application.network.webclientconfig.client;


import java.util.logging.Level;


/**
 *
 * @author Chathun
   Author:- Chathuranga Bandara
   Email :- chathunbandara@gmail.com
   Mobile:- +94716271637 
 */
public class test {

    public static void main(String args []){

         // Call<Products> loadRepo = ApiClient.get().getProducts("0","c4ca4238a0b923820dcc509a6f75849b");
        try {
           /* Response<Products> execute = loadRepo.execute();
              
            System.out.println("size "+execute.body().getProducts().size());
            System.out.println("Message "+execute.message() );
            System.out.println("Message "+execute.code() );
            System.out.println("Response Code  "+execute.code() );
            System.out.println("Response Error Body  "+execute.errorBody() );
            System.out.println("Error Body   "+execute.errorBody() );
            System.out.println("Headers   "+execute.headers() );
            System.out.println("Error Body   "+execute.toString() );
            for (ProductList col : execute.body().getProducts()) {
            System.out.println("Name "+col.getDescription());
            }*/
            
            
            /*
            Call<Products> getProducts = ApiClient.get().getProducts("0","c4ca4238a0b923820dcc509a6f75849b");
            getProducts.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> rspns) {
            System.out.println("size "+rspns.body().getProducts().size());
            System.out.println("Message "+rspns.message() );
            System.out.println("Message "+rspns.code() );
            System.out.println("Response Code  "+rspns.code() );
            System.out.println("Response Error Body  "+rspns.errorBody() );
            System.out.println("Error Body   "+rspns.errorBody() );
            System.out.println("Headers   "+rspns.headers() );
            System.out.println("Error Body   "+call.toString() );
            for (ProductList col : rspns.body().getProducts()) {
            System.out.println("Name "+col.getProductDiscription());
            }
            }
            @Override
            public void onFailure(Call<Products> call, Throwable thrwbl) {
            }
            });
             */
      } catch (Exception ex) {
            java.util.logging.Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
  
    
 
 
    
 
    }

}
