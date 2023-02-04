package com.example.domain.mapper

interface IMapper<Source,Domain> {

    fun mapToDomain(item:Source?):Domain?
    fun mapFromDomain(item: Domain?):Source?
}