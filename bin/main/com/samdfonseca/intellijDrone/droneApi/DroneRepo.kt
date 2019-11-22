package com.samdfonseca.intellijDrone.droneApi

data class DroneRepo(
    val active: Boolean,
    val allow_deploys: Boolean,
    val allow_pr: Boolean,
    val allow_push: Boolean,
    val allow_tags: Boolean,
    val avatar_url: String,
    val clone_url: String,
    val config_file: String,
    val default_branch: String,
    val full_name: String,
    val gated: Boolean,
    val id: Int,
    val last_build: Int,
    val link_url: String,
    val name: String,
    val owner: String,
    val private: Boolean,
    val scm: String,
    val timeout: Int,
    val trusted: Boolean,
    val visibility: String
)
