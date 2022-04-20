package com.seif.foody.data

import com.seif.foody.data.network.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localeDataSource: LocaleDataSource
) {
    val remote = remoteDataSource
    val locale = localeDataSource
}