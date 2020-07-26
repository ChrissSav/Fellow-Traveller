package gr.fellow.fellow_traveller.room.entites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_auth")
data class RegisteredUserEntity(

    @PrimaryKey(autoGenerate = false)
    val id : Int,
    @ColumnInfo(name = "first_name")
    val firstName : String,
    @ColumnInfo(name = "last_name")
    val lastName : String,
    val rate : Float,
    val reviews : Int,
    val picture : String,
    @ColumnInfo(name = "about_me")
    val aboutMe : String,
    val phone : String,
    val email : String


) {
    override fun toString(): String {
        return "RegisteredUserEntity(id=$id, firstName='$firstName', lastName='$lastName', rate=$rate, reviews=$reviews, picture='$picture', aboutMe='$aboutMe', phone='$phone', email='$email')"
    }
}
