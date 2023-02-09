package de.hhn.rz;

public abstract class AbstractService {

   public void checkParameter(Object o) {
       if(o == null) {
           throw new IllegalArgumentException("Parameter must not be NULL!");
       }

       if(o instanceof String s) {
           if(s.isBlank()) {
               throw new IllegalArgumentException("Parameter must not be an empty String!");
           }
       }
   }
}
