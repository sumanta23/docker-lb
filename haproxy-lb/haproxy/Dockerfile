FROM haproxy:latest

RUN DEBIAN_FRONTEND=noninteractive \
    apt-get update -qq && \
    apt-get -y install curl runit && \
    rm -rf /var/lib/apt/lists/*

ENV CT_URL https://github.com/hashicorp/consul-template/releases/download/v0.1.0/consul-template_0.1.0_linux_amd64.tar.gz
RUN curl -L $CT_URL | tar -C /usr/local/bin --strip-components 1 -zxf -

ADD haproxy.service /etc/service/haproxy/run
ADD consul-template.service /etc/service/consul-template/run
RUN chmod +x /etc/service/haproxy/run
RUN chmod +x /etc/service/consul-template/run


ADD haproxy.cfg /etc/consul-templates/haproxy.cfg

CMD ["/usr/bin/runsvdir", "/etc/service"]
