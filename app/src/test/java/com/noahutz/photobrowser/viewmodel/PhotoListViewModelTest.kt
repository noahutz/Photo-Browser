package com.noahutz.photobrowser.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.noahutz.photobrowser.base.BaseTest
import com.noahutz.photobrowser.model.Photo
import com.noahutz.photobrowser.repository.PhotoRepository
import com.noahutz.photobrowser.util.ResultOf
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Test

class PhotoListViewModelTest : BaseTest() {
    companion object {
        private val RESULT_SUCCESS = ResultOf.Success(listOf<Photo>())
        private val RESULT_FAILURE = ResultOf.Failure("some error message")
    }

    private val observer: Observer<ResultOf<List<Photo>>> = spyk()
    private val repository: PhotoRepository = mockk()
    private val viewModel: PhotoListViewModel = PhotoListViewModel(repository)

    @Test
    fun `repository success should return result success`() {
        coEvery { repository.getPhotos(any()) } returns MutableLiveData(RESULT_SUCCESS)

        viewModel.getPhotos(1).observeForever(observer)

        verify { observer.onChanged(RESULT_SUCCESS) }
        coVerify { repository.getPhotos(any()) }
    }

    @Test
    fun `repository fail should return result failure`() {
        coEvery { repository.getPhotos(any()) } returns MutableLiveData(RESULT_FAILURE)

        viewModel.getPhotos(1).observeForever(observer)

        verify { observer.onChanged(RESULT_FAILURE) }
        coVerify { repository.getPhotos(any()) }
    }
}
