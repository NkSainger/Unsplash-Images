package com.nk.searchimages.datamodel

data class Result(
    val alt_description: String,
    val blur_hash: String,
    val breadcrumbs: List<Breadcrumb>,
    val color: String,
    val created_at: String,
    val current_user_collections: List<Any>,
    val description: String,
    val height: Int,
    val id: String,
    val liked_by_user: Boolean,
    val likes: Int,
    val links: Links,
    val promoted_at: String,
    val slug: String,
    val sponsorship: Sponsorship,
    val tags: List<Tag>,
    val topic_submissions: TopicSubmissionsX,
    val updated_at: String,
    val urls: UrlsX,
    val user: User,
    val width: Int
)