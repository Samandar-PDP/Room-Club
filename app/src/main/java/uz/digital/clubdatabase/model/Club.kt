package uz.digital.clubdatabase.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Club(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val rating: Int,
    val league: String,
    val logo: ByteArray
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Club

        if (name != other.name) return false
        if (rating != other.rating) return false
        if (league != other.league) return false
        if (!logo.contentEquals(other.logo)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + rating
        result = 31 * result + league.hashCode()
        result = 31 * result + logo.contentHashCode()
        return result
    }
}
