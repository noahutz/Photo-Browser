package com.noahutz.photobrowser.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.noahutz.photobrowser.base.BaseTest
import com.noahutz.photobrowser.model.Album
import com.noahutz.photobrowser.repository.AlbumRepository
import com.noahutz.photobrowser.util.ResultOf
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Test

class AlbumListViewModelTest : BaseTest() {
    companion object {
        private val RESULT_ALBUMS = ResultOf.Success(
            listOf(
                Album(id = 3, title = "GHI", userId = 1),
                Album(id = 2, title = "DEF", userId = 1),
                Album(id = 1, title = "ABC", userId = 1)
            )
        )
        private val RESULT_ALBUMS_ALPHABETICAL = ResultOf.Success(
            listOf(
                Album(id = 1, title = "ABC", userId = 1),
                Album(id = 2, title = "DEF", userId = 1),
                Album(id = 3, title = "GHI", userId = 1)
            )
        )
        private val RESULT_FAILURE = ResultOf.Failure("some error message")
    }

    private val observer: Observer<ResultOf<List<Album>>> = spyk()
    private val repository: AlbumRepository = mockk()
    private val viewModel: AlbumListViewModel = AlbumListViewModel(repository)

    @Test
    fun `repository success should return result alphabetically`() {
        coEvery { repository.getAlbums() } returns MutableLiveData(RESULT_ALBUMS)

        viewModel.getAlbums().observeForever(observer)

        verify { observer.onChanged(RESULT_ALBUMS_ALPHABETICAL) }
        coVerify { repository.getAlbums() }
    }

    @Test
    fun `repository fail should return result failure`() {
        coEvery { repository.getAlbums() } returns MutableLiveData(RESULT_FAILURE)

        viewModel.getAlbums().observeForever(observer)

        verify { observer.onChanged(RESULT_FAILURE) }
        coVerify { repository.getAlbums() }
    }
}
