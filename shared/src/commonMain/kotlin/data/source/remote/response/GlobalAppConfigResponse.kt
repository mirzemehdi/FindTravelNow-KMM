package data.source.remote.response

import domain.mapper.DomainMapper
import domain.model.GlobalAppConfig
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GlobalAppConfigResponse(
    @SerialName("is_update_required") val isUpdateRequired: Boolean = false,
) : DomainMapper<GlobalAppConfig> {
    override fun mapToDomainModel(): GlobalAppConfig {
        return GlobalAppConfig(isUpdateRequired = isUpdateRequired)
    }
}