package com.abusiness.contactosbd

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText:EditText
    private lateinit var salvar:Button
    private lateinit var consltaButton:Button
    private lateinit var bosquejo:TextView
    private lateinit var db:DatabaseHander
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nameEditText = findViewById(R.id.editTextText)
        emailEditText = findViewById(R.id.editTextText2)
        consltaButton = findViewById(R.id.button2)
        bosquejo = findViewById(R.id.textView)
        salvar = findViewById(R.id.button)

        //todo llama a la clase para acceder a todos los metodos de la clase
        db= DatabaseHander(this)  // todo instancia la clase DatabaseHander

        //todo ponemos el escuchador del boton
        salvar.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            if  (name.isNotEmpty() && email.isNotEmpty()){
                val id=db.addContact(name,email) // todo devuelve una referencia exitosa o no
                if (id == -1L){
                    nameEditText.text.clear()
                    emailEditText.text.clear()
                }
                    else{
                        // todo aqui iria un toast avisando de error
                    Toast.makeText(applicationContext, "guardado", Toast.LENGTH_SHORT).show()
                    }
            }else{
                // todo ha habido un problema en la base de datos
               Toast.makeText(applicationContext, "te falta algun campo", Toast.LENGTH_SHORT).show()
            }
        }  // todo fin de salvar
        consltaButton.setOnClickListener {

            val contacList = db.getAllContacts()
            /* todo este for ya no hace falta porque tenemos el joinToString()
            // implementado en la siguiente variable
            for (contact in contacList)
                contact.name
                contact.email
                if(contact.id==variableBusqueda){

                }
                Log.d("Contacts","ID: ${contact.id}, Nombre: ${contact.name}, Email: ${contact.email}")
            */
            val resultadoBD =contacList.joinToString()  //todo este joinToString() recorre toda la coleccion
                                                        //todo y lo esta metiendo en la variable
            bosquejo.text=resultadoBD
        }// todo fin de consultabutton
    }
}