package com.dxn.notes.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.navArgument
import com.dxn.notes.common.models.Note
import com.dxn.notes.ui.navigation.Screens
import com.dxn.notes.ui.screens.edit.EditScreen
import com.dxn.notes.ui.screens.home.HomeScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun App(
    user: FirebaseUser,
    signOut: () -> Unit
) {
    val viewModel: AppViewModel = hiltViewModel()
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(navController = navController, startDestination = Screens.Home.route) {
        composable(Screens.Home.route) {
            HomeScreen(viewModel, navController, user, signOut)
        }
        composable(
            Screens.Edit.route + "?note={data}",
            arguments = listOf(navArgument("data") {
                nullable = true
            })
        ) {
            val data = it.arguments?.getString("data")
            val note = Gson().fromJson(data, Note::class.java)
            EditScreen(navController, note)
        }
    }
}