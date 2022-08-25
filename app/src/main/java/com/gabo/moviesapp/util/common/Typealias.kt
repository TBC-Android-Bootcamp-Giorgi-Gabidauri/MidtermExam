package com.gabo.moviesapp.util.common

import android.view.LayoutInflater
import android.view.ViewGroup

typealias Inflater<T> = (LayoutInflater, ViewGroup?, Boolean) -> T
