package com.abusiness.contactosbd
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class Registrar : AppCompatActivity() {
//Variables para firebase
private lateinit var txtName: EditText
private lateinit var txtLastName: EditText
private lateinit var txtEmail: EditText
private lateinit var txtPassword: EditText
private lateinit var progressBar: ProgressBar
private lateinit var dbReference: DatabaseReference
private lateinit var database: FirebaseDatabase
private lateinit var auth: FirebaseAuth
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_registrar)
    txtName=findViewById(R.id.txtName)
    txtLastName=findViewById(R.id.txtLastName)
    txtEmail=findViewById(R.id.txtEmail)
    txtPassword=findViewById(R.id.txtPassword)
    progressBar = findViewById(R.id.progressBar)
    database = FirebaseDatabase.getInstance()
    auth = FirebaseAuth.getInstance()
    dbReference = database.reference.child("User")
}
fun registrar(view: View){
    createNewAccount()
}
private fun createNewAccount(){
    val name:String = txtName.text.toString()
    val lastName:String = txtLastName.text.toString()
    val email:String = txtEmail.text.toString()
    val password:String = txtPassword.text.toString()
    if (!TextUtils.isEmpty(name) &&
        !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(email)
        && !TextUtils.isEmpty(password)){
        progressBar.visibility = View.VISIBLE
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){
                task->  // todo aqui se crea una tarea
            if(task.isComplete){  // todo al crear la tarea tendra metodos
                val user:FirebaseUser?=auth.currentUser
                verifyEmail(user)  // todo es una funcion que hemos creado mas abajo
                val userBD = dbReference.child(user!!.uid) // todo es una instancia que se crea en farebase
                userBD.child("Name").setValue(name)
                userBD.child("Apellido").setValue(lastName)
                action()  // todo es otra funcion mas abajo
            }
        }
    }
}
private fun action(){
    startActivity(Intent(this,Login::class.java))  // todo la lanza desde la actividad de login
}
private fun verifyEmail(user:FirebaseUser?){
    user?.sendEmailVerification()
        ?.addOnCompleteListener(this){
                task->
            if(task.isComplete) {
                Toast.makeText(this, "Email enviado",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Error al enviar el email",Toast.LENGTH_SHORT).show()
            }
        }
}
}