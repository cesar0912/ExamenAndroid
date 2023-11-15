package com.example.tiptime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reply.R
import com.example.reply.ui.theme.ReplyTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReplyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TipTimeLayoutPrincipal()
                }
            }
        }
    }
}

@Composable
fun TipTimeLayoutPrincipal(viewModel: SumadorViewModel = viewModel()){
    val uiState by viewModel.uiState.collectAsState()

        if (uiState.isShowingHomepage) {
            TipTimeLayout(
                uiState = uiState,
                onClick = {
                    viewModel.updateSumScreenStates(it)
                }
            )
        } else {
            Resul(uiState = uiState,
                onBackPressed = {
                    viewModel.resetHomeScreenStates()
                }
            )
        }

}
@Composable
fun Resul(uiState: ReplyUiState,
          onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxWidth()
            .fillMaxHeight()
){
        Box{
            TextField(
                value = "${uiState.sumador1} + ${uiState.sumador2} = ${uiState.sumador1+uiState.sumador2}",
                singleLine = true,
                modifier = modifier
                    .padding(40.dp, 20.dp, 0.dp, 0.dp)
                    .width(320.dp)
                    .height(80.dp),
                onValueChange = { },
                label = { Text("") }
            )
        }
        Box{
            TextField(
                value = "",
                modifier= modifier
                    .padding(40.dp, 20.dp, 0.dp, 0.dp)
                    .width(320.dp)
                    .height(240.dp),
                onValueChange = {}
            )
        }

        Box{
            Button(
                onClick = {onBackPressed},
                modifier.padding(150.dp,20.dp)
            ) {
                Text("Volver")
            }
        }

}


}



@Composable
fun TipTimeLayout(
    uiState: ReplyUiState,
    onClick: (ReplyUiState) -> Unit,
    ) {
var amountInput by remember { mutableStateOf("") }
var tipInput by remember { mutableStateOf("") }

Column(
    modifier = Modifier
        .padding(40.dp)
        .verticalScroll(rememberScrollState()),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
) {

    EditNumberField(
        label = R.string.sumador1,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        value = uiState.sumador1,
        onValueChanged = { amountInput = it },
        modifier = Modifier
            .padding(bottom = 32.dp)
            .fillMaxWidth(),
    )
    EditNumberField(
        label = R.string.sumador2,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        value = uiState.sumador2,
        onValueChanged = { tipInput = it },
        modifier = Modifier
            .padding(bottom = 32.dp)
            .fillMaxWidth(),
    )
    NumberButton (
        uiState = uiState,
        onSumPressed = {onClick}
    )

}
}


@Composable
fun NumberButton(
    uiState: ReplyUiState,
    onSumPressed: () -> Unit,
) {
    Button(onClick = {onSumPressed()}) {
        Text("+")
    }
}

@Composable
fun EditNumberField(
@StringRes label: Int,
keyboardOptions: KeyboardOptions,
value: String,
onValueChanged: (String) -> Unit,
modifier: Modifier = Modifier
) {
TextField(
    value = value,
    singleLine = true,
    modifier = modifier,
    onValueChange = onValueChanged,
    label = { Text(stringResource(label)) },
    keyboardOptions = keyboardOptions
)
}





@Preview(showBackground = true)
@Composable
fun TipTimeLayoutPreview() {
ReplyTheme {
    TipTimeLayoutPrincipal()
}
}