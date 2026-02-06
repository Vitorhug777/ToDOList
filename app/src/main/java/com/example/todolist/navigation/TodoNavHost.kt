package com.example.todolist.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel // Importante para o viewModel()
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.todolist.viewmodel.AuthViewModel
import com.example.todolist.viewmodel.ListViewModel // Importe o seu novo ViewModel
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

    // Instanciando o ListViewModel para ser usado na ListScreen
    val listViewModel: ListViewModel = viewModel()

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
                authViewModel = authViewModel,
                listViewModel = listViewModel, // ADICIONADO: Agora o parâmetro está preenchido
                navController = navController,
                navigateToAddEditScreen = { id ->
                    navController.navigate(AddEditRoute(id = id))
                }
            )
        }

        composable<AddEditRoute> { backStackEntry ->
            // Se o seu AddEditScreen precisar do ID para editar, você pega assim:
            val routeData = backStackEntry.toRoute<AddEditRoute>()

            AddEditScreen(
                // Passe o id para a tela se necessário: taskId = routeData.id,
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}