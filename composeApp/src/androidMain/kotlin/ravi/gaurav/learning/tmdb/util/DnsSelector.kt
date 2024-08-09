package ravi.gaurav.learning.tmdb.util

import okhttp3.Dns
import java.net.Inet4Address
import java.net.InetAddress


class DnsSelector : Dns {
    override fun lookup(hostname: String): List<InetAddress> {
        return Dns.SYSTEM.lookup(hostname).sortedBy { Inet4Address::class.isInstance(it) }
    }
}