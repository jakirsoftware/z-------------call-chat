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

package com.nextcloud.talk.newarch.local.converters

import androidx.room.TypeConverter
import com.nextcloud.talk.newarch.local.models.other.ChatMessageStatus

class ChatMessageStatusConverter {
    @TypeConverter
    fun fromChatMessageStatusToInt(chatMessageStatus: ChatMessageStatus): Int {
        return chatMessageStatus.ordinal
    }

    @TypeConverter
    fun fromIntToChatMessageStatus(value: Int): ChatMessageStatus {
        return when (value) {
            0 -> ChatMessageStatus.SENT
            1 -> ChatMessageStatus.RECEIVED
            2 -> ChatMessageStatus.PENDING
            3 -> ChatMessageStatus.PROCESSING
            4 -> ChatMessageStatus.FAILED_ONCE
            5 -> ChatMessageStatus.FAILED_TWICE
            else -> ChatMessageStatus.FAILED
        }
    }
}