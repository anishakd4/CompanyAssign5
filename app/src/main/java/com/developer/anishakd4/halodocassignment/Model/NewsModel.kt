package com.developer.anishakd4.halodocassignment.Model

data class NewsModel(
    val hits: List<HitsModel>,
    val nbHits: Int,
    val page: Int,
    val nbPages: Int,
    val hitsPerPage: Int,
    val exhaustiveNbHits: Boolean,
    val query: String,
    val params: String,
    val processingTimeMS: Int
)