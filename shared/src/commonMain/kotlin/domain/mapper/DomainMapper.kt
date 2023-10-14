package domain.mapper

/**
 *
 * This is for mapping data layer objects to domain layer models
 */

interface DomainMapper<DomainModel> {
    fun mapToDomainModel(): DomainModel
}

/**
 * This is for mapping data to localSource (Database) entity object
 */

interface EntityMapper<DomainModel, DatabaseEntity> {
    fun mapToEntity(domainObj: DomainModel): DatabaseEntity
}

/**
 * This is for mapping data to the network request object
 */

interface NetworkMapper<DomainModel, NetworkRequestObj> {
    fun mapToNetworkRequest(domainObj: DomainModel): NetworkRequestObj
}