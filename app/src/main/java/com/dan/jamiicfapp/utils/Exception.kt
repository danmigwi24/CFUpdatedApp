package com.dan.jamiicfapp.utils

import java.io.IOException

class APIException(string: String) : IOException(string)

class NoInternetException(string: String) : IOException(string)
