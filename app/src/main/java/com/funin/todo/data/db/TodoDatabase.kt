package com.funin.todo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.funin.todo.data.db.converters.TodoDatabaseConverters
import com.funin.todo.data.vo.Me
import com.funin.todo.data.vo.User

@Database(
    entities = [
        User::class,
        Me::class
    ],
    version = 1
)
@TypeConverters(TodoDatabaseConverters::class)
abstract class TodoDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: TodoDatabase? = null
        private const val DB_NAME = "todo-database"

        fun getInstance(context: Context): TodoDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context): TodoDatabase =
            Room.databaseBuilder(context, TodoDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

    abstract fun userDao(): UserDao
    abstract fun meDao(): MeDao
}