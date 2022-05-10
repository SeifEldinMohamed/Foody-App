package com.seif.foody.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seif.foody.models.Result
import com.seif.foody.utils.Constants.Companion.FAVOURITE_RECIPES_TABLE

@Entity(tableName = FAVOURITE_RECIPES_TABLE)
class FavouriteEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var result:Result
)