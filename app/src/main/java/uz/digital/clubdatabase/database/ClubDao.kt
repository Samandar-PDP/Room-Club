package uz.digital.clubdatabase.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import uz.digital.clubdatabase.model.Club

@Dao
interface ClubDao {

    @Query("SELECT * FROM Club ORDER BY id DESC")
    fun getAllClubs(): List<Club>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveClub(club: Club)

    @Update
    fun updateClub(club: Club)

    @Delete
    fun deleteClub(club: Club)
}