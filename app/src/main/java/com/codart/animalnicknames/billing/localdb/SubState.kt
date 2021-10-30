package com.codart.animalnicknames.billing.localdb

import android.util.Log
import io.paperdb.Paper

object SubState {
    fun saveItems(state: Boolean) {
        Paper.book().write("sub", state)
    }

    fun isSubActive(): Boolean {
        return try {
            Paper.book().read("sub", false)
        }
        catch (e: Exception)
        {
            Log.e("Db sub error", e.toString())
            false
        }
    }
}