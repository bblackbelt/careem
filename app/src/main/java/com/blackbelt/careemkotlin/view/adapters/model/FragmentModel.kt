package com.blackbelt.careemkotlin.view.adapters.model

import android.os.Bundle
import android.support.v4.app.Fragment

data class FragmentModel(val title: String, val fragment: Class<out Fragment>, val bundle: Bundle)
