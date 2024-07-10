package cl.govegan.msuserresources.exception;

public class TokenValidationException extends RuntimeException{
      public TokenValidationException(String message){
         super(message);
      }
}
