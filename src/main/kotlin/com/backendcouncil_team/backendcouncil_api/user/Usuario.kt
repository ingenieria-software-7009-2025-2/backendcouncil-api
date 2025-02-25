package com.backendcouncil_team.backendcouncil_api.user

/**
 * User.
 *
 * This class representes a user.
 *
 * @author The Backend Council
 *
 * @property name of the user.
 * @property mail of the user.
 * @property password of the user*.
 * @property token of the user.
 * 
 * @constructor Creates a user
 */
class Usuario(
    val name : String = "",
    val mail : String = "",
    val password : String = "",
    val token : String = "",
) {}