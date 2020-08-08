package com.dan.jamiicfapp.ui.jcahome.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.data.db.entities.JcaEvent
import com.dan.jamiicfapp.databinding.RecyclerEventsBinding
import com.dan.jamiicfapp.ui.jcahome.listerner.EventRecyclerviewItemclicked


class EventsAdapter(
    private val eventRecyclerviewItemclicked: EventRecyclerviewItemclicked,
    private val eventlist: List<JcaEvent>,
    private val listerner: (JcaEvent) -> Unit
) :
    RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.recycler_events,
            parent,
            false
        )
    )

    override fun getItemCount() = eventlist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.eventsBinding.events = eventlist[position]

        holder.eventsBinding.imageEvents.setOnClickListener {
            eventRecyclerviewItemclicked.OnrecyclerviewItemClicked(
                holder.eventsBinding.imageEvents,
                eventlist[position]
            )
            listerner(eventlist[position])
        }
        holder.eventsBinding.textViewEventTitle.setOnClickListener {
            eventRecyclerviewItemclicked.OnrecyclerviewItemClicked(
                holder.eventsBinding.textViewEventTitle,
                eventlist[position]
            )
            listerner(eventlist[position])
        }
        holder.eventsBinding.textViewReadMore.setOnClickListener {
            eventRecyclerviewItemclicked.OnrecyclerviewItemClicked(
                holder.eventsBinding.textViewReadMore,
                eventlist[position]
            )
            listerner(eventlist[position])
        }

    }
    inner class ViewHolder(val eventsBinding: RecyclerEventsBinding) :
        RecyclerView.ViewHolder(eventsBinding.root)


}