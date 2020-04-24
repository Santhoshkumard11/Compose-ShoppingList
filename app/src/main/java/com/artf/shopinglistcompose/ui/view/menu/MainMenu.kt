package com.artf.shopinglistcompose.ui.view.menu

import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.compose.state
import androidx.ui.core.*
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.*
import androidx.ui.material.IconButton
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.material.TextButton
import androidx.ui.res.stringResource
import androidx.ui.res.vectorResource
import androidx.ui.unit.IntPx
import androidx.ui.unit.IntPxPosition
import androidx.ui.unit.dp
import com.artf.shopinglistcompose.R
import com.artf.shopinglistcompose.ui.view.SharedViewModel
import com.artf.shopinglistcompose.ui.view.layout.Screen
import com.artf.shopinglistcompose.ui.view.layout.ScreenAmbient
import com.artf.shopinglistcompose.util.dpToPx
import org.koin.androidx.viewmodel.ext.android.viewModel

@Composable
fun MainMenu() {
    val sharedViewModel = LifecycleOwnerAmbient.current.viewModel<SharedViewModel>()
    val showMenu = state { false }
    val backStack = ScreenAmbient.current

    IconButton(onClick = { showMenu.value = true }) {
        Icon(vectorResource(R.drawable.ic_baseline_more_vert_black_24))
    }
    if (showMenu.value.not()) return
    DropdownPopup(
        offset = IntPxPosition(IntPx(0.dpToPx()), IntPx((-48 - 16).dpToPx())),
        popupProperties = PopupProperties(true) { showMenu.value = false }
    ) {
        Row(Modifier.padding(16.dp, 16.dp, 4.dp, 16.dp)) {
            Surface(
                Modifier.width((ConfigurationAmbient.current.screenWidthDp * 0.6).dp),
                shape = RoundedCornerShape(4.dp),
                elevation = 8.dp
            ) {
                Column {
                    TextButton(onClick = {
                        backStack.push(Screen.ShoppingListCurrent)
                        showMenu.value = false
                    }) {
                        Text(
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            text = stringResource(id = R.string.current_shopping_list),
                            color = MaterialTheme.colors.onSurface,
                            maxLines = 1
                        )
                    }
                    TextButton(onClick = {
                        backStack.push(Screen.ShoppingListArchived)
                        showMenu.value = false
                    }) {
                        Text(
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            text = stringResource(id = R.string.archived_shopping_list),
                            color = MaterialTheme.colors.onSurface,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}