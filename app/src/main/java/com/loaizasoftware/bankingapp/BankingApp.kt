package com.loaizasoftware.bankingapp

import android.app.Application
import com.loaizasoftware.bankingapp.injection.appModule
import com.loaizasoftware.bankingapp.injection.loginModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

//@HiltAndroidApp
class BankingApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BankingApp)
            modules(appModule, loginModule)
        }

    }

}