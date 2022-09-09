package hr.algebra.my_application.dao

import android.content.Context

fun getRepository(context: Context?) = SqlHelper(context)

