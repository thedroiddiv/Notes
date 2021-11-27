package com.dxn.notes.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.dxn.notes.R
import com.dxn.notes.ui.components.BodyText
import com.dxn.notes.ui.components.TitleText
import com.dxn.notes.ui.theme.NotesTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SignInActivity : ComponentActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var signInIntentLauncher: ActivityResultLauncher<Intent>

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            NotesTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    )
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp),
                        painter = painterResource(id = R.drawable.ic_illustration_plan),
                        contentDescription = "",
                        alpha = 0.8f,
                        contentScale = ContentScale.FillWidth
                    )
                    Column {
                        TitleText(text = stringResource(R.string.app_name))
                        BodyText(text = stringResource(R.string.notes_description))
                    }

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { signIn() },
                        shape = CircleShape,
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 8.dp),
                            text = "Continue with google",
                            style = MaterialTheme.typography.button
                        )
                    }
                }

            }
        }
        googleSignInClient = GoogleSignIn.getClient(
            this, GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(resources.getString(R.string.web_client_id))
                .requestEmail()
                .build()
        )
        signInIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                Log.d(TAG, "onCreate: ${it.resultCode}")
                if (it.resultCode == Activity.RESULT_OK) {
                    try {
                        val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                            .getResult(ApiException::class.java)
                        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                navigateToMainActivity()
                            } else {
                                throw Throwable(task.exception)
                            }
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) navigateToMainActivity()
    }

    private fun signIn() = signInIntentLauncher.launch(googleSignInClient.signInIntent)

    private fun navigateToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        googleSignInClient.revokeAccess()
        finish()
    }

    companion object {
        private const val TAG = "MAIN_ACTIVITY"
    }
}
