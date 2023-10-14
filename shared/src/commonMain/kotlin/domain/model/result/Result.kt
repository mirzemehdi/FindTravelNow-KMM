package domain.model.result

/**
 * A sealed interface that can be either successful result containing a data of type [T]
 * or an error result containing an errorEntity of type [ErrorEntity]
 */
sealed interface Result<out T> {

    /**
     * A class represents that result returned successfully.
     *
     * @property data a [T] object that contains success outcome
     */
    data class Success<T>(val data: T) : Result<T>

    /**
     * A class represents that some error happened while returning result.
     *
     * @property errorEntity an [ErrorEntity] object that contains error states.
     * @see ErrorEntity for different types of errors.
     */
    data class Error(val errorEntity: ErrorEntity? = null) : Result<Nothing>

    /**
     * Companion object for [Result] class that contains empty [Result.Success] object.
     */
    companion object {
        /**
         * Success result with an empty value. This can be used if you need to return
         * success result from a function but without any data.
         */
        val EMPTY = Success(Unit)

        fun <T> success(data: T): Result<T> = Success(data)
        fun error(errorEntity: ErrorEntity? = null): Error = Error(errorEntity)
    }
}

/**
 * Helper extension function that calls [action] if result is successful.
 *
 * @param action a function that will be called when result is successful
 * @return same result object
 *
 * Since function returns the same result object, this function can be used as a chain with
 * [Result.onError] function together. For example:
 *
 * var result: Result<Int>
 *
 * result
 *   .onSuccess { data-> }
 *   .onError { error-> }`
 */
inline fun <T : Any?> Result<T>.onSuccess(action: (T) -> Unit): Result<T> {
    if (this is Result.Success) action(data)
    return this
}

/**
 * Helper extension function that calls [action] if result is error.
 *
 * @param action a function that will be called when result is error
 * @return same result object
 */
inline fun <T : Any?> Result<T>.onError(action: (errorEntity: ErrorEntity?) -> Unit): Result<T> {
    if (this is Result.Error) action(errorEntity)
    return this
}

inline fun <T : Any?, R> Result<T>.mapDataOnSuccess(transform: (T) -> R): Result<R> {
    return when (this) {
        is Result.Error -> this
        is Result.Success -> Result.success(transform(data))
    }
}