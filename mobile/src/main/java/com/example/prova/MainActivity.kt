package com.example.prova

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import android.content.Intent
import com.google.android.gms.common.api.ApiException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Listener bottone login da google
        signInButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                signInButtonClicked()
            }
        })
    }

    // Creo oggetto per la richiesta delle informazioni dell'account google
    fun signInButtonClicked() {

        var googleSignInClient: GoogleSignInClient
        var googleSignInOptions: GoogleSignInOptions

        // Parametri che richiedo all'api
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        // Invio la richiesta ai server google, chiama in automatico onActivityResult
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, Companion.RC_SIGN_IN)
        println("Connessione in corso")

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    // Leggo le informazioni contenute nella richiesta api
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            println("Loggato correttamente")

            println("Nome: " + account.displayName)
            println("Email: " + account.email)
            println("Id: " + account.id)
            println("Id Token: " + account.idToken)
            println("Nome familia: " + account.familyName)

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
        }

    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }
}
