package com.noahutz.photobrowser.viewmodel

import androidx.lifecycle.Observer
import com.noahutz.photobrowser.model.Photo
import com.noahutz.photobrowser.repository.PhotoRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Test

class PhotoListViewModelTest : BaseViewModelTest() {

    private val observer: Observer<List<Photo>> = spyk()
    private val repository: PhotoRepository = mockk()
    private val viewModel: PhotoListViewModel = PhotoListViewModel(repository)

    @Test
    fun `getPhotos return list from repository`() {
        val albumId = 5
        val photoList = (0..10).map { id ->
            Photo(
                id = id,
                albumId = albumId,
                thumbnailUrl = "Thumbnail $id",
                title = "Title $id",
                url = "Url $id"
            )
        }
        coEvery { repository.getPhotos(any()) } returns photoList

        viewModel.getPhotos(albumId).observeForever(observer)

        verify { observer.onChanged(photoList) }
        coVerify { repository.getPhotos(albumId) }
    }
}
