package com.devopsexam.gameofthrones.exception

import com.devopsexam.gameofthrones.models.WrappedResponse
import com.google.common.base.Throwables
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.validation.ConstraintViolationException

@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    companion object {

        /**
         * For security reasons, we should not leak internal details, like
         * class names, or even the fact we are using Spring
         */
        const val INTERNAL_SERVER_ERROR_MESSAGE = "Internal server error"

        fun handlePossibleConstraintViolation(e: Exception){
            val cause = Throwables.getRootCause(e)
            if(cause is ConstraintViolationException) {
                throw cause
            }
            throw e
        }
    }

    /*
        Note: 404 needs to be handled specially. To do this, look
        at the configurations under application.properties
     */


    @ExceptionHandler(value = [UserInputValidationException::class])
    protected fun handleExplicitlyThrownExceptions(ex: UserInputValidationException, request: WebRequest)
            : ResponseEntity<Any> {

        return handleExceptionInternal(
                ex, null, HttpHeaders(), HttpStatus.valueOf(ex.httpCode), request)
    }


    /*
       This is one case in which JEE is actually better than Spring.
       You might want to have constraints on user inputs directly
       as annotations in method parameters, like it is done for
       example on EJBs.
       Unfortunately, Spring does not do such validation by default.
       See poor excuse/motivation at:
       https://github.com/spring-projects/spring-boot/issues/6228
       https://github.com/spring-projects/spring-boot/issues/6574
       This means we need to manually register an exception handler.
       Every time a ConstraintViolationException is thrown, instead
       of ending up in a 500 error, we catch it are return 400.
       Important: we also need to add @Validated on this class.
    */

    @ExceptionHandler(value = [ConstraintViolationException::class])
    protected fun handleFrameworkExceptionsForUserInputs(ex: Exception, request: WebRequest)
            : ResponseEntity<Any> {

        if(ex is ConstraintViolationException) {
            val messages = StringBuilder()

            for (violation in ex.constraintViolations) {
                messages.append(violation.message + "\n")
            }

            val msg = ex.constraintViolations.map { it.propertyPath.toString() + " " + it.message }
                    .joinToString("; ")

            return handleExceptionInternal(
                    RuntimeException(msg), null, HttpHeaders(), HttpStatus.valueOf(400), request)
        }

        return handleExceptionInternal(
                ex, null, HttpHeaders(), HttpStatus.valueOf(400), request)
    }

    @ExceptionHandler(Exception::class)
    fun handleBugsForUnexpectedExceptions(ex: Exception, request: WebRequest): ResponseEntity<Any> {

        return handleExceptionInternal(
                RuntimeException(INTERNAL_SERVER_ERROR_MESSAGE),
                null, HttpHeaders(),
                HttpStatus.valueOf(500), request)
    }


    override fun handleExceptionInternal(
            ex: Exception,
            body: Any?,
            headers: HttpHeaders,
            status: HttpStatus,
            request: WebRequest
    ) : ResponseEntity<Any> {

        val dto = WrappedResponse<Any>(
                code = status.value(),
                message = ex.message
        ).validated()

        return ResponseEntity(dto, headers, status)
    }
}