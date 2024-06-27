package com.sec.imslogger.ui.viewers.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.sec.imslogger.R
import com.sec.imslogger.databinding.AllMessageFragmentBinding
import com.sec.imslogger.ui.viewers.viewmodels.AllMessageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllMessageFragment : MessageFragment<AllMessageFragmentBinding>() {
    override val messageViewModel by viewModels<AllMessageViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = init(container, R.layout.all_message_fragment)

        binding.apply {
            lifecycleOwner = this@AllMessageFragment
            vm = messageViewModel
            initBindingView(allMessages, DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        return binding.root
    }
}
