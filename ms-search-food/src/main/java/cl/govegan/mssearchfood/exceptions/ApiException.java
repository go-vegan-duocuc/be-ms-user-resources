package cl.govegan.mssearchfood.exceptions;

public class ApiException extends RuntimeException {
   public ApiException(String message) {
       super(message);
   }
}