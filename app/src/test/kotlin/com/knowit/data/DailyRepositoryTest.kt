package com.knowit.data

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.knowit.model.Outcome
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class DailyRepositoryTest {

    @get:Rule
    val tmp = TemporaryFolder()

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private lateinit var repo: DailyRepository

    @Before
    fun setup() {
        val dataStore = PreferenceDataStoreFactory.create(scope = scope) {
            tmp.newFile("test.preferences_pb").also { it.delete() }
        }
        repo = DailyRepository(dataStore)
    }

    @After
    fun teardown() {
        scope.cancel()
    }

    @Test
    fun `defaults to nothing played`() = runBlocking {
        assertEquals(DailySave(), repo.load())
    }

    @Test
    fun `saves and loads a run`() = runBlocking {
        val save = DailySave(
            day = 20_000,
            outcomes = listOf(Outcome.CORRECT, Outcome.CLOSE, Outcome.WRONG),
            fiftyFiftyIndex = 2,
            lastCompletedDay = 19_999,
            streak = 4,
            bestStreak = 9
        )
        repo.save(save)
        assertEquals(save, repo.load())
    }

    @Test
    fun `streak grows on consecutive days and restarts after a gap`() {
        val day1 = DailySave().completed(100)
        assertEquals(1, day1.streak)
        val day2 = day1.completed(101)
        assertEquals(2, day2.streak)
        assertSame(day2, day2.completed(101)) // finishing the same day twice changes nothing
        val afterGap = day2.completed(103)
        assertEquals(1, afterGap.streak)
        assertEquals(2, afterGap.bestStreak)
    }

    @Test
    fun `current streak lapses after a missed day`() {
        val save = DailySave().completed(100).completed(101)
        assertEquals(2, save.currentStreak(today = 101))
        assertEquals(2, save.currentStreak(today = 102)) // today not played yet
        assertEquals(0, save.currentStreak(today = 103))
        assertEquals(0, DailySave().currentStreak(today = 103))
    }

    @Test
    fun `progress only counts for its own day`() {
        val save = DailySave(day = 100, outcomes = listOf(Outcome.CORRECT), fiftyFiftyIndex = 0)
        assertEquals(1, save.outcomesFor(100).size)
        assertEquals(0, save.outcomesFor(101).size)
        assertEquals(-1, save.fiftyFiftyIndexFor(101))
    }
}
