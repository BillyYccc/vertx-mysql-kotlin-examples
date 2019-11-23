package com.billyyccc

val INSERT_QUERY = """
            INSERT INTO users VALUES (1, 'Cat', 'cat123@foomail.com', '2001-05-10'),
            (2, 'Bird', 'bird999@foomail.com', '1999-12-25'),
            (3, 'Panda', 'panda@foobarmail.com', '2002-09-10'),
            (4, 'Dolphin', 'dolphin@foobarmail.com', '2010-04-01'),
            (5, 'Tiger', 'tiger@foomail.com', '2005-07-30');
            """.trimIndent()
const val FIND_ALL = "SELECT id, name, email, registered_date FROM users"
const val FIND_ID_GREATER_THAN = "SELECT id, name, email, registered_date FROM users WHERE id > ?"
const val FIND_ALL_NAME_ORDER_BY_REGISTERED_DATE_DESC =
    "SELECT id, name, email, registered_date FROM user ORDER BY registered_date DESC"
const val UPDATE_REGISTERED_DATE_BY_ID = "UPDATE users SET registered_date = ? WHERE id = ?"
const val DELETE_BY_ID = "DELETE FROM users WHERE ID = ?"
const val DELETE_ALL = "TRUNCATE users"

