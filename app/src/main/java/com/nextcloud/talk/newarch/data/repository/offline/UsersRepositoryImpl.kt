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

package com.nextcloud.talk.newarch.data.repository.offline

import androidx.lifecycle.LiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import com.nextcloud.talk.newarch.domain.repository.offline.UsersRepository
import com.nextcloud.talk.newarch.local.dao.UsersDao
import com.nextcloud.talk.newarch.local.models.User
import com.nextcloud.talk.newarch.local.models.UserNgEntity
import com.nextcloud.talk.newarch.local.models.toUser

class UsersRepositoryImpl(private val usersDao: UsersDao) : UsersRepository {
    override fun getActiveUserLiveData(): LiveData<UserNgEntity?> {
        return usersDao.getActiveUserLiveData().distinctUntilChanged()
    }

    override fun getActiveUser(): UserNgEntity? {
        return usersDao.getActiveUser()
    }

    override fun getUsers(): List<UserNgEntity> {
        return usersDao.getUsers()
    }

    override fun getUserWithId(id: Long): UserNgEntity {
        return usersDao.getUserWithId(id)
    }

    override fun getUsersLiveData(): LiveData<List<User>> {
        return usersDao.getUsersLiveData().distinctUntilChanged().map { usersList ->
            usersList.map {
                it.toUser()
            }
        }
    }

    override fun getUsersLiveDataWithoutActive(): LiveData<List<User>> {
        return usersDao.getUsersLiveDataWithoutActive().distinctUntilChanged().map { usersList ->
            usersList.map {
                it.toUser()
            }
        }
    }

    override suspend fun getUserWithUsernameAndServer(
            username: String,
            server: String
    ): UserNgEntity? {
        return usersDao.getUserWithUsernameAndServer(username, server)
    }

    override suspend fun updateUser(user: UserNgEntity): Int {
        return usersDao.updateUser(user)
    }

    override suspend fun insertUser(user: UserNgEntity): Long {
        return usersDao.saveUser(user)
    }

    override suspend fun setUserAsActiveWithId(id: Long): Boolean {
        return usersDao.setUserAsActiveWithId(id)
    }

    override suspend fun deleteUserWithId(id: Long) {
        usersDao.deleteUserWithId(id)
    }

    override suspend fun setAnyUserAsActive(): Boolean {
        return usersDao.setAnyUserAsActive()
    }

    override suspend fun markUserForDeletion(id: Long): Boolean {
        return usersDao.markUserForDeletion(id)
    }

}
