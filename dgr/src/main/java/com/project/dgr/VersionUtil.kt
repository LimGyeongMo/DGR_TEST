package com.project.dgr

import android.content.Context

public object VersionUtil {
    private const val VERSION_ERROR: Int = -1
    private const val VERSION_LATEST: Int = 0
    private const val VERSION_LOW_MAJOR: Int = 1
    private const val VERSION_LOW_MINOR: Int = 2
    private const val VERSION_LOW_PATCH: Int = 3

    public fun getVersionName(context: Context): String {
        try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            return packageInfo.versionName
        } catch (e: Exception) {
            return ""
        }

    }

    public fun getVersionCode(context: Context): Int {
        try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            return packageInfo.versionCode
        } catch (e: Exception) {
            return 0
        }
    }

    // 버전명 비교(클라이언트 버전이 서버버전보다 더 크더라도 최신버전으로 인식)
    public fun checkVersionName(context: Context, serverVersion: String): Int {
        val clientVersion: String

        try {
            clientVersion = context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: Exception) {
            return VERSION_ERROR
        }

        val arrServerVersion: Array<String>
        val arrClientVersion: Array<String>

        try {
            arrServerVersion = serverVersion.split("\\.".toRegex()).toTypedArray()
            arrClientVersion = clientVersion.split("\\.".toRegex()).toTypedArray()
        } catch (e: Exception) {
            return VERSION_ERROR
        }

        if ((arrServerVersion.size < 3) || (arrClientVersion.size < 3)) {
            return VERSION_ERROR
        }

        val serverMajor: Int
        val serverMinor: Int
        val serverPatch: Int
        val clientMajor: Int
        val clientMinor: Int
        val clientPatch: Int

        try {
            serverMajor = arrServerVersion[0].toInt()
            serverMinor = arrServerVersion[1].toInt()
            serverPatch = arrServerVersion[2].toInt()

            clientMajor = arrClientVersion[0].toInt()
            clientMinor = arrClientVersion[1].toInt()
            clientPatch = arrClientVersion[2].toInt()
        } catch (e: Exception) {
            return VERSION_ERROR
        }

        if (serverMajor > clientMajor) {
            return VERSION_LOW_MAJOR
        } else if (serverMajor == clientMajor) {
            if (serverMinor > clientMinor) {
                return VERSION_LOW_MINOR
            } else if (serverMinor == clientMinor) {
                if (serverPatch > clientPatch) {
                    return VERSION_LOW_PATCH
                }
            }
        }

        return VERSION_LATEST
    }
}