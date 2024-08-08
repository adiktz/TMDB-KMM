package ravi.gaurav.learning.tmdb.navigation

import platform.Foundation.NSUUID

actual fun randomUUID(): String = NSUUID().UUIDString()