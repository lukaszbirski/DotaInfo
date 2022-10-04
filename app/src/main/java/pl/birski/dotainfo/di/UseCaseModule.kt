package pl.birski.dotainfo.di

import android.app.Application
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.birski.herointeractors.UseCaseFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    @Named("heroAndroidSqlDriver") // in case you had another SQL Delight db
    fun provideAndroidDriver(app: Application): SqlDriver {
        return AndroidSqliteDriver(
            schema = UseCaseFactory.schema,
            context = app,
            name = UseCaseFactory.dbName
        )
    }

    @Provides
    @Singleton
    fun provideUseCase(
        @Named("heroAndroidSqlDriver") sqlDriver: SqlDriver
    ) = UseCaseFactory.build(sqlDriver = sqlDriver)
}
