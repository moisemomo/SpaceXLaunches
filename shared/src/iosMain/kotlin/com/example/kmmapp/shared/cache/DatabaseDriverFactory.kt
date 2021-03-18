package com.example.kmmapp.shared.cache

import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory() {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(SpaceXLaunchesDatabase.Schema, "test.db")
    }
}