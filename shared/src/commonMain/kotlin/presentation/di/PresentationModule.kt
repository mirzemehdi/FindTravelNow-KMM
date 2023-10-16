package presentation.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.screens.home.HomeUiStateHolder
import presentation.screens.onboarding.OnBoardingUiStateHolder
import presentation.screens.top5flights.Top5FlightsUiStateHolder

val presentationModule = module {
    factoryOf(::OnBoardingUiStateHolder)
    factoryOf(::Top5FlightsUiStateHolder)
    factoryOf(::HomeUiStateHolder)
}