package com.example.veterinaria.ui.screens.login


import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.veterinaria.ui.MainActivity
import com.example.veterinaria.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val token = "AIzaSyBKvhOADRrKhhyH62nUnZ0yM5nqIV1xmwc"
    val context = LocalContext.current

    val showLoginForm = rememberSaveable() {
        mutableStateOf(true)
    }
    val currentContext = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts
            .StartActivityForResult()
    ){
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)

        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken,null)
            viewModel.signInWithGoogleCredential(credential){
                /*
                currentContext.startActivity(
                    Intent(
                        currentContext,
                        MainActivity::class.java
                    )
                )*/

                Log.d("Credencial","Accediendo xd Screen ${credential}")
            }
        }catch (ex:Exception){
            ex.printStackTrace()
            Log.d("Credencial","Exception en Screen ${ex.message}")

        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (showLoginForm.value) {
                Text(text = "Iniciar Sesión")
                UserForm(
                    isCreateAccount = false,
                    showPhoto = false
                ) { email, password ,photo->
                    //Log.d("Mascota", "Logueando con $email , $password")
                    viewModel.signInWithEmailAndPassword(email, password) {
                        //navController.navigate(Screen.HomeScreen.ruta)
                        currentContext.startActivity(
                            Intent(
                                currentContext,
                                MainActivity::class.java
                            )
                        )
                    }

                }
            } else {
                Text(text = "Crea una cuenta")
                UserForm(
                    isCreateAccount = true,
                    showPhoto = true
                ) { email, password,photo ->
                    Log.d("Mascota Feliz", "creando cuenta con $email , $password")
                    viewModel.createUserWithEmailAndPassword(email, password,photo) {
                        //navController.navigate(MascotaScreens.MascotaHomeScreen.name)
                        currentContext.startActivity(
                            Intent(
                                currentContext,
                                MainActivity::class.java
                            )
                        )
                    }

                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val text1 =
                    if (showLoginForm.value) "No tienes Cuenta"
                    else "Ya tienes cuenta"
                val text2 =
                    if (showLoginForm.value) "Registrate"
                    else "Inicia Sesion"
                Text(text = text1)
                Text(text = text2,
                    modifier = Modifier
                        .clickable { showLoginForm.value = !showLoginForm.value }
                        .padding(start = 5.dp),
                    color = MaterialTheme.colors.secondaryVariant
                )
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    val opciones = GoogleSignInOptions.Builder(
                        GoogleSignInOptions.DEFAULT_SIGN_IN
                    )
                        .requestIdToken(token)
                        .requestEmail()
                        .build()

                    val googeSignInClient = GoogleSignIn.getClient(context, opciones)
                    launcher.launch(googeSignInClient.signInIntent)
                },
                verticalAlignment = Alignment.CenterVertically
                , horizontalArrangement = Arrangement.Center
            ){
                Text(text = "Login con Google")
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserForm(
    isCreateAccount: Boolean = false,
    //onDone: (String, String) -> Unit = { email, pwd -> }
    showPhoto: Boolean = false,
    onDone: (String, String, String) -> Unit = { email, pwd, photo -> }
) {

    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisible = rememberSaveable() { mutableStateOf(false) }
    val valido = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() &&
                password.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val emailState = rememberSaveable { mutableStateOf("") }
    val photoState = rememberSaveable { mutableStateOf("") }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        EmailInput(
            emailState = email
        )
        PasswordInput(
            passwordState = password,
            labelId = "Password",
            passwordVisible = passwordVisible
        )
        //PhotoInput(photoState)
        if (showPhoto) {
            PhotoInput(
                valueState = photoState,
                labelId = "Photo"
            )
        }
        SubmitButon(
            textId = if (isCreateAccount) "Crear cuenta" else "login",
            inputValido = valido
        ) {
            //onDone(email.value.trim(), password.value.trim())
            //onDone(email.value.trim(), password.value.trim())
            onDone(email.value.trim(), password.value.trim(), photoState.value.trim())
            keyboardController?.hide()
        }

    }
}

@Composable
fun PhotoInput(valueState: MutableState<String>, labelId: String= "Photo") {

    InputField(
        valueState = valueState,
        labelId = labelId,
        keyboartType = KeyboardType.Text
    )
}

@Composable
fun SubmitButon(
    textId: String,
    inputValido: Boolean,
    onClic: () -> Unit
) {
    Button(
        onClick = onClic,
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape,
        enabled = inputValido

    ) {
        Text(
            text = textId,
            modifier = Modifier
                .padding(5.dp)
        )
    }
}

@Composable
fun PasswordInput(
    passwordState: MutableState<String>,
    labelId: String,
    passwordVisible: MutableState<Boolean>
) {
    val visualTransformation = if (passwordVisible.value)
        VisualTransformation.None
    else PasswordVisualTransformation()

    OutlinedTextField(
        value = passwordState.value,
        onValueChange = { passwordState.value = it },
        label = { Text(text = labelId) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        visualTransformation = visualTransformation,
        trailingIcon = {
            if (passwordState.value.isNotBlank()) {
                PasswordVisibleIcon(passwordVisible)
            } else null
        }
    )
}

@Composable
fun PasswordVisibleIcon(passwordVisible: MutableState<Boolean>) {
    val imagen =
        if (passwordVisible.value)
            Icons.Default.VisibilityOff
        else
            Icons.Default.Visibility
    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }
    ) {
        Icon(
            imageVector = imagen,
            contentDescription = ""
        )
    }
}

@Composable
fun EmailInput(
    emailState: MutableState<String>,
    labelId: String = "Email"
) {
    InputField(
        valueState = emailState,
        labelId = labelId,
        keyboartType = KeyboardType.Email
    )
}

@Composable
fun InputField(
    valueState: MutableState<String>,
    isSingleLine: Boolean = true,
    labelId: String,
    keyboartType: KeyboardType
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text(text = labelId) },
        singleLine = isSingleLine,
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboartType)

    )
}

