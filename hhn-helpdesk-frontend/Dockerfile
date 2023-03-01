FROM node:18 as build

COPY . /work
RUN cd /work && npm i && npm run build


FROM nginx:alpine

COPY nginx.conf /etc/nginx/conf.d/default.conf

COPY --from=build /work/dist /usr/share/nginx/html



