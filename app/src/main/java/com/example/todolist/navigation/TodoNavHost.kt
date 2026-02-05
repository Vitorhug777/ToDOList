package com.example.todolist.navigation

import AuthViewModel
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.todolist.viewmodel.AuthViewModel // Certifique-se de criar este pacote
import com.example.todolist.ui.pages.LoginPage      // Certifique-se de criar este pacote
import com.example.todolist.ui.pages.SignupPage     // Certifique-se de criar este pacote
import com.example.todolist.ui.feature.AddEdit.AddEditScreen
import com.example.todolist.ui.feature.ListScreen
import kotlinx.serialization.Serializable

// 1. Definição das Rotas (Type-Safe)
@Serializable
object LoginRoute

@Serializable
object SignupRoute

@Serializable
object ListRoute

@Serializable
data class AddEditRoute(val id: Long? = null)

@Composable
fun TodoNavHost(authViewModel: AuthViewModel) { // 2. Recebe o ViewModel por parâmetro

    val navController = rememberNavController()

    // 3. O destino inicial agora é o Login conforme o vídeo
    NavHost(navController = navController, startDestination = LoginRoute) {

        // Tela de Login
        composable<LoginRoute> {
            LoginPage(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        // Tela de Cadastro
        composable<SignupRoute> {
            SignupPage(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        // Tela da Lista (Sua Home)
        composable<ListRoute> {
            ListScreen(
                authViewModel = authViewModel, // Para poder fazer Sign Out
                navigateToAddEditScreen = { id ->
                    navController.navigate(AddEditRoute(id = id))
                }
            )
        }

        // Tela de Adicionar/Editar
        composable<AddEditRoute> { backStackEntry ->
            val addEditRoute = backStackEntry.toRoute<AddEditRoute>()
            AddEditScreen(
                navigateBack = navController::popBackStack
            )
        }
    }
}