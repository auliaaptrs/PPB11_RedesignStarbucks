package com.example.appauthentication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Data class untuk menyimpan informasi pengguna
data class UserData(val password: String, val displayName: String)

// Database dummy sekarang menggunakan email sebagai Key dan UserData sebagai Value
val dummyUserDB = mutableStateMapOf<String, UserData>()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Color(0xFF006241),
                    secondary = Color(0xFFD4A05D),
                    background = Color(0xFFF7F7F7),
                    onBackground = Color.Black
                )
            ) {
                AppWithIntro()
            }
        }
    }
}

@Composable
fun AppWithIntro() {
    var showIntro by remember { mutableStateOf(true) }

    if (showIntro) {
        IntroScreen {
            showIntro = false
        }
    } else {
        SimpleAuthApp()
    }
}

@Composable
fun CustomLogo(size: Int = 120) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = Color(0xFF006241),
        modifier = Modifier
            .size(size.dp)
            .shadow(12.dp, RoundedCornerShape(24.dp)),
        tonalElevation = 8.dp
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Default.Coffee,
                contentDescription = "Starbucks",
                tint = Color.White,
                modifier = Modifier.size((size * 0.5).dp)
            )
        }
    }
}

@Composable
fun IntroScreen(onContinue: () -> Unit) {
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF006241), Color(0xFF00754A), Color(0xFFF7F7F7))
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            CustomLogo(150)
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Starbucks",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Secangkir Pengalaman Digital",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Masuk atau daftar untuk\nmenikmati fitur eksklusif dari kami",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )
            Spacer(modifier = Modifier.height(48.dp))
            Button(
                onClick = onContinue,
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(56.dp)
                    .shadow(8.dp, RoundedCornerShape(25.dp))
            ) {
                Text(
                    "Mulai Sekarang",
                    color = Color(0xFF006241),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = Color(0xFF006241)
                )
            }
        }
    }
}


@Composable
fun SimpleAuthApp() {
    var isLoginScreen by remember { mutableStateOf(true) }
    var displayName by remember { mutableStateOf("") } // Mengganti username menjadi displayName
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var inputOTP by remember { mutableStateOf("") }
    var generatedOTP by remember { mutableStateOf("") }
    var isOTPSent by remember { mutableStateOf(false) }
    var otpTimer by remember { mutableStateOf(0) }

    var message by remember { mutableStateOf("") }
    var isLoggedIn by remember { mutableStateOf(false) }
    var loggedInDisplayName by remember { mutableStateOf("") } // State untuk nama pengguna yang login
    var passwordVisible by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val gradient = Brush.verticalGradient(
        listOf(Color(0xFF006241), Color(0xFF00754A), Color(0xFFF7F7F7))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        if (isLoggedIn) {
            WelcomeScreen(loggedInDisplayName) { // Tampilkan displayName setelah login
                isLoggedIn = false
                email = ""
                password = ""
                loggedInDisplayName = ""
            }
        } else {
            AuthScreen(
                isLoginScreen,
                displayName,
                email,
                password,
                confirmPassword,
                inputOTP,
                isOTPSent,
                otpTimer,
                passwordVisible,
                message,
                onDisplayNameChange = { displayName = it },
                onEmailChange = { email = it },
                onPasswordChange = { password = it },
                onConfirmPasswordChange = { confirmPassword = it },
                onOTPChange = { inputOTP = it },
                onPasswordVisibilityToggle = { passwordVisible = !passwordVisible },

                onSendOTP = {
                    generatedOTP = (100000..999999).random().toString()
                    isOTPSent = true
                    otpTimer = 3
                    scope.launch {
                        while (otpTimer > 0) {
                            delay(1000)
                            otpTimer--
                        }
                    }
                },

                onAuthAction = {
                    // Validasi input berdasarkan layar
                    if (isLoginScreen) {
                        if (email.isBlank() || password.isBlank()) {
                            message = "Email dan Kata Sandi tidak boleh kosong."
                            return@AuthScreen
                        }
                    } else {
                        if (displayName.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank() || inputOTP.isBlank()) {
                            message = "Mohon lengkapi semua field yang diperlukan."
                            return@AuthScreen
                        }
                    }

                    // Logika untuk Login
                    if (isLoginScreen) {
                        if (!dummyUserDB.containsKey(email)) {
                            message = "Akun dengan email ini tidak ditemukan."
                        } else if (dummyUserDB[email]?.password != password) {
                            message = "Kata Sandi yang Anda masukkan salah."
                        } else {
                            isLoggedIn = true
                            loggedInDisplayName = dummyUserDB[email]?.displayName ?: ""
                            message = ""
                        }
                        // Logika untuk Registrasi
                    } else {
                        if (dummyUserDB.containsKey(email)) {
                            message = "Email ini sudah terdaftar sebelumnya."
                        } else if (password != confirmPassword) {
                            message = "Konfirmasi kata sandi tidak cocok."
                        } else if (inputOTP != generatedOTP) {
                            message = "Kode OTP yang dimasukkan tidak valid."
                        } else {
                            // Simpan data baru dengan email sebagai key
                            dummyUserDB[email] = UserData(password = password, displayName = displayName)
                            message = "Selamat! Pendaftaran berhasil. Silakan masuk."
                            isLoginScreen = true // Langsung arahkan ke layar login
                            // Reset field
                            displayName = ""
                            email = ""
                            password = ""
                            confirmPassword = ""
                            generatedOTP = ""
                            inputOTP = ""
                            isOTPSent = false
                        }
                    }
                },

                onToggleScreen = {
                    isLoginScreen = !isLoginScreen
                    message = ""
                    displayName = ""
                    email = ""
                    password = ""
                    confirmPassword = ""
                    inputOTP = ""
                    generatedOTP = ""
                    isOTPSent = false
                    otpTimer = 0
                }
            )
        }
    }
}


@Composable
fun WelcomeScreen(displayName: String, onLogout: () -> Unit) { // Menerima displayName
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF006241), Color(0xFF00754A), Color(0xFFD4A05D))
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = CircleShape,
                color = Color.White,
                modifier = Modifier
                    .size(140.dp)
                    .shadow(16.dp, CircleShape),
                tonalElevation = 8.dp
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color(0xFF006241),
                    modifier = Modifier
                        .size(80.dp)
                        .padding(30.dp)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Halo, Selamat Menikmati!",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = displayName, // Menampilkan displayName
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Kami senang Anda kembali!\nSemoga hari Anda semanis secangkir kopi ☕",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )
            Spacer(modifier = Modifier.height(48.dp))
            Button(
                onClick = onLogout,
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(56.dp)
                    .shadow(8.dp, RoundedCornerShape(25.dp))
            ) {
                Icon(
                    Icons.Default.ExitToApp,
                    contentDescription = null,
                    tint = Color(0xFF006241)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "Keluar",
                    color = Color(0xFF006241),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    isLoginScreen: Boolean,
    displayName: String,
    email: String,
    password: String,
    confirmPassword: String,
    inputOTP: String,
    isOTPSent: Boolean,
    otpTimer: Int,
    passwordVisible: Boolean,
    message: String,
    onDisplayNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onOTPChange: (String) -> Unit,
    onPasswordVisibilityToggle: () -> Unit,
    onSendOTP: () -> Unit,
    onAuthAction: () -> Unit,
    onToggleScreen: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomLogo(100)
        Spacer(modifier = Modifier.height(32.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(12.dp, shape = RoundedCornerShape(28.dp)),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(28.dp),
        ) {
            Column(modifier = Modifier.padding(32.dp)) {
                Text(
                    text = if (isLoginScreen) "Masuk ke Akun Anda" else "Buat Akun Baru",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF004D33),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (isLoginScreen) "Masukkan email dan kata sandi Anda" else "Lengkapi informasi untuk membuat akun",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))

                // Field Nama Tampilan hanya muncul saat registrasi
                if (!isLoginScreen) {
                    OutlinedTextField(
                        value = displayName,
                        onValueChange = onDisplayNameChange,
                        label = { Text("Nama Tampilan", fontWeight = FontWeight.Medium) },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = textFieldColors(),
                        shape = RoundedCornerShape(16.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Field Email muncul di kedua layar (Login dan Registrasi)
                OutlinedTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    label = { Text("Alamat Email", fontWeight = FontWeight.Medium) },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    colors = textFieldColors(),
                    shape = RoundedCornerShape(16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    label = { Text("Kata Sandi", fontWeight = FontWeight.Medium) },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = onPasswordVisibilityToggle) {
                            Icon(
                                if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = null
                            )
                        }
                    },
                    colors = textFieldColors(),
                    shape = RoundedCornerShape(16.dp)
                )

                if (!isLoginScreen) {
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = onConfirmPasswordChange,
                        label = { Text("Konfirmasi Kata Sandi", fontWeight = FontWeight.Medium) },
                        leadingIcon = { Icon(Icons.Default.LockReset, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation(),
                        colors = textFieldColors(),
                        shape = RoundedCornerShape(16.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = onSendOTP,
                        enabled = otpTimer == 0,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4A05D)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(Icons.Default.Send, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            if (otpTimer > 0) "Kirim Ulang OTP ($otpTimer s)" else "Kirim Kode OTP",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = inputOTP,
                        onValueChange = onOTPChange,
                        label = { Text("Kode Verifikasi OTP", fontWeight = FontWeight.Medium) },
                        leadingIcon = { Icon(Icons.Default.Key, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = textFieldColors(),
                        shape = RoundedCornerShape(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))
                Button(
                    onClick = onAuthAction,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006241))
                ) {
                    Text(
                        if (isLoginScreen) "Masuk Sekarang" else "Daftar Sekarang",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                TextButton(
                    onClick = onToggleScreen,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        if (isLoginScreen) "Belum memiliki akun? Daftar di sini" else "Sudah memiliki akun? Masuk di sini",
                        color = Color(0xFF006241),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                if (message.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = if (message.contains("berhasil"))
                                Color(0xFF4CAF50).copy(alpha = 0.1f)
                            else
                                Color(0xFFF44336).copy(alpha = 0.1f)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = message,
                            color = if (message.contains("berhasil")) Color(0xFF2E7D32) else Color(0xFFD32F2F),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun textFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Color.Black,
    unfocusedTextColor = Color.DarkGray,
    focusedBorderColor = Color(0xFF006241),
    unfocusedBorderColor = Color.LightGray,
    focusedLabelColor = Color(0xFF006241)
)
