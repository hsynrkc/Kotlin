package com.androidapp.sozlukuygulamasi

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class VeriTabaniYardimcisi(context:Context) : SQLiteOpenHelper(context,"sozluk.sqlite",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS `kelimeler` (\n"+
                "\t`kelimeler_id`\tINTEGER PRIMARY KEY AUTOINCREMENT, \n"+
                "\t`ingilizce`\tTEXT,\n"+
                "\t`turkce`\tTEXT\n" +
                ");")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS kelimeler")
        onCreate(db)
    }
}