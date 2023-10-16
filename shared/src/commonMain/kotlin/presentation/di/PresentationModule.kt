package presentation.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.screens.home.HomeStateHolder
import presentation.screens.onboarding.OnBoardingStateHolder
import presentation.screens.top5flights.Top5FlightsStateHolder

val presentationModule = module {
    factoryOf(::OnBoardingStateHolder)
    factoryOf(::Top5FlightsStateHolder)
    factoryOf(::HomeStateHolder)
}