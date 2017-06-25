package com.mysurvey.domain

case class QuestionAnswer(questionId: QuestionId, question: Question, answer: Answer) {
  def withAnswer(updatedAnswer: Answer): QuestionAnswer = copy(answer = updatedAnswer)
}