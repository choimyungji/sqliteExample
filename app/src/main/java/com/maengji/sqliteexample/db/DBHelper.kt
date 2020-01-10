package com.maengji.sqliteexample.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.maengji.sqliteexample.vo.Person

class DBHelper(
    private val context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        val sb = StringBuffer()

        sb.append(" CREATE TABLE TEST_TABLE ( ")
        sb.append(" _ID INTEGER PRIMARY KEY AUTOINCREMENT, ")
        sb.append(" NAME TEXT, ")
        sb.append(" AGE INTEGER, ")
        sb.append(" PHONE TEXT ) ")

        db!!.execSQL(sb.toString())

        Toast.makeText(context, "Table 생성완료", Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Toast.makeText(context, "버전이 올라갔습니다.", Toast.LENGTH_SHORT).show()
    }

    fun testDB() {
        val db = readableDatabase

    }

    fun addPerson(person: Person) {
        val db = writableDatabase

        val queryString = """
            INSERT INTO TEST_TABLE (
            NAME, AGE, PHONE )
            VALUES ( ?, ?, ? )
        """.trimIndent()

        db.execSQL(queryString, arrayOf(person.name, person.age.toInt(), person.phone))

        Toast.makeText(context, "Insert 완료", Toast.LENGTH_SHORT).show()

    }

    fun getAllPersonData(): ArrayList<Person> {
        val queryString = """ SELECT _ID, NAME, AGE, PHONE FROM TEST_TABLE """

        val db = readableDatabase

        val cursor = db.rawQuery(queryString, null)

        var people: ArrayList<Person> = arrayListOf()

        while(cursor.moveToNext()) {
            val person = Person(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getString(3))

            people.add(person)
        }

        return people
    }
}