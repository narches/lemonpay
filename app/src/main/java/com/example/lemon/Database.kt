package com.example.lemon.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity
data class MenuItemRoom(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val price: Double,
    val imageResId: Int
)

@Dao
interface MenuItemDao {

    // Fetch all items
    @Query("SELECT * FROM MenuItemRoom")
    fun getAll(): LiveData<List<MenuItemRoom>>

    // Insert list of items
    @Insert
    fun insertAll(vararg items: MenuItemRoom)

    // Check if table is empty (optional)
    @Query("SELECT (SELECT COUNT(*) FROM MenuItemRoom) == 0")
    fun isEmpty(): Boolean

    // Count items (THIS WAS MISSING A @Query)
    @Query("SELECT COUNT(*) FROM MenuItemRoom")
    suspend fun countItems(): Int
}

@Database(
    entities = [MenuItemRoom::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun menuItemDao(): MenuItemDao
}

class MenuRepository(private val dao: MenuItemDao) {
    val allItems = dao.getAll()

    suspend fun insertAll(items: List<MenuItemRoom>) {
        dao.insertAll(*items.toTypedArray())
    }
}
