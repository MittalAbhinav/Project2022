package com.sec.imslogger.ui.viewers.viewmodels

import com.sec.imslogger.constant.imsdm.MessageType
import com.sec.imslogger.interfaces.model.IRepository
import com.sec.imslogger.interfaces.model.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllMessageViewModel @Inject constructor(repository: IRepository<Message, MessageType>) : MessageViewModel(repository) {
    override val type = MessageType.AllMessages
}
