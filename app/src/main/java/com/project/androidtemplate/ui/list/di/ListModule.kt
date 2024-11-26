package com.project.androidtemplate.ui.list.di

import com.project.androidtemplate.ui.list.IListRepository
import com.project.androidtemplate.ui.list.ListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ListModule {

    @Binds
    abstract fun bindsListRepository(listRepository: ListRepository): IListRepository

}