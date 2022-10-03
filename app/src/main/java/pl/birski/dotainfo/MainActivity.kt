package pl.birski.dotainfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.squareup.sqldelight.android.AndroidSqliteDriver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pl.birski.core.DataState
import pl.birski.core.Logger
import pl.birski.core.ProgressBarState
import pl.birski.core.UIComponent
import pl.birski.dotainfo.ui.theme.DotaInfoTheme
import pl.birski.herointeractors.UseCaseFactory
import pl.birski.uiherolist.HeroListState
import pl.birski.uiherolist.components.HeroList

class MainActivity : ComponentActivity() {

    private val state: MutableState<HeroListState> = mutableStateOf(HeroListState())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val getHeroesUseCase = UseCaseFactory.build(
            sqlDriver = AndroidSqliteDriver(
                schema = UseCaseFactory.schema,
                context = this,
                name = UseCaseFactory.dbName
            )
        ).getHeroesUseCase
        val logger = Logger("GetHeroesTest")
        getHeroesUseCase.execute().onEach { dataState ->
            when (dataState) {
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> {
                            logger.log((dataState.uiComponent as UIComponent.Dialog).description)
                        }
                        is UIComponent.None -> {
                            logger.log((dataState.uiComponent as UIComponent.None).message)
                        }
                    }
                }
                is DataState.Data -> {
                    state.value = state.value.copy(heroes = dataState.data ?: listOf())
                }
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
            }
        }.launchIn(CoroutineScope(IO))

        setContent {
            DotaInfoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HeroListCompose(
                        state = state.value
                    )
                }
            }
        }
    }
}

@Composable
fun HeroListCompose(state: HeroListState) {
    HeroList(state = state)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val state: HeroListState = HeroListState(
        progressBarState = ProgressBarState.Idle,
        heroes = listOf()
    )
    DotaInfoTheme {
        HeroList(state = state)
    }
}
