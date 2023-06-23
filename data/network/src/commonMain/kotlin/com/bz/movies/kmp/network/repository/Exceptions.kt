package com.bz.movies.kmp.network.repository

class EmptyBodyException : Exception("Empty body")
class HttpException(message: String) : Exception(message)
class NoInternetException : Exception("No Internet Connection")
