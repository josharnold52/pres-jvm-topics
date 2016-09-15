#/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
source "$DIR/setup.bash"

java -Xmx4g -Xms4g -XX:+UseConcMarkSweepGC -Xloggc:"$DIR"/../logs/lru-gc.log \
-XX:+PrintGC \
-XX:-PrintGCCause \
-XX:+PrintGCDetails \
-XX:+PrintGCDateStamps \
-XX:+PrintGCTimeStamps \
-XX:+PrintTenuringDistribution \
jvminternals.gclru.LruExample "$@"

#--cache-size 0 --key-space 200000
#--cache-size 200000 --key-space 200000
#--cache-size 100000 --key-space 200000
#--cache-size 195000 --key-space 200000
#--cache-size 199000 --key-space 200000
#--cache-size 199900 --key-space 200000

