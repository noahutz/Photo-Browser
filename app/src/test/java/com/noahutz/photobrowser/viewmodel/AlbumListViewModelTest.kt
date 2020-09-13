package com.noahutz.photobrowser.viewmodel

import androidx.lifecycle.Observer
import com.noahutz.photobrowser.model.Album
import com.noahutz.photobrowser.repository.AlbumRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Test

class AlbumListViewModelTest : BaseViewModelTest() {
    private val observer: Observer<List<Album>> = spyk()
    private val repository: AlbumRepository = mockk()
    private val viewModel: AlbumListViewModel = AlbumListViewModel(repository)

    @Test
    fun `getAlbums return list from repository`() {
        val albumList = (0..10).map { id -> Album(id = id, title = "Title $id", userId = 1) }
        coEvery { repository.getAlbums() } returns albumList

        viewModel.getAlbums().observeForever(observer)

        verify { observer.onChanged(albumList) }
        coVerify { repository.getAlbums() }
    }
}