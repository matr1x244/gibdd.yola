package com.geekbrains.gibddyola.di.koin

import com.geekbrains.gibddyola.data.news.web.RepoVkGroupUseCaseImpl
import com.geekbrains.gibddyola.data.news.web.RepoVkNewsUseCaseImpl
import com.geekbrains.gibddyola.data.news.web.VkData
import com.geekbrains.gibddyola.data.news.web.api.VkApi
import com.geekbrains.gibddyola.domain.news.RepoVkGroupUseCase
import com.geekbrains.gibddyola.domain.news.RepoVkNewsUseCase
import com.geekbrains.gibddyola.ui.news.details.VkNewsDetailsFragment
import com.geekbrains.gibddyola.ui.news.details.recyclerView.VkNewsDetailsImageRVAdapter
import com.geekbrains.gibddyola.ui.news.details.recyclerView.VkNewsDetailsVideoRVAdapter
import com.geekbrains.gibddyola.ui.news.list.VkNewsFragment
import com.geekbrains.gibddyola.ui.news.list.recyclerView.VkNewsRVAdapter
import com.geekbrains.gibddyola.ui.news.list.viewModel.VkNewsViewModel
import com.geekbrains.gibddyola.utils.checkConnection.ConnectionChecker
import com.geekbrains.gibddyola.utils.flow_loading.LoadingApi
import com.geekbrains.gibddyola.utils.flow_loading.LoadingApiImpl
import com.geekbrains.gibddyola.utils.flow_loading.LoadingDataSource
import com.geekbrains.gibddyola.utils.flow_loading.LoadingFlowRepository
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val vkNewsKoinModule = module {
    single(named("base_url")) { VkData.API_URL }

    single<VkApi>(named("vk_api")) {
        get<Retrofit>().create(VkApi::class.java)
    }

    single<RepoVkNewsUseCase>(named("repo_usecase")) {
        RepoVkNewsUseCaseImpl(get(named("vk_api")))
    }

    single<RepoVkGroupUseCase>(named("repo_group_usecase")) {
        RepoVkGroupUseCaseImpl(get(named("vk_api")))
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(get<String>(named("base_url")))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<LoadingApi>(named("loadingApi")) {
        LoadingApiImpl()
    }

    single(named("loadingDataSource")) {
        LoadingDataSource(get(named("loadingApi")))
    }

    single(named("loadingFlowRepository")) {
        LoadingFlowRepository(get(named("loadingDataSource")))
    }

    scope<VkNewsFragment> {
        scoped(named("vk_news_rv_adapter")) {
            VkNewsRVAdapter()
        }

        scoped(named("connection_checker")) {
            ConnectionChecker(androidContext())
        }

        viewModel(named("vk_news_view_model")) {
            VkNewsViewModel(
                get(named("repo_usecase")),
                get(named("repo_group_usecase")),
                get(named("loadingFlowRepository")),
                get(named("connection_checker"))
            )
        }
    }

    scope<VkNewsDetailsFragment> {
        scoped<VkNewsDetailsImageRVAdapter>(named("vk_news_details_image_rv_adapter")) {
            VkNewsDetailsImageRVAdapter()
        }

        scoped<VkNewsDetailsVideoRVAdapter>(named("vk_news_details_video_rv_adapter")) {
            VkNewsDetailsVideoRVAdapter()
        }
    }
}