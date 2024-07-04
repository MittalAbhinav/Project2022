package com.sec.imslogger.ui.viewers.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.sec.imslogger.R
import com.sec.imslogger.constant.SimCard

class McsDbFragment : Fragment() {
    var slotId =0

    private fun getSlotIcon(slotId: Int): Drawable? {
        val drawableId = if (slotId == SimCard.SLOT_1) {
            R.drawable.ic_slot_1
        } else {
            R.drawable.ic_slot_2
        }

        return ResourcesCompat.getDrawable(resources, drawableId, null)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.mcs_db_fragment, container, false)

        requireActivity().addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.message_view_menu, menu)

                    menu.findItem(R.id.slot_id)?.apply {
                        if (!SimCard.isMultiSim()) {
                            isVisible = true
                        } else {
                            icon = getSlotIcon(slotId)
                        }
                    }

                    menu.findItem(R.id.filter)?.apply {
                        setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
//                                messageViewModel.resetFilter()
                                return true
                            }

                            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                                return true
                            }
                        })

                    }
                }
                override fun onMenuItemSelected(item: MenuItem): Boolean {
                    when (item.itemId) {
                        R.id.clear -> {

                        }

                        R.id.slot_id -> {
                            if (slotId == SimCard.SLOT_1) {
                                slotId = SimCard.SLOT_2
                            } else {
                                slotId = SimCard.SLOT_1
                            }

                            item.icon = getSlotIcon(slotId)
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

        // Set up dropdown buttons and table visibility logic
        setupDropdowns(view)

        return view



    }


    private fun setupDropdowns(view: View) {
        val dropdownButton1: Button = view.findViewById(R.id.dropdown_button1)
        val table1: TableLayout = view.findViewById(R.id.table1)
        dropdownButton1.setOnClickListener {
            toggleTableVisibility(table1)
        }

        val dropdownButton2: Button = view.findViewById(R.id.dropdown_button2)
        val table2: TableLayout = view.findViewById(R.id.table2)
        dropdownButton2.setOnClickListener {
            toggleTableVisibility(table2)
        }

        val dropdownButton3: Button = view.findViewById(R.id.dropdown_button3)
        val table3: TableLayout = view.findViewById(R.id.table3)
        dropdownButton3.setOnClickListener {
            toggleTableVisibility(table3)
        }

        val dropdownButton4: Button = view.findViewById(R.id.dropdown_button4)
        val table4: TableLayout = view.findViewById(R.id.table4)
        dropdownButton4.setOnClickListener {
            toggleTableVisibility(table4)
        }
    }

    private fun toggleTableVisibility(table: TableLayout) {
        if (table.visibility == View.GONE) {
            table.visibility = View.VISIBLE
        } else {
            table.visibility = View.GONE
        }
    }
}
