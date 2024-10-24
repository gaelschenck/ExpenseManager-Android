package com.gaelschenck.expensemanager

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Spinner
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class AddExpense : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_expense)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Initialisation des vues
        val amountInput = findViewById<EditText>(R.id.expenseAmount)
        val descriptionInput = findViewById<EditText>(R.id.expenseDescription)
        val dateTextView = findViewById<TextView>(R.id.expenseDate)
        val categorySpinner = findViewById<Spinner>(R.id.expenseCategory)
        val saveButton = findViewById<Button>(R.id.saveExpense)

        // Configuration du calendrier pour le sélecteur de date
        val calendar = Calendar.getInstance()

        // Définir un DatePickerDialog pour changer la date quand on clique sur la TextView
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            // Mettre à jour la TextView avec la nouvelle date sélectionnée
            val selectedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)
            dateTextView.text = selectedDate
        }

        // Par défaut, on affiche la date actuelle
        val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        dateTextView.text = currentDate

        // Ouvrir le DatePickerDialog au clic sur le TextView de la date
        dateTextView.setOnClickListener {
            DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Configuration des catégories dans le Spinner
        val categories = arrayOf("Alimentation", "Transport", "Logement", "Loisirs", "Autres")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter

        // Gérer l'enregistrement de la dépense
        saveButton.setOnClickListener {
            val amount = amountInput.text.toString()
            val description = descriptionInput.text.toString()
            val category = categorySpinner.selectedItem.toString()
            val date = currentDate

            if (amount.isNotEmpty() && description.isNotEmpty()) {
                // Pour l'instant, on affiche les informations avec un Toast
                Toast.makeText(this, "Dépense enregistrée: \nMontant: $amount\nDescription: $description\nCatégorie: $category\nDate: $date", Toast.LENGTH_LONG).show()

                // Retourner à l'activité précédente
                finish()
            } else {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            }
        }
    }
}