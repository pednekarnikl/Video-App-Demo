package com.video.videoappdemo.di

import com.video.videoappdemo.core.network.HttpClientFactory
import com.video.videoappdemo.home.data.repository.VideoRepositoryImpl
import com.video.videoappdemo.home.data.repository.dataSource.VideoRemoteDataSource
import com.video.videoappdemo.home.data.repository.dataSourceImpl.VideoRemoteDataSourceImpl
import com.video.videoappdemo.home.domain.repository.VideoRepository
import com.video.videoappdemo.home.domain.usecase.GetVideoUseCase
import com.video.videoappdemo.home.presentation.VideoListViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)
        modules(
            sharedModule,
        )
    }

val sharedModule = module {
    single<HttpClientEngine> { Android.create() }
    single<HttpClient> { HttpClientFactory.create(get()) }
    singleOf(::VideoRemoteDataSourceImpl).bind<VideoRemoteDataSource>()
    singleOf(::VideoRepositoryImpl).bind<VideoRepository>()
    singleOf(::GetVideoUseCase)
    viewModel { VideoListViewModel(get()) }
}