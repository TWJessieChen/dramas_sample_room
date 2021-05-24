package com.jc666.dramas_sample_room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jc666.dramas_sample_room.database.model.DramaData
import com.jc666.dramas_sample_room.database.model.DataDao

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */
@Database(entities = [DramaData::class], version = 1)
abstract class DataRoomDatabase : RoomDatabase() {

    abstract fun dataDao(): DataDao

    companion object {
        @Volatile
        private var INSTANCE: DataRoomDatabase? = null

        fun getDatabase(
            context: Context
        ): DataRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataRoomDatabase::class.java,
                    "dramas_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(DramaDatabaseCallback())
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class DramaDatabaseCallback(
        ) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                /**
                 * 可以在此做初始化
                 * 或
                 * 讀取資料來init
                 * 等等
                 */

            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
        suspend fun populateDatabase(dataDao: DataDao) {

        }
    }
}
