package com.project.dgr

import org.json.JSONArray
import org.json.JSONObject

object ConvertJsonUtil {
    private var pureData: String? = null
    private var pureColumnNameList: String? = null
    private val tableDelimiter = "\u0014\u0019"
    private val rowDelimiter = "\u0019"
    private val delimiter = "\u0018"
    private val columnTableDelimiter = "\u0014\u0018"
    private val columnDelimiter = "\u0018"

    fun convertJson(pureData: String, pureColumnNameList: String, convertJsonListener: ConvertJsonListener) {
        ConvertJsonUtil.pureData = pureData
        ConvertJsonUtil.pureColumnNameList = pureColumnNameList

        val splitPureData = pureData.split(tableDelimiter).toTypedArray()
        val splitPureColumnNameList = pureColumnNameList.split(columnTableDelimiter).toTypedArray()
        val jsonArray = JSONArray()

        for (i in splitPureData.indices) {
            val pieceRowColumnNameList = splitPureColumnNameList[i].split(columnDelimiter).toTypedArray()

            val jsonArrayIndex = JSONArray()
            val pieceRowDataList = splitPureData[i].split(rowDelimiter).toTypedArray()

            for (row in pieceRowDataList) {
                if (row.isNotEmpty()) {
                    val jsonObject = JSONObject()
                    val pieceDataList = row.split(delimiter).toTypedArray()
                    try {
                        if (pieceRowColumnNameList.size == pieceDataList.size) {
                            for (j in pieceRowColumnNameList.indices) {
                                jsonObject.put(pieceRowColumnNameList[j], pieceDataList[j])
                            }
                        }
                    } catch (e: Exception) {
                        convertJsonListener.error(e.toString())
                    }
                    jsonArrayIndex.put(jsonObject)
                } else {
                    convertJsonListener.error("")
                }
            }
            jsonArray.put(jsonArrayIndex)
        }
        convertJsonListener.success(jsonArray)
    }

    interface ConvertJsonListener {
        fun success(jsonArray: JSONArray)
        fun error(error: String)
    }
}