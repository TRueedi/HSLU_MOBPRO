package ch.hslu.mobpro.ui.bands

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ch.hslu.mobpro.business.bands.BandInfo
import coil3.compose.AsyncImage

@Composable
fun BandInfoScreen(
    bandCode: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val bandsViewModel: BandsViewModel = viewModel()
        bandsViewModel.requestBandInfoFromServer(bandCode)
        bandsViewModel.currentBand.collectAsState(null).value?.let { currentBand ->
            CurrentBand(currentBand)
        }
    }
}


@Composable
fun CurrentBand(
    currentBand: BandInfo,
) {
    Text(
        modifier = Modifier.padding(8.dp),
        text = currentBand.name,
        style = MaterialTheme.typography.displaySmall,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        modifier = Modifier.padding(8.dp),
        text = "${currentBand.homeCountry}, ${currentBand.foundingYear}",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.primary
    )
    AsyncImage(
        modifier = Modifier.padding(8.dp),
        model = currentBand.bestOfCdCoverImageUrl,
        contentDescription = null
    )
}



@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
fun BandInfoScreenPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CurrentBand(
            currentBand = BandInfo(
                name = "The Beatles",
                homeCountry = "UK",
                foundingYear = 1960,
                bestOfCdCoverImageUrl = "https://upload.wikimedia.org/wikipedia/en/1/1c/The_Beatles_-_1.jpg"
            )
        )
    }
}