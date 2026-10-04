package com.knowit.data

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class HighScoreRepositoryTest {

    @get:Rule
    val tmp = TemporaryFolder()

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private lateinit var repo: HighScoreRepository

    @Before
    fun setup() {
        val dataStore = PreferenceDataStoreFactory.create(scope = scope) {
            tmp.newFile("test.preferences_pb").also { it.delete() }
        }
        repo = HighScoreRepository(dataStore)
    }

    @After
    fun teardown() {
        scope.cancel()
    }

    @Test
    fun `high score defaults to zero`() = runBlocking {
        assertEquals(0, repo.getHighScore())
    }

    @Test
    fun `high score only ever increases`() = runBlocking {
        repo.saveHighScore(120)
        assertEquals(120, repo.getHighScore())

        repo.saveHighScore(80)
        assertEquals(120, repo.getHighScore())

        repo.saveHighScore(200)
        assertEquals(200, repo.getHighScore())
    }
}
