package com.koding.web.data.local

import androidx.room.*

@Entity(tableName = "remote_keys_article")
data class RemoteKeysArticle(
    @PrimaryKey val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)

@Dao
interface RemoteKeysArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeysArticle>)

    @Query("SELECT * FROM remote_keys_article WHERE id = :id")
    suspend fun getRemoteKeysId(id: String): RemoteKeysArticle?

    @Query("DELETE FROM remote_keys_article")
    suspend fun deleteRemoteKeys()
}