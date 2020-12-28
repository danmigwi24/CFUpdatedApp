package com.dan.jamiicfapp

import android.app.Application
import com.dan.jamiicfapp.data.db.AppDatabase
import com.dan.jamiicfapp.data.db.preference.SessionManager
import com.dan.jamiicfapp.data.network.JcaApiService
import com.dan.jamiicfapp.data.network.NetworkInterceptor
import com.dan.jamiicfapp.data.repository.*
import com.dan.jamiicfapp.ui.adminui.ui.cruddisability.viewmodel.CrudDisabilityViewModelFactory
import com.dan.jamiicfapp.ui.auth.viewmodel.AuthViewModelFactory
import com.dan.jamiicfapp.ui.commentui.cviewmodel.CommentViewModelFactory
import com.dan.jamiicfapp.ui.jcahome.ui.feedbacks.feedbackviewmodel.FeedbackViewModelFactory
import com.dan.jamiicfapp.ui.jcahome.ui.home.homeviewmodel.HomeViewModelFactory
import com.dan.jamiicfapp.ui.jcahome.ui.recordcase.recordveiwmodel.RecordcaseViewModelFactory
import com.dan.jamiicfapp.ui.paymentmode.dviewmodel.MpesaViewModelProvider
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class KodeinApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@KodeinApplication))

        //Necessary dependency
        bind() from singleton { SessionManager(instance()) }
        bind() from singleton { NetworkInterceptor(instance()) }
        bind() from singleton { JcaApiService(instance()) }
        bind() from singleton { AppDatabase(instance()) }

        //Login Registration dependency
        bind() from singleton { AuthRepository(instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }


        //Home dependency
        bind() from singleton { PwdsRepository(instance(), instance(), instance()) }
        bind() from provider {
            HomeViewModelFactory(
                instance()
            )
        }

        //Donation and Payments
        bind() from singleton { DonateRepository(instance()) }
        bind() from provider { MpesaViewModelProvider(instance()) }

        //Recordcase
        bind() from singleton { RecordcaseRepository(instance(), instance(), instance()) }
        bind() from provider {
            RecordcaseViewModelFactory(
                instance()
            )
        }

        //FeedBack
        bind() from singleton { FeedBackRepository(instance()) }
        bind() from provider { FeedbackViewModelFactory(instance()) }

        //Comment
        bind() from singleton { CommentRepository(instance(), instance(), instance()) }
        bind() from provider { CommentViewModelFactory(instance()) }

        //Edit

        bind() from provider { CrudDisabilityViewModelFactory(instance()) }

    }
}