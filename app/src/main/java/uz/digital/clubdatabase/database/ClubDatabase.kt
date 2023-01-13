package uz.digital.clubdatabase.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.digital.clubdatabase.model.Club

@Database(entities = [Club::class], version = 1, exportSchema = false)
abstract class ClubDatabase : RoomDatabase() {
    abstract val dao: ClubDao

    companion object {
        @Volatile
        private var instance: ClubDatabase? = null

        operator fun invoke(context: Context): ClubDatabase = instance ?: synchronized(Any()) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context): ClubDatabase {
            return Room.databaseBuilder(
                context,
                ClubDatabase::class.java,
                "Club.db"
            ).allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}