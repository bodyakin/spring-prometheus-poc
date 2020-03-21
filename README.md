docker run -d -p 27017:27017 -v ~/data:/data/db --name mongo mongo

docker pull prom/prometheus
docker run -d --name=prometheus -p 9090:9090 -v ./prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus --config.file=/etc/prometheus/prometheus.yml

docker run -d -p 3000:3000 grafana/grafana
