package com.gabo.moviesapp.other.common

import android.view.LayoutInflater
import android.view.ViewGroup

typealias Inflater<T> = (LayoutInflater, ViewGroup?, Boolean) -> T
