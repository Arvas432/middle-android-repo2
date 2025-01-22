package ru.yandex.praktikumchatapp.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.retryWhen

class ChatRepository(
    private val api: ChatApi = ChatApi()
) {
    fun getReplyMessage(): Flow<String> {
        var currentDelay = INITIAL_DELAY
        return api.getReply().retryWhen { cause, attempt ->
            if (cause is Exception) {
                delay(currentDelay)
                currentDelay *= DELAY_FACTOR
                true
            } else {
                false
            }
        }
    }

    companion object {
        private const val INITIAL_DELAY = 100L
        private const val DELAY_FACTOR = 2
    }
}