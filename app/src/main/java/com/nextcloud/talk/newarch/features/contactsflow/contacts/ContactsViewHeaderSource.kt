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

package com.nextcloud.talk.newarch.features.contactsflow.contacts

import android.content.Context
import com.nextcloud.talk.R
import com.nextcloud.talk.models.json.participants.Participant
import com.nextcloud.talk.newarch.features.contactsflow.ParticipantElement
import com.otaliastudios.elements.Page
import com.otaliastudios.elements.Source
import com.otaliastudios.elements.extensions.HeaderSource

class ContactsHeaderSource(private val context: Context, private val elementType: Int) : HeaderSource<ParticipantElement, String>() {

    // Store the last header that was added, even if it belongs to a previous page.
    private var headersAlreadyAdded = mutableListOf<String>()

    override fun dependsOn(source: Source<*>) = source is ContactsViewSource

    override fun computeHeaders(page: Page, list: List<ParticipantElement>): List<Data<ParticipantElement, String>> {
        val results = arrayListOf<Data<ParticipantElement, String>>()
        headersAlreadyAdded = mutableListOf()
        for (participantElement in list) {
            if (participantElement.data is Participant) {
                val participant = participantElement.data
                val header = when (participant.source) {
                    "users" -> {
                        context.getString(R.string.nc_contacts)
                    }
                    "groups" -> {
                        context.getString(R.string.nc_groups)
                    }
                    "emails" -> {
                        context.getString(R.string.nc_emails)
                    }
                    "circles" -> {
                        context.getString(R.string.nc_circles)
                    }
                    else -> {
                        context.getString(R.string.nc_others)

                    }
                }

                if (!headersAlreadyAdded.contains(header)) {
                    results.add(Data(participantElement, header))
                    headersAlreadyAdded.add(header)
                }
            }
        }

        return results
    }

    override fun getElementType(data: Data<ParticipantElement, String>): Int {
        return elementType
    }

    override fun areItemsTheSame(first: Data<ParticipantElement, String>, second: Data<ParticipantElement, String>): Boolean {
        return first == second
    }
}