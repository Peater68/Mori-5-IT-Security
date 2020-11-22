package hu.bme.caffshare.ui.profile.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import hu.bme.caffshare.ui.profile.boughtcafflist.BoughtCaffListFragment
import hu.bme.caffshare.ui.profile.uploadedcafflist.UploadedCaffListFragment

class ProfileListsAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(
    fragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    private val fragments = listOf<Fragment>(UploadedCaffListFragment(), BoughtCaffListFragment())

    override fun getCount() = fragments.size

    override fun getItem(position: Int) = fragments[position]
}