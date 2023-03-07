#
# Copyright © 2023 Hochschule Heilbronn (ticket@hs-heilbronn.de)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

FROM openjdk:17-jdk as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw clean install -DskipTests=true

FROM alpine:edge
RUN apk add --no-cache openjdk17 msttcorefonts-installer fontconfig && update-ms-fonts

WORKDIR /deployments/
COPY --from=build /workspace/app/target/hhn-helpdesk-backend-1.0.2.jar /deployments/

ENTRYPOINT ["java", "-jar", "/deployments/hhn-helpdesk-backend-1.0.2.jar"]

