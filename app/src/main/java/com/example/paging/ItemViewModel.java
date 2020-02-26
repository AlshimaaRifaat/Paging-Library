package com.example.paging;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import java.util.List;

public class ItemViewModel extends ViewModel {

    LiveData<PagedList<Item>> pagedListLiveData;
    LiveData<PageKeyedDataSource<Integer,Item>> sourceLiveData;

    public ItemViewModel() {
        ItemDataSourceFactory itemDataSourceFactory=new ItemDataSourceFactory();
        sourceLiveData=itemDataSourceFactory.getMutableLiveDataSource();

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(ItemDataSource.PAGE_SIZE).build();

        //Building the paged list
        pagedListLiveData = (new LivePagedListBuilder(itemDataSourceFactory, pagedListConfig))
                .build();
    }
}
