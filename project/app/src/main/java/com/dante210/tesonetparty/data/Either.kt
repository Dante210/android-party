package com.dante210.tesonetparty.data

interface Either<A, B> {
  fun <C> fold(onLeft : (A) -> C, onRight: (B) -> C) : C
  fun <C> map(mapping: (A) -> C): Either<C, B>
  fun <C> flatMap(mapping: (A) -> Either<C, B>): Either<C, B>
}

data class Left<A, B>(private val value: A) : Either<A, B> {
  override fun <C> fold(onLeft: (A) -> C, onRight: (B) -> C) = onLeft(value)
  override fun <C> map(mapping: (A) -> C): Either<C, B> = Left(mapping(value))
  override fun <C> flatMap(mapping: (A) -> Either<C, B>): Either<C, B> = mapping(value)
}

data class Right<A, B>(private val value: B) : Either<A, B> {
  override fun <C> fold(onLeft: (A) -> C, onRight: (B) -> C) = onRight(value)
  override fun <C> map(mapping: (A) -> C): Either<C, B> = Right(value)
  override fun <C> flatMap(mapping: (A) -> Either<C, B>): Either<C, B> = Right(value)
}

data class ErrorMsg(val value: String) {
  fun <B>toError() : Left<ErrorMsg, B> = Left(this)
}
