FROM nginx:latest

MAINTAINER anastasiy.tovstik@gmail.com

COPY . /var/www/gradebook

RUN rm /etc/nginx/conf.d/default.conf && mv /var/www/gradebook/gradebook.conf /etc/nginx/conf.d/gradebook.conf

ENTRYPOINT sed -i -e 's|http://localhost:8080|${OAUTH_SERVICE_URL}|g' js/services.js \
               && sed -i -e 's|http://localhost:8081|${GRADEBOOK_SERVICE_URL}|g' js/services.js \
               && nginx -g "daemon off;"

EXPOSE 80