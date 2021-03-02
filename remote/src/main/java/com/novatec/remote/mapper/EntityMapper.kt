package com.novatec.remote.mapper

/**
 * Interface for model mappers. It provides helper methods that facilitate
 * retrieving of models from outer data source layers
 *
 * @param <M> the remote model input type
 * @param <E> the entity model output type
 */
interface EntityMapper< M,  E> {
    fun mapToEntity(type: M): E
    fun mapFromEntity(type: E): M
}