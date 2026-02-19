package com.myna.bell.util

import java.nio.ByteBuffer
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import kotlin.math.pow

object TotpGenerator {

    private const val ALGORITHM = "HmacSHA1"
    private const val DIGITS = 6
    private const val PERIOD = 30L

    fun generateTOTP(secret: ByteArray, time: Long = System.currentTimeMillis() / 1000): String {
        val counter = time / PERIOD
        return generateHOTP(secret, counter)
    }

    private fun generateHOTP(secret: ByteArray, counter: Long): String {
        val keySpec = SecretKeySpec(secret, ALGORITHM)
        val mac = Mac.getInstance(ALGORITHM)
        mac.init(keySpec)

        val buffer = ByteBuffer.allocate(8)
        buffer.putLong(counter)
        val hash = mac.doFinal(buffer.array())

        val offset = hash[hash.size - 1].toInt() and 0xf
        val binary = ((hash[offset].toInt() and 0x7f) shl 24) or
                ((hash[offset + 1].toInt() and 0xff) shl 16) or
                ((hash[offset + 2].toInt() and 0xff) shl 8) or
                (hash[offset + 3].toInt() and 0xff)

        val otp = binary % 10.0.pow(DIGITS).toInt()
        return otp.toString().padStart(DIGITS, '0')
    }
}
