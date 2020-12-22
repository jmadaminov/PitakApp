package com.badcompany.remote.model


data class ReqUpdateProfileInfo(                                val name: String,
                                val surname: String,
                                val image: IdNameBody? = null)