package com.artf.shopinglistcompose.ui.view.layout.currentList

import androidx.compose.*
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.shape.corner.CornerSize
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.material.*
import androidx.ui.res.stringResource
import androidx.ui.res.vectorResource
import androidx.ui.unit.dp
import com.artf.data.database.model.ShoppingList
import com.artf.shopinglistcompose.R
import com.artf.shopinglistcompose.ui.data.ScreenBackStackAmbient
import com.artf.shopinglistcompose.ui.data.SharedViewModelAmbient
import com.artf.shopinglistcompose.ui.view.layout.EmptyScreen
import com.artf.shopinglistcompose.util.observer

@Composable
fun ProductListCurrentScreen(
    shoppingList: ShoppingList,
    scaffoldState: ScaffoldState = remember { ScaffoldState() }
) {
    val backStack = ScreenBackStackAmbient.current
    val showDialog = state { false }
    Scaffold(
        scaffoldState = scaffoldState,
        topAppBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                backgroundColor = MaterialTheme.colors.primary,
                navigationIcon = {
                    IconButton(onClick = { backStack.pop() }) {
                        Icon(vectorResource(R.drawable.ic_baseline_arrow_back_24))
                    }
                }
            )
        },
        bodyContent = {
            ScreenBody(shoppingList)
            CreateProductDialog(showDialog, shoppingList)
        },
        floatingActionButton = { Fab(showDialog) }
    )
}

@Composable
private fun ScreenBody(shoppingList: ShoppingList) {
    val sharedViewModelAmbient = SharedViewModelAmbient.current
    sharedViewModelAmbient.onShoppingListClick(shoppingList)
    val productList = observer(sharedViewModelAmbient.productListUi)
    if (productList == null || productList.isEmpty()) {
        EmptyScreen(
            R.string.empty_view_product_list_title,
            R.string.empty_view_product_list_subtitle_text
        )
        return
    }
    VerticalScroller {
        Column(Modifier.fillMaxWidth().padding(8.dp, 8.dp, 8.dp, 96.dp)) {
            productList.forEach { post -> ProductCurrentItem(sharedViewModelAmbient, post) }
        }
    }
}

@Composable
private fun Fab(showDialog: MutableState<Boolean>) {
    FloatingActionButton(
        onClick = { showDialog.value = true },
        modifier = Modifier,
        shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
        backgroundColor = MaterialTheme.colors.secondary,
        contentColor = contentColorFor(MaterialTheme.colors.onSecondary),
        elevation = 6.dp,
        icon = { Icon(vectorResource(R.drawable.ic_add_black_24dp)) }
    )
}