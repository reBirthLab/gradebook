FROM nginx:latest

MAINTAINER anastasiy.tovstik@gmail.com

COPY . /var/www/gradebook

RUN mv /var/www/gradebook/gradebook /etc/nginx/sites-available/gradebook \
    && ln -s /etc/nginx/sites-available/gradebook /etc/nginx/sites-enabled/gradebook

ENTRYPOINT nginx -g "daemon off;"

EXPOSE 80