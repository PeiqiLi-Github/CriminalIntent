package com.bignerdranch.android.criminalintent

import android.content.Context
import androidx.room.Room
import datebase.CrimeDatabase
import java.util.UUID

private const val DATABASE_NAME = "crime-database"
class CrimeRepository private constructor(context: Context) {
    private val database:CrimeDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            CrimeDatabase::class.java,
            DATABASE_NAME
        ).build()

    suspend fun getCrimes() : List<Crime> = database.crimeDao().getCrimes()

    suspend fun getCrime(id: UUID) : Crime = database.crimeDao().getCrime(id)
    companion object {
        private var INSTANCE: CrimeRepository? = null
//  our CrimeRepository needs to be a singleton
        fun initilize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get() : CrimeRepository {
            return INSTANCE ?:
            throw java.lang.IllegalStateException("CrimeRepositoy must be initialized")
        }
    }
}