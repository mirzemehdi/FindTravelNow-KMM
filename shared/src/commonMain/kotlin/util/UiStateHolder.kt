package util

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.ScreenModelStore
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.plus
import org.koin.compose.getKoin
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier


//a.k.a ViewModel
abstract class UiStateHolder : ScreenModel {

    protected open fun onCleared() {}

    override fun onDispose() {
        super.onDispose()
        onCleared()
    }

}

val UiStateHolder.uiStateHolderScope: CoroutineScope
    get() = ScreenModelStore.getOrPutDependency(
        screenModel = this,
        name = "ScreenModelCoroutineScope",
        factory = { key -> CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate) + CoroutineName(key) },
        onDispose = { scope -> scope.cancel() }
    )

@Composable
inline fun <reified T : UiStateHolder> Screen.getUiStateHolder(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null,
): T {
    val koin = getKoin()
    return rememberScreenModel(tag = qualifier?.value) { koin.get(qualifier, parameters) }
}
