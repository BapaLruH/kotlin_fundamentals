package ru.movie.app.data.service

interface HttpResponse {
    val statusCode: Int
    val statusMessage: String?
    val url: String?
}