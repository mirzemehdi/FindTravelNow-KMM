package root

import data.dataModule
import domain.domainModule
import org.koin.core.module.Module
import presentation.presentationModule


val appModules: List<Module> = domainModule + dataModule + presentationModule