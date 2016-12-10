package com.example.anthony.testretrofit;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://78.21.58.182:3000/api/").addConverterFactory(GsonConverterFactory.create()).build();
                UserService service = retrofit.create(UserService.class);
                LoginAttempt attempt = new LoginAttempt(new User("testaccount@test.com", "hetwachtwoord"));
                Call<Response> call = service.login(attempt);
                call.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (response.isSuccessful()) {
                            Log.i("response", response.body().user.token);
                        } else {
                            try {
                                Log.i("went wrong", response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                    }
                });
                /*try {
                    ResponseUser token = call.execute().body();
                    return token.token;
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                return "";
            }
        }.execute();
    }

    public interface UserService {
        @Headers({
                "Content-Type: application/json",
                "Accept: application/json"
        })
        @POST("users/login")
        Call<Response> login(@Body LoginAttempt body);

        @Headers({
                "Content-Type: application/json",
                "Accept: application/json"
        })
        @POST("users")
        Call<ResponseBody> register(@Body RequestBody body);
    }
}
