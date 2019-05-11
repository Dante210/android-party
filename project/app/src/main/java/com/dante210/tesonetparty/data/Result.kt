package com.dante210.tesonetparty.data

sealed class Result<A, B> {
  abstract fun <C> fold(onLeft : (A) -> C, onRight: (B) -> C) : C
  abstract fun <C> map(mapping: (A) -> C): Result<C, B>
  abstract fun <C> flatMap(mapping: (A) -> Result<C, B>): Result<C, B>
  abstract fun <C> mapFailure(mapping: (B) -> C): Result<A, C>
  abstract fun <C> flatMapFailure(mapping: (B) -> Result<A, C>): Result<A, C>
  abstract fun orElse(other: A): A
  abstract fun orElse(function: (B) -> A): A
}

data class Success<A, B>(val value: A) : Result<A, B>() {
  override fun <C> fold(onLeft: (A) -> C, onRight: (B) -> C) = onLeft(value)
  override fun <C> map(mapping: (A) -> C): Result<C, B> = Success(mapping(value))
  override fun <C> flatMap(mapping: (A) -> Result<C, B>): Result<C, B> = mapping(value)
  override fun <C> mapFailure(mapping: (B) -> C): Result<A, C> = Success(value)
  override fun <C> flatMapFailure(mapping: (B) -> Result<A, C>): Result<A, C> = Success(value)
  override fun orElse(other: A): A = value
  override fun orElse(function: (B) -> A): A = value
}

data class Error<A, B>(val value: B) : Result<A, B>() {
  override fun <C> fold(onLeft: (A) -> C, onRight: (B) -> C) = onRight(value)
  override fun <C> map(mapping: (A) -> C): Result<C, B> = Error(value)
  override fun <C> flatMap(mapping: (A) -> Result<C, B>): Result<C, B> = Error(value)
  override fun <C> mapFailure(mapping: (B) -> C): Result<A, C> = Error(mapping(value))
  override fun <C> flatMapFailure(mapping: (B) -> Result<A, C>): Result<A, C> = mapping(value)
  override fun orElse(other: A): A = other
  override fun orElse(function: (B) -> A): A = function(value)
}

data class ErrorMsg(val value: String) {
  fun <A>toError() : Error<A, ErrorMsg> = Error(this)
}
