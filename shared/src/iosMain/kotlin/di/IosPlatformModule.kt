package di

import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import util.AppOpenerUtil
import util.AppOpenerUtilImpl
import util.AppVersion
import util.IosAppVersion


internal actual val platformModule: Module = module {
    factoryOf(::AppOpenerUtilImpl) bind AppOpenerUtil::class
    factoryOf(::IosAppVersion) bind AppVersion::class
}