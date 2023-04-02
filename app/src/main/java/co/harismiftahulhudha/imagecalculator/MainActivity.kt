package co.harismiftahulhudha.imagecalculator

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import co.harismiftahulhudha.imagecalculator.core.base.BaseActivity
import co.harismiftahulhudha.imagecalculator.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponents()
    }

    override fun initComponents() {
        binding.apply {
            navHostFragment = supportFragmentManager.findFragmentById(R.id.mainHostFragment) as NavHostFragment
            navController = navHostFragment.findNavController()
        }
    }
}