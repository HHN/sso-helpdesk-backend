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
package de.hhn.rz.db;

import de.hhn.rz.db.entities.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {

    @Query("SELECT l FROM Location l")
    List<Location> getLocations();

    @Query("SELECT COUNT(c) FROM Location l, AccountCredential c WHERE c.location = l AND c.used is NULL AND l.id = (?1)")
    int getFree(Long id);

    @Query("SELECT COUNT(c) FROM Location l, AccountCredential c WHERE c.location = l AND l.id = (?1)")
    int getTotal(Long id);

}
