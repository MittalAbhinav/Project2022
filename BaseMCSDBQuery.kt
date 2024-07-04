package com.sec.imslogger.database

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri

 open class BaseMCSDBQuery(private val context: Context, private val contentUri: Uri) {

    private val contentResolver: ContentResolver = context.contentResolver

    //  to query data from the content provider
    fun query(
        projection: Array<String>? = null, // same
        selection: String? = null,  // same
        selectionArgs: Array<String>? = null, // same
        sortOrder: String? = null
    ): Cursor? {
        return contentResolver.query(contentUri, projection, selection, selectionArgs, sortOrder)
    }



    //  to update data in the content provider
    fun update(values: ContentValues, selection: String?, selectionArgs: Array<String>?): Int {
        return contentResolver.update(contentUri, values, selection, selectionArgs) ?: 0
    }

    //  to delete data from the content provider
    fun delete(selection: String?, selectionArgs: Array<String>?): Int {
        return contentResolver.delete(contentUri, selection, selectionArgs) ?: 0
    }
}
