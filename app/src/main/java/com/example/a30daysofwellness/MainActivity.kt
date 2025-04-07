package com.example.a30daysofwellness

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import com.example.a30daysofwellness.data.DataSource
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.animation.core.spring
import androidx.compose.ui.tooling.preview.Preview
import com.example.a30daysofwellness.model.Task
import com.example.compose.WellnessTheme
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.dimensionResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WellnessTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WellnessApp()
                }
            }
        }
    }
}

@Composable
fun WellnessApp() {
    Column(modifier = Modifier.padding(16.dp)) {
        Scaffold(
            topBar = {
                WellnessTopAppBar()
            }
        ) { innerPadding ->
            // objeto do tipo PaddingValues gerado automaticamente, contém as margens necessárias para acomodar elementos como topBar
            // innerPadding é passado para contentPadding, garantindo que os itens da lista não fiquem colados em Scaffold
            LazyColumn(
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(DataSource.tasks) { task ->
                    WellnessCard(
                        task =task,
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WellnessTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar( // usada pra criar barra do topo que tem conteúdo alinhado ao centro
        title = {
            Row(verticalAlignment = Alignment.CenterVertically){
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.displayLarge
                )
            }
        },
        modifier = modifier
    )
}

@Composable
fun WellnessCard(task: Task, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp), // adiciona sombra /
        // efeito de elevação do card
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = spring( // tipo de animação -> como uma mola
                    dampingRatio = Spring.DampingRatioNoBouncy, // sem amortecimento
                    stiffness = Spring.StiffnessMedium // rigidez da mola
                )
            )
    ) {
        Column {
            Text(
                text = stringResource(id = task.name),
                style = MaterialTheme.typography.displayMedium,
                color = Color.Gray,
                modifier = Modifier
                    .padding(8.dp)
            )
            Text(
                text = stringResource(id = task.description),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Image(
                painter = painterResource(id = task.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            WellnessItemButton(
                expanded = expanded,
                onClick =  { expanded = !expanded } // diferente do valor original, ou seja, true
            )
            if (expanded) {
                Text(
                    text = stringResource(id = task.longDescription),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun WellnessItemButton(
    expanded: Boolean, // vai receber var expanded
    onClick: () -> Unit, // lambda pra quando for clicado, torna o botão reutilizável em
    // diferentes cenários. Não retorna Unit.
    modifier: Modifier = Modifier
){
    IconButton(
        onClick = onClick, // quando o botão for clicado, executa onClick da função
        modifier = modifier
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = stringResource(R.string.expand_button_content_description),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WellnessPreview(){
    WellnessApp()
}