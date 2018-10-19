docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_DATABASE=sell -d mysql:5.6
docker run -d -p 6379:6379 redis:4.0.8
docker run -d --hostname my-rabbit -p 5672:5672 -p 15672:15672 rabbitmq:3.7.8-management
docker run -d -p 9411:9411 openzipkin/zipkin
docker run -d --restart=unless-stopped -p 80:80 -p 443:443 rancher/rancher