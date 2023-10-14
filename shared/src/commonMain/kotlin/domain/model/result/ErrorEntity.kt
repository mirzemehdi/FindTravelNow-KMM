package domain.model.result

/**
 * A sealed interface representing Errors
 */
sealed interface ErrorEntity {

    companion object {
        fun networkConnection() = ErrorEntity.NetworkConnection
        fun apiError(
            errorMessage: String? = "",
            responseCode: Int? = null,
            exception: Exception? = null
        ) = ErrorEntity.ApiError(errorMessage, responseCode, exception)

        fun unexpected(exception: Throwable) = Unexpected(exception)
    }

    /**
     * An object that represents error happened because of internet connection.
     */
    object NetworkConnection : ErrorEntity

    /**
     * A class that represents error happened because of some api result.
     * @property errorMessage errorMessage that is returned from the server
     * @property responseCode responseCode of api request
     * @property exception the exception that caused the error
     */
    data class ApiError(
        val errorMessage: String? = "",
        val responseCode: Int? = null,
        val exception: Exception? = null
    ) : ErrorEntity

    /**
     * A class that represents Unexpected error type containing [e] of type [Throwable].
     * @property e the exception that caused the error
     */
    data class Unexpected(val e: Throwable) : ErrorEntity

    /**
     * In each feature module this can be extended
     */
    abstract class FeatureError : ErrorEntity
}