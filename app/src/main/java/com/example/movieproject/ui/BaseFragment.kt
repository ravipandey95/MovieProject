package com.example.movieproject.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.movieproject.R
import com.example.movieproject.utils.DataState
import com.example.movieproject.utils.hideLoading
import com.example.movieproject.utils.showLoading

abstract class BaseFragment<VDB: ViewDataBinding> : Fragment() {
    @get:LayoutRes
    abstract val layoutResId: Int
    protected var binding: VDB ? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,layoutResId,container,false/*,bindingComponent*/)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let {
            it.lifecycleOwner = this
        }
    }

    val observer = Observer<DataState<*>> {
        when (it) {
            is DataState.Error<*> -> {
                // could handle various usecases of Error based on the Status code we set in the Datastate class . But here just simply showing one Toast error.
                hideLoading()
                Toast.makeText(requireActivity(), "Something went wrong or else please check internet", Toast.LENGTH_LONG).show()
            }
            is DataState.Loading -> {
                showLoading()
            }
            is DataState.Success<*> -> {
                hideLoading()
                receivedResponse(it.data)
            }
            DataState.Default ->{
                hideLoading()
            }
        }
    }
    abstract fun receivedResponse(item: Any?)
}