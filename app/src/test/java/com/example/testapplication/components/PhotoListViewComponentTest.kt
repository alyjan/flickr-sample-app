package com.example.testapplication.components

import android.content.Context
import androidx.core.view.isVisible
import androidx.test.core.app.ApplicationProvider
import com.example.testapplication.view.components.PhotoListViewComponent
import com.example.testapplication.view.components.ViewPhotoListComponentState
import com.nhaarman.mockitokotlin2.mock
import junit.framework.TestCase.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PhotoListViewComponentTest {

    private lateinit var component: PhotoListViewComponent
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        component = PhotoListViewComponent(context)
    }

    @Test
    fun handleState_givenLoadingState_createsNewPhotoAdapter() {
        // Given
        component.actionHandler = mock()

        // When
        component.handleState(ViewPhotoListComponentState.Loading)

        // Then
        assertNotNull(component.photoAdapter)
    }

    @Test
    fun handleState_givenDisplayPhotosState_setsPhotoGridVisible() {
        // Given
        val displayPhotosState = ViewPhotoListComponentState.DisplayPhotos(
            photos = listOf(),
            resetData = false,
            photoGridVisible = true,
            loadingVisible = false
        )

        // When
        component.handleState(displayPhotosState)

        // Then
        assertTrue(component.binding.photoGrid.isVisible)
    }

    @Test
    fun handleState_givenDisplayPhotosState_setsLoadingInvisible() {
        // Given
        val displayPhotosState = ViewPhotoListComponentState.DisplayPhotos(
            photos = listOf(),
            resetData = false,
            photoGridVisible = true,
            loadingVisible = false
        )

        // When
        component.handleState(displayPhotosState)

        // Then
        assertFalse(component.binding.loading.isVisible)
    }
}
