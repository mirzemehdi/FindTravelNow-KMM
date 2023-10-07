package root

import data.dataModule
import domain.domainModule
import org.koin.core.module.Module

val appModules: List<Module> = domainModule + dataModule