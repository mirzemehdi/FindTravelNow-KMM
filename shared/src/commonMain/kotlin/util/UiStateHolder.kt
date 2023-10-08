package util

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.CoroutineScope
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
    get() = coroutineScope

@Composable
public inline fun <reified T : UiStateHolder> Screen.getUiStateHolder(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null,
): T {
    val koin = getKoin()
    return rememberScreenModel(tag = qualifier?.value) { koin.get(qualifier, parameters) }
}
