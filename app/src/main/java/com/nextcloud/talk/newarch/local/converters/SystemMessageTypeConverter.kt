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
import com.nextcloud.talk.models.json.chat.ChatMessage.SystemMessageType
import com.nextcloud.talk.models.json.chat.ChatMessage.SystemMessageType.*

class SystemMessageTypeConverter {
    @TypeConverter
    fun fromStringToSystemMessageType(string: String): SystemMessageType {
        when (string) {
            "conversation_created" -> return CONVERSATION_CREATED
            "conversation_renamed" -> return CONVERSATION_RENAMED
            "call_started" -> return CALL_STARTED
            "call_joined" -> return CALL_JOINED
            "call_left" -> return CALL_LEFT
            "call_ended" -> return CALL_ENDED
            "guests_allowed" -> return GUESTS_ALLOWED
            "guests_disallowed" -> return GUESTS_DISALLOWED
            "password_set" -> return PASSWORD_SET
            "password_removed" -> return PASSWORD_REMOVED
            "user_added" -> return USER_ADDED
            "user_removed" -> return USER_REMOVED
            "moderator_promoted" -> return MODERATOR_PROMOTED
            "moderator_demoted" -> return MODERATOR_DEMOTED
            "file_shared" -> return FILE_SHARED
            "lobby_none" -> return LOBBY_NONE
            "lobby_non_moderators" -> return LOBBY_NON_MODERATORS
            "lobby_timer_reached" -> return LOBBY_OPEN_TO_EVERYONE
            else -> return DUMMY
        }
    }

    @TypeConverter
    fun fromSystemMessageTypeToString(systemMessageType: SystemMessageType?): String {

        if (systemMessageType == null) {
            return ""
        }

        when (systemMessageType) {
            CONVERSATION_CREATED -> return "conversation_created"
            CONVERSATION_RENAMED -> return "conversation_renamed"
            CALL_STARTED -> return "call_started"
            CALL_JOINED -> return "call_joined"
            CALL_LEFT -> return "call_left"
            CALL_ENDED -> return "call_ended"
            GUESTS_ALLOWED -> return "guests_allowed"
            GUESTS_DISALLOWED -> return "guests_disallowed"
            PASSWORD_SET -> return "password_set"
            PASSWORD_REMOVED -> return "password_removed"
            USER_ADDED -> return "user_added"
            USER_REMOVED -> return "user_removed"
            MODERATOR_PROMOTED -> return "moderator_promoted"
            MODERATOR_DEMOTED -> return "moderator_demoted"
            FILE_SHARED -> return "file_shared"
            LOBBY_NONE -> return "lobby_none"
            LOBBY_NON_MODERATORS -> return "lobby_non_moderators"
            LOBBY_OPEN_TO_EVERYONE -> return "lobby_timer_reached"
            else -> return ""
        }
    }

}