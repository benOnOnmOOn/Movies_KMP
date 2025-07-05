import kotlin.coroutines.cancellation.CancellationException

@Suppress("TooGenericExceptionCaught")
inline fun <R> runSuspendCatching(block: () -> R): Result<R> =
    try {
        Result.success(block())
    } catch (c: CancellationException) {
        throw c
    } catch (e: Throwable) {
        Result.failure(e)
    }
