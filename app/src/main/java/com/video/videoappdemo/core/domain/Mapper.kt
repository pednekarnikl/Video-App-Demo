package com.video.videoappdemo.core.domain

interface Mapper<E, R> {
    fun mapFromEntity(entity: E): R?
    fun mapToEntity(domain: R): E?
}