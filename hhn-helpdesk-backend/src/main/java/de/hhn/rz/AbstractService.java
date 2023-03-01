/*
 * Copyright © 2023 Hochschule Heilbronn (ticket@hs-heilbronn.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
