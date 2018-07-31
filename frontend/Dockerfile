FROM nginx:latest

MAINTAINER anastasiy.tovstik@gmail.com

COPY . /var/www/gradebook

RUN rm /etc/nginx/conf.d/default.conf && mv /var/www/gradebook/gradebook.conf /etc/nginx/conf.d/gradebook.conf

ENTRYPOINT nginx -g "daemon off;"

EXPOSE 80