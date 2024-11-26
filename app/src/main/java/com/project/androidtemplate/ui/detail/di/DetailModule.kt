package com.project.androidtemplate.ui.detail.di

import com.project.androidtemplate.ui.detail.DetailRepository
import com.project.androidtemplate.ui.detail.IDetailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class DetailModule {

    @Binds
    abstract fun bindsDetailRepository(detailRepository: DetailRepository): IDetailRepository


}