/*
 *
 *  * Nextcloud Talk application
 *  *
 *  * @author Mario Danic
 *  * Copyright (C) 2017-2020 Mario Danic <mario@lovelyhq.com>
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.nextcloud.talk.newarch.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nextcloud.talk.newarch.local.models.UserNgEntity
import com.nextcloud.talk.newarch.local.models.other.UserStatus

@Dao
abstract class UsersDao {
    // get active user
    @Query("SELECT * FROM users where status = 2")
    abstract fun getActiveUser(): UserNgEntity?

    @Query("SELECT * FROM users WHERE status = 2")
    abstract fun getActiveUserLiveData(): LiveData<UserNgEntity?>

    @Query("SELECT * from users ORDER BY status DESC")
    abstract fun getUsersLiveData(): LiveData<List<UserNgEntity>>

    @Query("SELECT * from users WHERE status != 2 ORDER BY status DESC")
    abstract fun getUsersLiveDataWithoutActive(): LiveData<List<UserNgEntity>>

    @Query("DELETE FROM users WHERE id = :id")
    abstract suspend fun deleteUserWithId(id: Long)

    @Update
    abstract suspend fun updateUser(user: UserNgEntity): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveUser(user: UserNgEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun saveUsers(vararg users: UserNgEntity): List<Long>

    // get all users not scheduled for deletion
    @Query("SELECT * FROM users where status != 0")
    abstract fun getUsers(): List<UserNgEntity>

    @Query("SELECT * FROM users where id = :id")
    abstract fun getUserWithId(id: Long): UserNgEntity

    @Query("SELECT * FROM users where status = 0")
    abstract fun getUsersScheduledForDeletion(): List<UserNgEntity>

    @Query("SELECT * FROM users WHERE username = :username AND base_url = :server")
    abstract suspend fun getUserWithUsernameAndServer(username: String, server: String): UserNgEntity?

    @Transaction
    open suspend fun setUserAsActiveWithId(id: Long) : Boolean {
        val users = getUsers()
        for (user in users) {
            if (user.id != id && UserStatus.ACTIVE == user.status) {
                user.status = UserStatus.DORMANT
                updateUser(user)
            } else if (user.id == id && UserStatus.ACTIVE != user.status) {
                user.status = UserStatus.ACTIVE
                updateUser(user)
            }
        }

        return true
    }

    @Transaction
    open suspend fun markUserForDeletion(id: Long): Boolean {
        val users = getUsers()
        for (user in users) {
            if (user.id == id) {
                user.status = UserStatus.PENDING_DELETE
                updateUser(user)
                break
            }
        }

        return setAnyUserAsActive()
    }
    
    @Transaction
    open suspend fun setAnyUserAsActive(): Boolean {
        val users = getUsers()
        for (user in users) {
            user.status = UserStatus.ACTIVE
            updateUser(user)
            return true
        }

        return false
    }
}