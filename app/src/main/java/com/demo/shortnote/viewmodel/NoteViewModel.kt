package com.demo.shortnote.viewmodel

import androidx.lifecycle.*
import com.demo.shortnote.helper.EventMessage
import com.demo.shortnote.model.MyNote
import com.demo.shortnote.repository.MyNoteRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*

class NoteViewModel(private val repository: MyNoteRepository): ViewModel(){
    private val statusMessage =  MutableLiveData<EventMessage<String>>()
    val message: LiveData<EventMessage<String>> get() = statusMessage


    val inputTitle = MutableLiveData<String>()
    val inputDetails = MutableLiveData<String>()

    val saveOrUpdateButtonText = MutableLiveData<String>()
    val deleteAllButtonText = MutableLiveData<String>()

    private var isUpdateOrDelete = false
    private lateinit var myNoteToDeleteOrUpdate: MyNote

    init {
        saveOrUpdateButtonText.value = "Save"
        deleteAllButtonText.value = "Delete All"
    }

    fun saveOrUpdate() {
        if (inputTitle.value == null) {
            statusMessage.value = EventMessage("Enter note title")
        } else if (inputDetails.value == null) {
            statusMessage.value = EventMessage("Enter note details")
        } else{

            if (isUpdateOrDelete){
                myNoteToDeleteOrUpdate.title = inputTitle.value!!
                myNoteToDeleteOrUpdate.details = inputDetails.value!!
                updateMyNote(myNoteToDeleteOrUpdate)

            }else{
                val title= inputTitle.value!!
                val details = inputDetails.value!!
                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                val currentDate = sdf.format(Date())
                insertSubscriber(MyNote(0,title, details,currentDate))

                inputTitle.value = ""
                inputDetails.value = ""
            }
        }

    }

    private fun insertSubscriber(myNote: MyNote) = viewModelScope.launch {
        val newRowId = repository.insert(myNote)
        if (newRowId > -1) {
            statusMessage.value = EventMessage("Successfully subscriber created -  $newRowId")
        } else {
            statusMessage.value = EventMessage("Error Occurred")
        }
    }

    fun getSaveMyNotes() = liveData {
        repository.myNotes.collect {
            emit(it)
        }
    }

    private fun clearAll() = viewModelScope.launch {
        val noOfRowOfRows = repository.deleteAll()
        if (noOfRowOfRows > 0) {
            statusMessage.value = EventMessage("Deleted successfully")
        }else{
            statusMessage.value = EventMessage("Delete operation failed!")
        }
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete){
            deleteMyNote(myNoteToDeleteOrUpdate)
        }else{
            clearAll()
        }
    }

    fun initUpdateOrDelete(myNote: MyNote){
        inputTitle.value = myNote.title
        inputDetails.value = myNote.details
        isUpdateOrDelete = true
        myNoteToDeleteOrUpdate = myNote

        saveOrUpdateButtonText.value = "Update"
        deleteAllButtonText.value = "Delete"

    }

    private fun updateMyNote(myNote: MyNote) = viewModelScope.launch{
        var res = repository.update(myNote)
        if (res > 0) {
            inputTitle.value = ""
            inputDetails.value = ""
            isUpdateOrDelete = false

            saveOrUpdateButtonText.value = "Save"
            deleteAllButtonText.value = "Delete All"

            statusMessage.value = EventMessage("Updated successfully")

        }else{
            statusMessage.value = EventMessage("Update operation failed")
        }

    }

    private fun deleteMyNote(myNote: MyNote) = viewModelScope.launch {
        var res = repository.delete(myNote)
        if (res > 0) {
            inputTitle.value = ""
            inputDetails.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            deleteAllButtonText.value = "Delete All"
            statusMessage.value = EventMessage("Deleted successfully")
        } else {
            statusMessage.value = EventMessage("Delete operation failed")
        }
    }


}