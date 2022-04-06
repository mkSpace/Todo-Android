package com.funin.todo.data.db

import androidx.room.Dao
import com.funin.todo.data.vo.User

@Dao
abstract class UserDao : BaseDao<User>()