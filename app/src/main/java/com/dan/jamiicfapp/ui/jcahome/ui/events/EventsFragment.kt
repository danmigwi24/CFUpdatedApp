package com.dan.jamiicfapp.ui.jcahome.ui.events

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.data.db.entities.JcaEvent
import com.dan.jamiicfapp.databinding.FragmentEventsBinding
import com.dan.jamiicfapp.ui.jcahome.adapter.EventsAdapter
import com.dan.jamiicfapp.ui.jcahome.listerner.EventRecyclerviewItemclicked
import com.dan.jamiicfapp.ui.jcahome.ui.events.eventveiwmodel.EventsViewModel
import com.dan.jamiicfapp.ui.jcahome.ui.events.eventveiwmodel.EventsViewModelFactory
import com.dan.jamiicfapp.ui.jcahome.ui.home.MoreDetailsPWDSActivity
import com.dan.jamiicfapp.utils.Coroutines
import com.dan.jamiicfapp.utils.toast
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class EventsFragment : Fragment(), KodeinAware, EventRecyclerviewItemclicked {

    override val kodein by kodein()
    val factory by instance<EventsViewModelFactory>()

    private lateinit var eventsViewModel: EventsViewModel
    private lateinit var eventsAdapter: EventsAdapter
    private lateinit var binding: FragmentEventsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        eventsViewModel =
            ViewModelProvider(this, factory).get(EventsViewModel::class.java)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_events,
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Coroutines.main {
            val pwd = eventsViewModel.getEventsInVM.await()
            pwd.observe(requireActivity(), Observer { events ->
                eventsAdapter = EventsAdapter(this@EventsFragment, events) {
                    val intent = Intent(requireContext(), MoreAboutEventsActivity::class.java)
                    intent.putExtra(INTENT_PARCABLE_EVENTS, it)
                    startActivity(intent)
                }
                binding.recyclerViewEvents.apply {
                    layoutManager = LinearLayoutManager(activity)
                    setHasFixedSize(true)
                    adapter = eventsAdapter
                }

            })
        }
    }

    override fun OnrecyclerviewItemClicked(view: View, jcaEvent: JcaEvent) {
        when (view.id) {
            R.id.textViewReadMore->{
                context?.toast("This is event")
                eventsAdapter
            }
            R.id.imageEvents->{
                eventsAdapter
            }
        }

    }

    companion object {
        const val INTENT_PARCABLE_EVENTS = "jcaEvents"
    }
}