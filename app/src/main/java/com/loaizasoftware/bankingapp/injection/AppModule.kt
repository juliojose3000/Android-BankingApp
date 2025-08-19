package com.loaizasoftware.bankingapp.injection

import com.loaizasoftware.data.local.AppDatabase
import com.loaizasoftware.data.local.dao.UserDao
import com.loaizasoftware.data.local.datasources.UserLocalDataSource
import com.loaizasoftware.data.repository.UserRepositoryImpl
import com.loaizasoftware.domain.repository.UserRepository
import com.loaizasoftware.domain.usecases.UserSignInUseCase
import com.loaizasoftware.feature_login.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<AppDatabase> { AppDatabase.getDatabase(androidContext()) }
    single<UserDao> { get<AppDatabase>().userDao() }
    single<UserLocalDataSource> { UserLocalDataSource(get<UserDao>()) }
    single<UserRepository> { UserRepositoryImpl(get<UserLocalDataSource>()) }
}

val loginModule = module {
    viewModel { LoginViewModel(signInUseCase = get()) }
    single<UserSignInUseCase> { UserSignInUseCase(get<UserRepository>()) }
}