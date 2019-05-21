package com.angelvargas.data.mappers

abstract class Transform<T1, T2> {
    /**
     * Transforms the input value in the output value specified
     *
     * @param value input value type
     * @return output value type if valid; otherwise null
     */
    abstract fun transform(value: T1): T2

    /**
     * Transforms the collection input values in the collection output values specified
     *
     * @param values collection output values type
     * @return collection input values type if valid; otherwise null
     */
    fun transformCollection(values: List<T1>) = values.map { t1 -> transform(t1) }
}