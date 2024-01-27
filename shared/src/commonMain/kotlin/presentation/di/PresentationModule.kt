package presentation.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.screens.account.profile.ProfileUiStateHolder
import presentation.screens.home.HomeUiStateHolder
import presentation.screens.main.MainUiStateHolder
import presentation.screens.onboarding.OnBoardingUiStateHolder
import presentation.screens.top5flights.Top5FlightsUiStateHolder

val presentationModule = module {
    factoryOf(::MainUiStateHolder)
    factoryOf(::OnBoardingUiStateHolder)
    factoryOf(::Top5FlightsUiStateHolder)
    factoryOf(::HomeUiStateHolder)
    factoryOf(::ProfileUiStateHolder)
}