package com.example.e_commerceapplication

import android.content.Context
import androidx.room.Room
import com.example.e_commerceapplication.repository.RegisterRepository
import com.example.e_commerceapplication.roomdb.Database
import com.example.e_commerceapplication.roomdb.address.AddressRepo
import com.example.e_commerceapplication.roomdb.cart.CartRepo
import com.example.e_commerceapplication.roomdb.product.ProductRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ): Database{
        return Room.databaseBuilder(
            appContext,
            Database::class.java,
            "ecommerce.db"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }
}