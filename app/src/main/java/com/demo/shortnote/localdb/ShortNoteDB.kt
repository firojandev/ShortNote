package com.demo.shortnote.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.demo.shortnote.model.MyNote

@Database(entities = [MyNote::class], version = 1)
abstract class ShortNoteDB: RoomDatabase() {

    abstract val myNoteDAO: MyNoteDAO

    companion object {
        @Volatile
        private var INSTANCE: ShortNoteDB? = null

        fun getInstance(context: Context): ShortNoteDB {
            synchronized(this){
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ShortNoteDB::class.java,
                        "db_shortnotes"
                    ).build()
                }
                return instance
            }
        }


    }

}