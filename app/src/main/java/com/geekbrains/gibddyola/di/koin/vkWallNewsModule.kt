package com.geekbrains.gibddyola.di.koin

import com.geekbrains.gibddyola.data.news.RepoVkNewsUseCaseImpl
import com.geekbrains.gibddyola.data.news.VkData
import com.geekbrains.gibddyola.data.news.api.VkApi
import com.geekbrains.gibddyola.domain.news.RepoVkNewsUseCase
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val vkWallNewsModule = module {
    single(named("base_url")) { VkData.BASE_URL }

    single<VkApi>(named("vk_api")) {
        get<Retrofit>().create(VkApi::class.java)
    }

    single<RepoVkNewsUseCase>(named("repo_usecase")) {
        RepoVkNewsUseCaseImpl(get(named("vk_api")))
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(get<String>(named("base_url")))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}