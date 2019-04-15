package com.samdfonseca.intellijDrone.droneApi

import com.samdfonseca.intellijDrone.getLogger
import com.samdfonseca.intellijDrone.tsIntToInstant
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import retrofit2.Call
import retrofit2.Response
import java.time.Instant

data class DroneBuild(
    val author: String,
    val author_avatar: String,
    val author_email: String,
    val branch: String,
    val commit: String,
    val created_at: Int,
    val deploy_to: String,
    val enqueued_at: Int,
    val error: String,
    val event: DroneEvent,
    val finished_at: Int,
    val id: Int,
    val link_url: String,
    val message: String,
    val number: Int,
    val parent: Int,
    val procs: Array<DroneProc>,
    val ref: String,
    val refspec: String,
    val remote: String,
    val reviewed_at: Int,
    val reviewed_by: String,
    val sender: String,
    val signed: Boolean,
    val started_at: Int,
    val status: String,
    val timestamp: Int,
    val title: String,
    val verified: Boolean
) {
    private val logger = getLogger(this)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DroneBuild

        return EqualsBuilder()
            .append(author, other.author)
            .append(author_avatar, other.author_avatar)
            .append(author_email, other.author_email)
            .append(branch, other.branch)
            .append(commit, other.commit)
            .append(created_at, other.created_at)
            .append(deploy_to, other.deploy_to)
            .append(enqueued_at, other.enqueued_at)
            .append(error, other.error)
            .append(event, other.event)
            .append(finished_at, other.finished_at)
            .append(id, other.id)
            .append(link_url, other.link_url)
            .append(message, other.message)
            .append(number, other.number)
            .append(parent, other.parent)
            .append(procs, other.procs)
            .append(ref, other.ref)
            .append(refspec, other.refspec)
            .append(remote, other.remote)
            .append(reviewed_at, other.reviewed_at)
            .append(reviewed_by, other.reviewed_by)
            .append(sender, other.sender)
            .append(signed, other.signed)
            .append(started_at, other.started_at)
            .append(status, other.status)
            .append(timestamp, other.timestamp)
            .append(title, other.title)
            .append(verified, other.verified)
            .isEquals
    }

    override fun hashCode(): Int {
        return HashCodeBuilder()
            .append(author)
            .append(author_avatar)
            .append(author_email)
            .append(branch)
            .append(commit)
            .append(created_at)
            .append(deploy_to)
            .append(enqueued_at)
            .append(error)
            .append(event)
            .append(finished_at)
            .append(id)
            .append(link_url)
            .append(message)
            .append(number)
            .append(parent)
            .append(procs)
            .append(ref)
            .append(refspec)
            .append(reviewed_at)
            .append(reviewed_by)
            .append(sender)
            .append(signed)
            .append(started_at)
            .append(timestamp)
            .append(title)
            .append(verified)
            .toHashCode()
    }

    fun createdAtInstant() = tsIntToInstant(this.created_at)
    fun enqueuedAtInstant() = tsIntToInstant(this.enqueued_at)
    fun finishedAtInstant() = tsIntToInstant(this.finished_at)
    fun reviewedAtInstant() = tsIntToInstant(this.reviewed_at)
    fun startedAtInstant() = tsIntToInstant(this.started_at)
    fun timestampInstant() = tsIntToInstant(this.timestamp)

    fun getRepo(droneAPI: DroneAPI, callback: (DroneRepo?) -> Unit) {
        if (!droneAPI.hasRequiredSettings()) {
            logger.debug("missing required Drone settings")
            return
        }
        logger.debug("finding build ${this.number} repo")
        droneAPI.getService().currentUserReposList().enqueue(object: retrofit2.Callback<Array<DroneRepo>> {
            override fun onFailure(call: Call<Array<DroneRepo>>, t: Throwable) {
                logger.debug("handling currentUserReposList failure")
                logger.error(t.message)
            }

            override fun onResponse(call: Call<Array<DroneRepo>>, response: Response<Array<DroneRepo>>) {
                logger.debug("handling currentUserReposList response")
                val repos = response.body()
                val matches = repos?.filter { it.clone_url == remote }
                callback.invoke(matches?.firstOrNull())
            }
        })
    }
}
