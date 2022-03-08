package fr.paug.androidmakers.util

import fr.androidmakers.store.model.Partners
import fr.androidmakers.store.model.Ribbon
import fr.androidmakers.store.model.SocialNetworkHandle
import java.util.*

object MapUtil {

    @JvmStatic
    fun Map<*, *>.getInt(key: String, defaultValue: Int): Int {
        return (this[key] as? Number)?.toInt() ?: defaultValue
    }

    @JvmStatic
    fun Map<*, *>.getString(key: String): String? {
        return this[key] as? String
    }

    @JvmStatic
    fun getSocialItems(map: Map<Any?, Any?>, key: String): List<SocialNetworkHandle>? {
        return (map[key] as? List<*>)
                ?.let { list ->
                    val socialNetworkHandles: MutableList<SocialNetworkHandle> = ArrayList()
                    list.forEach { item ->
                        (item as? Map<*, *>)?.apply {
                            val name = this.getString("name")
                            val link = this.getString("link")
                            if (name.isNullOrBlank().not() && link.isNullOrBlank().not()) {
                                socialNetworkHandles.add(SocialNetworkHandle(name, link))
                            }
                        }
                    }
                    return socialNetworkHandles.takeIf { it.size > 0 }
                }
    }

    @JvmStatic
    fun getRibbonItems(map: Map<*, *>, key: String): List<Ribbon>? {
        return (map[key] as? List<*>)
                ?.let { list ->
                    val ribbonList: MutableList<Ribbon> = ArrayList()
                    list.forEach { item ->
                        (item as? Map<*, *>)?.apply {
                            val name = this.getString("abbr")
                            val title = this.getString("title")
                            val link = this.getString("url")
                            if (name.isNullOrBlank().not() && link.isNullOrBlank().not()) {
                                ribbonList.add(Ribbon(name, title, link))
                            }
                        }
                    }
                    return ribbonList.takeIf { it.size > 0 }
                }
    }

    @JvmStatic
    fun getPartnerList(map: Map<*, *>, key: String): List<Partners>? {
        return (map[key] as? List<*>)
                ?.let { list ->
                    val partnersList: MutableList<Partners> = ArrayList()
                    list.forEach { item ->
                        (item as? Map<*, *>)?.apply {
                            val name = this.getString("name")
                            val link = this.getString("link")
                            val imageUrl = this.getString("imageUrl")
                            val description = this.getString("description")
                            if (name.isNullOrBlank().not() && imageUrl.isNullOrBlank().not()) {
                                partnersList.add(Partners(name, imageUrl, link, description))
                            }
                        }
                    }
                    return partnersList.takeIf { it.size > 0 }
                }
    }

    @JvmStatic
    fun getIntArray(map: Map<*, *>, key: String): IntArray? {
        return (map[key] as? List<*>)
                ?.let { list ->
                    val result = IntArray(list.size)
                    list.forEachIndexed { index, item ->
                        val value = (item as? Number)?.toInt() ?: -1
                        if (value < 0) {
                            return null
                        }
                        result[index] = value
                    }
                    return result
                }
    }

}