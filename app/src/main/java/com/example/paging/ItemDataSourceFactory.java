package com.example.paging;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

public class ItemDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer,Item>> mutableLiveDataSource=new MutableLiveData<>();


    @NonNull
    @Override
    public DataSource create() {
        ItemDataSource itemDataSource=new ItemDataSource();
        mutableLiveDataSource.postValue(itemDataSource);
        return itemDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Item>> getMutableLiveDataSource() {
        return mutableLiveDataSource;
    }
}
