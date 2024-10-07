package com.example.myapplication.feature.pokemon.presentation.screen.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.core.ui.component.rippleClickable
import com.example.myapplication.feature.pokemon.presentation.model.Pokemon

@Composable
fun PokemonCard(
    pokemon: () -> Pokemon,
    onClick: () -> Unit,
) {
    val color = MaterialTheme.colorScheme.surfaceContainerHighest
    val poke = remember { pokemon() }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(16.dp))
            .drawBehind { drawRect(color) }
            .rippleClickable(onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = poke.url),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        )
        Text(
            text = poke.name,
            modifier = Modifier.padding(8.dp),
        )
    }
}