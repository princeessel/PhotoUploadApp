package com.example.photouploadapp.component

import com.example.photouploadapp.*
import com.example.photouploadapp.module.RoomDatabaseModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomDatabaseModule::class])
interface ApplicationComponent {
    fun inject(loginFragment: MainFragment)
    fun inject(profileFragment: ProfileFragment)
    fun inject(signUpFragment: SignUpFragment)
    fun inject(paymentFragment: PaymentFragment)
    fun inject(editPaymentFragment: EditPaymentFragment)
    fun inject(userActivitiesFragment: UserActivitiesFragment)
}
