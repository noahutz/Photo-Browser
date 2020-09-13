package com.noahutz.photobrowser.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.noahutz.photobrowser.model.Album
import com.noahutz.photobrowser.repository.AlbumRepository
import com.noahutz.photobrowser.util.CoroutineTestRule
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

class AlbumListViewModelTest {
    @get:Rule
    val instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private val observer: Observer<List<Album>> = spyk()
    private val repository: AlbumRepository = mockk()
    private val viewModel: AlbumListViewModel = AlbumListViewModel(repository)

    @Test
    fun `getAlbums return list from repository`() {
        val albumList = (0..10).map { id -> Album(id = id, title = "Title $id", userId = 1) }
        coEvery { repository.loadAlbums() } returns albumList

        viewModel.getAlbums().observeForever(observer)

        verify { observer.onChanged(albumList) }
    }
}