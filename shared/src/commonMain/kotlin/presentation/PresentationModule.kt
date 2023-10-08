package presentation

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.screens.onboarding.OnBoardingStateHolder

val presentationModule = module {
    factoryOf(::OnBoardingStateHolder)
}