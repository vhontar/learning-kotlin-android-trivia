/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentGameWonBinding
import kotlinx.android.synthetic.main.fragment_game_over.*


class GameWonFragment : Fragment() {
    private lateinit var viewDataBinding: FragmentGameWonBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_game_won, container, false)
        viewDataBinding.apply {
            nextMatchButton.setOnClickListener {
                it.findNavController().navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
            }
        }

        setHasOptionsMenu(true)

        return viewDataBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.winner_menu, menu)

        if (getShareIntent().resolveActivity(activity!!.packageManager) == null) {
            menu?.findItem(R.id.share)?.isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId) {
            R.id.share -> startSharing()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startSharing() {
        startActivity(getShareIntent())
    }

    private fun getShareIntent(): Intent {
        val args = GameWonFragmentArgs.fromBundle(arguments!!)
        return ShareCompat.IntentBuilder.from(activity)
                .setText(getString(R.string.share_success_text, args.numQuestions, args.correctAnswers))
                .setType("text/plain")
                .intent
    }
}
