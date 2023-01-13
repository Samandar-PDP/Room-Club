package uz.digital.clubdatabase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.digital.clubdatabase.databinding.ClubLayoutBinding
import uz.digital.clubdatabase.model.Club
import uz.digital.clubdatabase.util.toBitmap

class ClubAdapter : RecyclerView.Adapter<ClubAdapter.UserViewHolder>() {
    lateinit var onClick: (Club) -> Unit
    private var userList = mutableListOf<Club>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ClubLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size

    inner class UserViewHolder(
        private val binding: ClubLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(club: Club) {
            with(binding) {
                name.text = club.name
                imageView.setImageBitmap(club.logo.toBitmap())
            }
            itemView.setOnClickListener {
                onClick(club)
            }
        }
    }

    fun setList(userList: MutableList<Club>) {
        this.userList = userList
        notifyDataSetChanged()
    }
}