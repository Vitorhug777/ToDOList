package com.example.todolist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.todolist.viewmodel.AuthViewModel
import com.example.todolist.ui.pages.LoginPage
import com.example.todolist.ui.pages.SignupPage
import com.example.todolist.ui.feature.AddEdit.AddEditScreen
import com.example.todolist.ui.feature.ListScreen
import kotlinx.serialization.Serializable

@Serializable
object LoginRoute

@Serializable
object SignupRoute

@Serializable
object ListRoute

@Serializable
data class AddEditRoute(val id: Long? = null)

@Composable
fun TodoNavHost(authViewModel: AuthViewModel) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = LoginRoute) {

        composable<LoginRoute> {
            LoginPage(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable<SignupRoute> {
            SignupPage(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable<ListRoute> {
            ListScreen(
                authViewModel = authViewModel, // Corrigido para a variável minúscula
                navController = navController,   // Adicionado para permitir o logout
                navigateToAddEditScreen = { id ->
                    navController.navigate(AddEditRoute(id = id))
                }
            )
        }

        composable<AddEditRoute> { backStackEntry ->
            // Se precisar do ID na tela AddEditScreen:
            // val id = backStackEntry.toRoute<AddEditRoute>().id
            AddEditScreen(
                navigateBack = navController::popBackStack
            )
        }
    }
}