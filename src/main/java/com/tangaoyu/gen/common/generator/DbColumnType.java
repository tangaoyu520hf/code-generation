/**
 * Copyright (c) 2011-2020, hubin (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.tangaoyu.gen.common.generator;

/**
 * <p>
 * 表字段类型
 * </p>
 *
 * @author tangaoyu
 * @since 2017-06-27
 */
public enum DbColumnType {
    // 基本类型
    BASE_INT("int", null),
    BASE_BOOLEAN("boolean", null),
    BASE_FLOAT("float", null),
    BASE_DOUBLE("double", null),

    // 包装类型
    STRING("String", "String"),
    LONG("Long", "Long"),
    INTEGER("Integer", "Integer"),
    FLOAT("Float", "Float"),
    DOUBLE("Double", "Double"),
    BOOLEAN("Boolean", "Boolean"),
    BYTE_ARRAY("byte[]", "byte[]"),
    CHARACTER("Character", "Character"),
    OBJECT("Object", "Object"),
    DATE("Date", "java.util.Date"),
    TIME("Time", "java.sql.Time"),
    BLOB("Blob", "java.sql.Blob"),
    CLOB("Clob", "java.sql.Clob"),
    TIMESTAMP("Timestamp", "java.sql.Timestamp"),
    BIG_INTEGER("BigInteger", "java.math.BigInteger"),
    BIG_DECIMAL("BigDecimal", "java.math.BigDecimal"),
    LOCAL_DATE("LocalDate", "java.time.LocalDate"),
    LOCAL_TIME("LocalTime", "java.time.LocalTime"),
    LOCAL_DATE_TIME("LocalDateTime", "java.time.LocalDateTime");

    /**
     * 类型
     */
    private final String type;

    /**
     * 包路径
     */
    private final String pkg;

    DbColumnType(final String type, final String pkg) {
        this.type = type;
        this.pkg = pkg;
    }

    public String getType() {
        return this.type;
    }

    public String getPkg() {
        return this.pkg;
    }

}