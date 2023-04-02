package co.harismiftahulhudha.imagecalculator.home.ui_home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.harismiftahulhudha.imagecalculator.home.domain.models.ImageCalculatorModel
import co.harismiftahulhudha.imagecalculator.home.ui_home.databinding.ItemImageCalculatorBinding

class ImageCalculatorAdapter: ListAdapter<ImageCalculatorModel, ImageCalculatorAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemImageCalculatorBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { data ->
            holder.bind(data)
        }
    }

    inner class ViewHolder(private val binding: ItemImageCalculatorBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ImageCalculatorModel) {
            binding.apply {
                txtInput.text = data.input
                txtResult.text = data.result
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ImageCalculatorModel>() {
        override fun areItemsTheSame(
            oldItem: ImageCalculatorModel,
            newItem: ImageCalculatorModel
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ImageCalculatorModel,
            newItem: ImageCalculatorModel
        ) = oldItem == newItem
    }
}