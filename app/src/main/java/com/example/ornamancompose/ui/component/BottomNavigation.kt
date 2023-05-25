package com.example.ornamancompose.ui.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.ornamancompose.R

data class NavItem(
    val name : String,
    val route : String,
    val icon : Painter
)


@Composable
fun BottomNav(
    modifier : Modifier = Modifier,
    selected : (route : String) -> Boolean,
    onItemClick : (route : String) -> Unit
){
    val bottomNavigationItems = listOf(
        NavItem(
            name = stringResource(R.string.home_bottom_nav_menu),
            route = stringResource(R.string.home_bottom_nav_menu),
            icon = painterResource(R.drawable.ic_home)
        ),
        NavItem(
            name = stringResource(R.string.scan_bottom_nav_menu),
            route = stringResource(R.string.scan_bottom_nav_menu),
            icon = painterResource(R.drawable.ic_scan)
        ),
        NavItem(
            name = stringResource(R.string.profile_bottom_nav_menu),
            route = stringResource(R.string.profile_bottom_nav_menu),
            icon = painterResource(R.drawable.ic_profile)
        ),
    )

    NavigationBar(
        modifier = modifier,
    ) {
        bottomNavigationItems.forEach{navItem ->
            NavigationBarItem(
                selected = selected(navItem.route),
                onClick = {
                   onItemClick(navItem.route)
                },
                label = {
                    Text(
                        text = navItem.name
                    )
                },
                icon = {
                    Icon(
                        painter = navItem.icon,
                        contentDescription = "${navItem.name} icon"
                    )
                }
            )
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun BottomNavPreview() {
//    OrnamanComposeTheme {
//        BottomNav(
//            modifier = Modifier.fillMaxWidth()
//                .wrapContentHeight()
//        )
//    }
//}