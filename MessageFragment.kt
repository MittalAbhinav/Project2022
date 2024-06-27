/*
 * Copyright (c) 2021. Samsung Electronics Co., Ltd.
 * @author YOUNGJIN JO (yjspecial.jo@samsung.com) IMS Platform team / CP Solution R&D
 *
 * All right reserved. This software is the confidential and proprietary
 * information of Samsung Electronics ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you
 * entered into with Samsung Electronics.
 */

package com.sec.imslogger.ui.viewers.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.MenuProvider
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import com.sec.imslogger.BR
import com.sec.imslogger.R
import com.sec.imslogger.constant.SimCard
import com.sec.imslogger.constant.SimCard.SLOT_1
import com.sec.imslogger.constant.SimCard.SLOT_2
import com.sec.imslogger.interfaces.model.Message
import com.sec.imslogger.ui.viewers.adapters.DataBindingAdapter
import com.sec.imslogger.ui.viewers.adapters.MessageAdapter
import com.sec.imslogger.ui.viewers.viewmodels.MessageViewModel

abstract class MessageFragment<T : ViewDataBinding> : DataBindingFragment<Message, T>() {
    abstract val messageViewModel: MessageViewModel
    private lateinit var filter: SearchView
    override val dataBindingAdapter: DataBindingAdapter<Message, T> = object : MessageAdapter<T>(BR.message) {}

    private fun onItemFilterSubmit(filter: String) {
        messageViewModel.applyFilter(filter)
    }

    private fun onItemClear() {
        messageViewModel.clearAllMessages()
    }

    private fun getSlotIcon(slotId: Int): Drawable? {
        val drawableId = if (slotId == SLOT_1) {
            R.drawable.ic_slot_1
        } else {
            R.drawable.ic_slot_2
        }

        return ResourcesCompat.getDrawable(resources, drawableId, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.message_view_menu, menu)

                    menu.findItem(R.id.slot_id)?.apply {
                        if (!SimCard.isMultiSim()) {
                            isVisible = false
                        } else {
                            icon = getSlotIcon(messageViewModel.slotId)
                        }
                    }

                    menu.findItem(R.id.filter)?.apply {
                        setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                                messageViewModel.resetFilter()
                                return true
                            }

                            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                                return true
                            }
                        })

                        filter = actionView as SearchView
                    }

                    filter.apply {
                        queryHint = context.getString(R.string.filter_hint)
                        setOnQueryTextListener(queryTextListener)
                    }
                }

                override fun onMenuItemSelected(item: MenuItem): Boolean {
                    when (item.itemId) {
                        R.id.clear -> {
                            if (filter.isIconified) {
                                onItemClear()
                            } else {
                                filter.setQuery("", false)
                                messageViewModel.resetFilter()
                            }
                        }

                        R.id.slot_id -> {
                            filter.setQuery("", false)
                            if (messageViewModel.slotId == SLOT_1) {
                                messageViewModel.slotId = SLOT_2
                            } else {
                                messageViewModel.slotId = SLOT_1
                            }

                            item.icon = getSlotIcon(messageViewModel.slotId)
                        }

                        else -> {
                            return false
                        }
                    }

                    return true
                }
            },
            viewLifecycleOwner,
            Lifecycle.State.RESUMED,
        )
    }

    private val queryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            onItemFilterSubmit(query)
            return true
        }

        override fun onQueryTextChange(newText: String?) = true
    }
}
