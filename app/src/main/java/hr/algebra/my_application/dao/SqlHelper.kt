package hr.algebra.my_application.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import hr.algebra.my_application.model.Item


private const val DB_NAME = "items.db"
private const val VERSION = 1
private const val TABLE = "items"

private val CREATE = "create table $TABLE(" +
        "${Item::_id.name} integer primary key autoincrement, " +
        "${Item::title.name} text not null, " +
        "${Item::explanation.name} text not null, " +
        "${Item::picturePath.name} text not null, " +
        "${Item::date.name} text not null, " +
        "${Item::read.name} integer not null" +
         ")"
private const val DROP = "drop table $TABLE"
class SqlHelper(
    context: Context?
) : SQLiteOpenHelper(context, DB_NAME, null, VERSION), Repository {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP)
        onCreate(db)
    }

    override fun delete(selection: String?, selectionArgs: Array<String>?): Int
       =writableDatabase.delete(TABLE, selection, selectionArgs)

    override fun update(values: ContentValues?, selection: String?,
                        selectionArgs: Array<String>?)
        =writableDatabase.update(TABLE, values, selection, selectionArgs)

    override fun insert(values: ContentValues?)
        =writableDatabase.insert(TABLE, null, values)

    override fun query(
        projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
        ): Cursor
            = readableDatabase.query(TABLE, projection, selection, selectionArgs, null, null, sortOrder)


}