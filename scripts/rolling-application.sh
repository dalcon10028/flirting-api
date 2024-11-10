sudo docker pull dalcon10280/dalcon:latest

sudo docker stop flirting
sudo docker rm flirting
sudo docker run -d -p 8880:8880 -e SPRING_PROFILE=development --name flirting dalcon10280/dalcon:latest
sudo docker image prune -f