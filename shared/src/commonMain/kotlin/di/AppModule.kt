package di

import data.dataModule
import domain.di.domainModule
import org.koin.core.module.Module
import presentation.di.presentationModule


internal expect val platformModule: Module
val appModules: List<Module> = platformModule + domainModule + dataModule + presentationModule