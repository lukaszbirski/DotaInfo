package pl.birski.dotainfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import pl.birski.dotainfo.ui.theme.DotaInfoTheme
import pl.birski.uiherolist.components.HeroList
import pl.birski.uiherolist.ui.HeroesListViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imageLoader = ImageLoader.Builder(applicationContext)
            .error(R.drawable.error_image)
            .placeholder(R.drawable.white_background)
            .availableMemoryPercentage(0.25)
            .crossfade(true)
            .build()

        setContent {
            DotaInfoTheme {
                val viewModel: HeroesListViewModel = hiltViewModel()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HeroList(
                        state = viewModel.state.value,
                        imageLoader = imageLoader
                    )
                }
            }
        }
    }
}
