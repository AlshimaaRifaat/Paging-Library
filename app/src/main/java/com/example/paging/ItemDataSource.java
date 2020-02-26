package com.example.paging;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDataSource extends PageKeyedDataSource<Integer,Item> {
 public static final int PAGE_SIZE=50;
 private static final int FIRST_PAGE=1;
 private static final String SITE_NAME="stackoverflow";





    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Item> callback) {
             RetrofitClient .getInstance().getApi().getAnswers(FIRST_PAGE,15,SITE_NAME)
                     .enqueue(new Callback<StackApiResponse>() {
                         @Override
                         public void onResponse(Call<StackApiResponse> call, Response<StackApiResponse> response) {
                             if (response.body()!=null)
                             {

                                 callback.onResult(response.body().items,null,FIRST_PAGE+1);
                                 //Toast.makeText(, "first page loaded", Toast.LENGTH_SHORT).show();
                             }
                         }

                         @Override
                         public void onFailure(Call<StackApiResponse> call, Throwable t) {

                         }
                     });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Item> callback) {
        RetrofitClient.getInstance().getApi().getAnswers(params.key,20,SITE_NAME)
                .enqueue(new Callback<StackApiResponse>() {
                    @Override
                    public void onResponse(Call<StackApiResponse> call, Response<StackApiResponse> response) {
                        Integer adjacentKey=(params.key>1)? params.key-1:null;
                        if(response.body()!=null)
                        {
                            callback.onResult(response.body().items,adjacentKey);
                           // Toast.makeText(mCtx, "second page loaded", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<StackApiResponse> call, Throwable t) {

                    }
                });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Item> callback) {
        RetrofitClient.getInstance().getApi().getAnswers(params.key,15,SITE_NAME)
                .enqueue(new Callback<StackApiResponse>() {
                    @Override
                    public void onResponse(Call<StackApiResponse> call, Response<StackApiResponse> response) {
                        if(response.body()!=null)
                        {
                            Integer key=response.body().has_more? params.key+1 :null;
                            callback.onResult(response.body().items,key);
                          //  Toast.makeText(mCtx, "third page loaded", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<StackApiResponse> call, Throwable t) {

                    }
                });
    }
}
