package uz.digital.clubdatabase.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import uz.digital.clubdatabase.R
import uz.digital.clubdatabase.database.ClubDatabase
import uz.digital.clubdatabase.databinding.FragmentAddUpdateBinding
import uz.digital.clubdatabase.databinding.FragmentClubListBinding
import uz.digital.clubdatabase.model.Club
import uz.digital.clubdatabase.util.toBitmap
import uz.digital.clubdatabase.util.toByteArray


class AddUpdateFragment : Fragment(R.layout.fragment_add_update) {
    private var club: Club? = null
    private val array = arrayOf("APL", "Laliga", "A Seria", "Bundes Liga", "Liga 1")
    private var _binding: FragmentAddUpdateBinding? = null
    private val binding get() = _binding!!
    private val database by lazy { ClubDatabase(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        club = arguments?.getParcelable("club")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddUpdateBinding.bind(view)
        if (club != null) {
            // TODO: update
            binding.apply {
                toolBar.title = "Update"
                btn.text = "Update Club"
                rating.setText(club?.rating.toString())
                imageView.setImageBitmap(club?.logo?.toBitmap())
                name.setText(club?.name)
                league.setText(club?.league)
            }
        } else {
            // TODO: add
            binding.apply {
                toolBar.title = "Add new"
                btn.text = "Create Club"
            }
        }
        binding.btn.setOnClickListener {
            val name = binding.name.text.toString().trim()
            val rating = binding.rating.text.toString().trim().toInt()
            val league = binding.league.text.toString().trim()
            val image = binding.imageView
            if (club != null) {
                binding.apply {
                    database.dao.updateClub(
                        Club(
                            club?.id!!,
                            name,
                            rating,
                            league,
                            image.toByteArray()
                        )
                    )
                    Toast.makeText(requireContext(), "Updated", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_addUpdateFragment_to_clubListFragment)
                }
            } else {
                database.dao.saveClub(
                    Club(
                        name = name,
                        league = league,
                        rating = rating,
                        logo = image.toByteArray()
                    )
                )
                Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
        binding.imageView.setOnClickListener {
            binding.imageView.setBackgroundResource(0)
            imageLauncher.launch("image/*")
        }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, array)
        binding.league.setAdapter(adapter)
        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private val imageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let { uri ->
            binding.imageView.setImageURI(uri)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}