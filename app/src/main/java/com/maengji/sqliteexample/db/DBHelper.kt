package com.maengji.sqliteexample.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull
import com.maengji.sqliteexample.vo.Person

class DBHelper(
    private val context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        val queryString = """
         CREATE TABLE TEST_TABLE ( 
         _ID INTEGER PRIMARY KEY AUTOINCREMENT, 
         NAME TEXT, 
         AGE INTEGER,
         PHONE TEXT,
         ADDRESS TEXT)
        """.trimIndent()

        db!!.execSQL(queryString)

        Toast.makeText(context, "Table 생성완료", Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Toast.makeText(context, "버전이 올라갔습니다.", Toast.LENGTH_SHORT).show()

        if (oldVersion == 1 && newVersion == 2) {
            val queryString = """
                alter table TEST_TABLE add ADDRESS TEXT    
            """.trimIndent()

            db?.execSQL(queryString)
        }
    }

    fun testDB() {
        val db = readableDatabase

    }

    fun addPerson(person: Person) {
        val db = writableDatabase

        val queryString = """
            INSERT INTO TEST_TABLE (
            NAME, AGE, ADDRESS, PHONE )
            VALUES ( ?, ?, ?, ? )
        """.trimIndent()

        db.execSQL(queryString, arrayOf(person.name, person.age, person.address, person.phone))

        Toast.makeText(context, "Insert 완료", Toast.LENGTH_SHORT).show()

    }

    fun getAllPersonData(): ArrayList<Person> {
        val queryString = """ SELECT _ID, NAME, AGE, PHONE, ADDRESS FROM TEST_TABLE """

        val db = readableDatabase

        val cursor = db.rawQuery(queryString, null)

        var people: ArrayList<Person> = arrayListOf()

        while(cursor.moveToNext()) {
            val person = Person(
                cursor.getInt(0),
                cursor.getStringOrNull(1) ?: "",
                cursor.getIntOrNull(2) ?: 0,
                cursor.getStringOrNull(3) ?: "",
                cursor.getStringOrNull(4) ?: ""
            )

            people.add(person)
        }

        return people
    }
}