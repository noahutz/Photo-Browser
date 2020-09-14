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
        private val RESULT_SUCCESS = ResultOf.Success(listOf<Album>())
        private val RESULT_FAILURE = ResultOf.Failure("some error message")
    }

    private val observer: Observer<ResultOf<List<Album>>> = spyk()
    private val repository: AlbumRepository = mockk()
    private val viewModel: AlbumListViewModel = AlbumListViewModel(repository)

    @Test
    fun `repository success should return result success`() {
        coEvery { repository.getAlbums() } returns MutableLiveData(RESULT_SUCCESS)

        viewModel.getAlbums().observeForever(observer)

        verify { observer.onChanged(RESULT_SUCCESS) }
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
