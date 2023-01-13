package uz.digital.clubdatabase.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import uz.digital.clubdatabase.R
import uz.digital.clubdatabase.adapter.ClubAdapter
import uz.digital.clubdatabase.database.ClubDatabase
import uz.digital.clubdatabase.databinding.FragmentClubListBinding

class ClubListFragment : Fragment(R.layout.fragment_club_list) {
    private var _binding: FragmentClubListBinding? = null
    private val binding get() = _binding!!
    private val database by lazy { ClubDatabase(requireContext()) }
    private val adapter by lazy { ClubAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentClubListBinding.bind(view)
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_clubListFragment_to_addUpdateFragment)
        }
        binding.rv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ClubListFragment.adapter
        }
        adapter.setList(database.dao.getAllClubs().toMutableList())
        adapter.onClick = {
            val club = bundleOf("club" to it)
            findNavController().navigate(R.id.action_clubListFragment_to_detailFragment, club)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}