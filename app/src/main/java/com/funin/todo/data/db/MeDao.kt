package com.funin.todo.data.db

import androidx.room.Dao
import com.funin.todo.data.vo.Me

@Dao
abstract class MeDao : BaseDao<Me>()