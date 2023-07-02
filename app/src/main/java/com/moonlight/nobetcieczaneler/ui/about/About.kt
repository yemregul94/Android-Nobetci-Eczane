package com.moonlight.nobetcieczaneler.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.moonlight.nobetcieczaneler.R
import com.moonlight.nobetcieczaneler.databinding.FragmentAboutBinding
import com.moonlight.nobetcieczaneler.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class About : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AboutViewModel by viewModels()
    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.layoutLinkedin.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/yemregul94/"))
            startActivity(intent)
        }

        binding.layoutMail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("yemregul94@gmail.com"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Nöbetçi Eczaneler")
            startActivity(intent)
        }

        binding.layoutGithub.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/yemregul94"))
            startActivity(intent)
        }

        binding.ibShare.setOnClickListener {
            ShareCompat.IntentBuilder.from(requireActivity())
                .setType("text/plain")
                .setChooserTitle(getString(R.string.share_with))
                .setText("https://play.google.com/store/apps/details?id=com.moonlight.nobetcieczaneler")
                .startChooser();
        }

        binding.layoutApi.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nosyapi.com/"))
            startActivity(intent)
        }

        return binding.root
    }


}