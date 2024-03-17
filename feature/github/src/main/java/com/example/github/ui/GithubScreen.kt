package com.example.github.ui

import androidx.lifecycle.ViewModel
import com.apollographql.apollo3.ApolloClient
import javax.inject.Inject


class GithubViewModel @Inject constructor(
    private val apolloClient: ApolloClient
): ViewModel() {}