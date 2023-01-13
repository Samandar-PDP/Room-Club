package uz.digital.clubdatabase.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import uz.digital.clubdatabase.R
import uz.digital.clubdatabase.database.ClubDatabase
import uz.digital.clubdatabase.databinding.FragmentClubListBinding
import uz.digital.clubdatabase.databinding.FragmentDetailBinding
import uz.digital.clubdatabase.model.Club
import uz.digital.clubdatabase.util.toBitmap

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val database by lazy { ClubDatabase(requireContext()) }
    private lateinit var club: Club

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        club = arguments?.getParcelable("club")!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)

        binding.apply {
            imageView.setImageBitmap(club.logo.toBitmap())
            textData.text = "${club.name}\n${club.league}"
            btnDelete.setOnClickListener {
                database.dao.deleteClub(club)
                findNavController().popBackStack()
                Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
            }
            btnUpdate.setOnClickListener {
                val bundle = bundleOf(
                    "club" to Club(
                        name = club.name,
                        rating = club.rating,
                        league = club.league,
                        logo = byteArrayOf()
                    )
                )
                findNavController().navigate(
                    R.id.action_detailFragment_to_addUpdateFragment,
                    bundle
                )
            }
        }
        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}