package com.maengji.sqliteexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.maengji.sqliteexample.db.DBHelper
import com.maengji.sqliteexample.vo.Person

class MainActivity : AppCompatActivity() {

    private lateinit var btnCreateDatabase: Button
    private lateinit var btnInsertDatabase: Button
    private lateinit var btnSelectAllData: Button
    private lateinit var lvPeople: ListView
    private var dbHelper: DBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lvPeople = findViewById(R.id.lvPeople)

        btnCreateDatabase = findViewById(R.id.btnCreateButton)
        btnCreateDatabase.setOnClickListener {
            val etDBName = EditText(this)
            etDBName.hint = "DB명을 입력하세요"

            val dialog = AlertDialog.Builder(this)
            dialog
                .setTitle("Database이름을 입력하세요.")
                .setView(etDBName)
                .setPositiveButton("생성") { dialog, which ->
                    if (etDBName.text.toString().isNotEmpty()) {
                        dbHelper = DBHelper(this, etDBName.text.toString(), null, 2)
                        dbHelper?.testDB()
                    }
                }
                .setNegativeButton("취소") { _, _ -> }
                .create()
                .show()
        }

        btnInsertDatabase = findViewById(R.id.btnInsertButton)
        btnInsertDatabase.setOnClickListener {
            val layout = LinearLayout(this)
            layout.orientation = LinearLayout.VERTICAL

            val etName = EditText(this)
            etName.hint = "이름을 입력하세요."

            val etAge = EditText(this)
            etAge.hint = "나이를 입력하세요."

            val etPhone = EditText(this)
            etPhone.hint = "전화번호를 입력하세요."

            val etAddress = EditText(this)
            etAddress.hint = "주소를 입력하세요."

            layout.addView(etName)
            layout.addView(etAge)
            layout.addView(etPhone)
            layout.addView(etAddress)

            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("정보를 입력하세요")
                .setView(layout)
                .setPositiveButton("등록") { dialog, which ->
                    val name = etName.text.toString()
                    val age = etAge.text.toString().toInt()
                    val phone = etPhone.text.toString()
                    val address = etAddress.text.toString()

                    if (dbHelper == null) {
                        dbHelper = DBHelper(this, "TEST", null, 2)
                    }

                    val person = Person(name = name, age = age, phone = phone, address = address)

                    dbHelper?.addPerson(person)
                }
                .setNegativeButton("취소") { _, _ -> }
                .create()
                .show()
        }

        btnSelectAllData = findViewById(R.id.btnSelectAllData)
        btnSelectAllData.setOnClickListener {
            lvPeople.visibility = View.VISIBLE

            if (dbHelper == null) {
                dbHelper = DBHelper(this, "TEST", null, 2)
            }

            val people = dbHelper?.getAllPersonData()
            if (people != null) {
                lvPeople.adapter = PersonListAdapter(this, people)
            }
        }

    }
}
