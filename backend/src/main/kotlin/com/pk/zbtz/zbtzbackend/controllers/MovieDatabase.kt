package com.pk.zbtz.zbtzbackend.controllers

enum class MovieDatabase {
    POSTGRESQL,
    MONGO_DB,
    INFLUX,
    FAKE_DATABASE,
}