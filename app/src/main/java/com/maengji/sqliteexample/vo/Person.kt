package com.maengji.sqliteexample.vo

data class Person(
    var id: Int? = null,
    val name: String,
    val age: Int,
    val phone: String,
    val address: String
)