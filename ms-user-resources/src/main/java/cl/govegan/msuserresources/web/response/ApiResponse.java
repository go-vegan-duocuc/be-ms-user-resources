package cl.govegan.msuserresources.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ApiResponse<T> {

   public int status;
   public String message;
   public T data;
   
}
