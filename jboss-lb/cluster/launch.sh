#!/bin/bash
      
./add-user.sh sam Pass@1234 --silent

IPADDR=$(ip a s | sed -ne '/127.0.0.1/!{s/^[ \t]*inet[ \t]*\([0-9.]\+\)\/.*$/\1/p}')
      
./standalone.sh -c standalone-ha.xml -b $IPADDR -bmanagement $IPADDR -u 230.0.0.4 -Djboss.node.name=server-$IPADDR
